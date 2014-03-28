
package engine;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.sun.opengl.util.Animator;

/**
 * ywho
 * @author 929837
 *
 */
public class Source extends JFrame 
{

	BufferedImage crosshairs = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
	World world = new World();
	Eye eye = new Eye();
	Control control = new Control();
	KeyFunctions kFunc = new KeyFunctions();
	MouseFunctions mFunc = new MouseFunctions();
	float zoom = 0;
	float speed = 25f;
	String currentTrack = "";
	MP3 mp3;
	private int windowWidth;
	private int windowHeight;

	/**
	 * Creates a new window and displays to the user. Makes it visible.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Source frame = new Source();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	/**
	 * Titles the program, sets the Window Size to full screen, imports and sets up JOGL graphics
	 * Adds listeners to the window pane
	 */
	public Source() {
		setTitle("Velociraptor Hunter");
		setUndecorated(true);
		windowWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		windowHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());

			/*try {
				crosshairs = ImageIO.read(new File("crosshairwhite.png"));
			} catch (IOException e) {
				System.err.println("File Not Found!");
				e.printStackTrace();
			}
		*/

		//TODO load in new image for cursor
		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB) , new Point(0,0), "none"));
			//
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(crosshairs, new Point(0,0), "cross"));

		setSize(windowWidth, windowHeight);
		GraphicListener listener = new GraphicListener();
		GLCanvas canvas = new GLCanvas(new GLCapabilities());
		canvas.addGLEventListener(listener);
		getContentPane().add(canvas);
		canvas.addMouseListener(new MouseAdapter() {
			/**
			 * Calls the fire method
			 */
			public void mousePressed(MouseEvent e) {
				///control.click(e);
			}
		});


		canvas.addMouseMotionListener(new MouseAdapter() {
			/**
			 * Calls the camera and moves it based on user-mouse movement
			 */
			public void mouseMoved(MouseEvent e) {
				control.move(e);
			}
		});

		canvas.addMouseWheelListener(new MouseAdapter() {
			/**
			 * If mouse wheel is moved, calls the switchWeapon method
			 */
			public void mouseWheelMoved(MouseWheelEvent e) {

			}
		});

		addKeyListener(new KeyListener() {
			/**
			 * Exits the program if Esc key is pressed
			 */
			public void keyPressed(KeyEvent e) {
				control.pressKey(e);
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
			public void keyReleased(KeyEvent e) {
				control.releaseKey(e);
			}
			public void keyTyped(KeyEvent e) {
			}
		});

		addWindowFocusListener(new WindowAdapter() {
			/**
			 * Controls are inactive when window loses focus
			 */
			public void windowLostFocus(WindowEvent e) {
				control.stopKeys();
			}
		});

		Animator animator = new Animator(canvas);
		animator.start();
		requestFocus();
		canvas.setFocusable(false);
		kFunc.setSpeed(speed);
		kFunc.setMap(world.getMap());
		mFunc.setSpeed(speed);
		//setMusic("What I'm Made Of");
	}
	/**
	 * Implements the default OpenGL methods into the program
	 *
	 */
	public class GraphicListener implements GLEventListener {
		/**
		 * Sets up mouse movement to correspond with character, sets up perspective matrix, calibrates camera movement
		 */
		public void display(GLAutoDrawable glad) {
			kFunc.setMouse(control.getMouseX(), control.getMouseY());
			kFunc.processKeys(control.getKeysDown());
			kFunc.moveEye(eye);
			mFunc.setMouse(control.getMouseX(), control.getMouseY());
			mFunc.moveEye(eye);
			GL myGL = glad.getGL();
			GLU glu = new GLU();
		
		
			myGL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			myGL.glMatrixMode(GL.GL_MODELVIEW);
			myGL.glLoadIdentity();
			
			myGL.glPushMatrix();
			myGL.glLoadIdentity();
			myGL.glOrtho(0.0f, windowWidth, windowHeight, 0.0f, 0.0f, 1.0f);
			drawStatics(myGL);
			myGL.glPopMatrix();
		
		//	myGL.glRotated(90,0,0,1);
			/**
			 * Moves the camera to where viewer is looking
			 */
			glu.gluLookAt(eye.getXPosition(), eye.getYPosition(), eye.getZPosition(),
					eye.getXView(), eye.getYView(), eye.getZView(),
					0, 1, 0);
			/**
			 * Creates and draws the player environment
			 */
			myGL.glPushMatrix();
		
			world.makeWorld(glad);
			myGL.glPopMatrix();
			
		}
		/**
		 *Calls if you change resolution or monitors
		 */
		public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		}
		/**
		 * Initializes lighting
		 */
		public void init(GLAutoDrawable glad) {
			Lighting.light(glad);
		}
		/**
		 * Called when window is resized (which it won't be)
		 */
		public void reshape(GLAutoDrawable glad, int arg1, int arg2, int arg3, int arg4) {
			GLU glu = new GLU();
			GL myGL = glad.getGL();
			myGL.glMatrixMode(GL.GL_PROJECTION);
			myGL.glLoadIdentity();
			glu.gluPerspective(60, 1, 1, 500000); 	
		
		}
	}
	/**
	 * Jam to your bad background music while you kill velociraptors
	 * @param mp3 file name
	 */
	public void setMusic(String str) {
		if(!str.equals(currentTrack)) {
			mp3 = new MP3("/Soundtrack/" + str + ".mp3");
			mp3.play();
			currentTrack = str;
		}
	}
	/**
	 * Stops the music
	 */
	public void stopMusic() {
		mp3.close();
		currentTrack = "";
	}
	
	public void drawStatics(GL myGL){
		myGL.glBegin(GL.GL_LINES);
		myGL.glColor3f(1, 0, 0);
		myGL.glVertex2d(0,0);
		myGL.glVertex2d(1,0);
		myGL.glVertex2d(0,1);
		myGL.glEnd();
	}

}
