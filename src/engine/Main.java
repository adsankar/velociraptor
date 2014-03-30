
package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.j2d.TextRenderer;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

/**
 * ywho
 * @author 929837
 *
 */
public class Main extends GLCanvas{
	//TODO per vertex normals, player movement height, move dimensions, enemies, shoot, stats, help window

	private Texture cross;
	private BufferedImage crosshairs = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

	//private float zoom = 0;
	private float speed = 25f;
	private TextRenderer renderer;
	private String currentTrack = "";
	private MP3 mp3;
	private static int windowWidth;
	private static int windowHeight;
	private World world;
	private Eye eye;
	private Control control;
	private KeyFunctions kFunc;
	private MouseFunctions mFunc;
	private static Animator an;

	//Temporary
	private int ammo = 100;


	/**
	 * Creates a new window and displays to the user. Makes it visible.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		GLCapabilities glCap = new GLCapabilities();  
		Main m = new Main(glCap);
		JFrame jf = new JFrame("Velociraptor Hunter 3D");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBackground(Color.gray);
		jf.setSize(windowWidth,windowHeight);
		jf.setUndecorated(true);
		jf.add(m);//add it to the frame

		m.setFocusable(true);
		m.requestFocusInWindow();
		jf.setVisible(true);//show the frame
		an=new FPSAnimator(m, 60);//create animator, add it to the <code>TextureRunner</code> object and set it to 60 frames/second   
		an.start();//start the animator;

	}
	/**
	 * Titles the program, sets the Window Size to full screen, imports and sets up JOGL graphics
	 * Adds listeners to the window pane
	 */
	public Main(GLCapabilities glc) {
		super(glc);
		windowWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		windowHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		world = new World();
		eye = new Eye();
		control = new Control();
		kFunc = new KeyFunctions();
		mFunc = new MouseFunctions();
		try {
			crosshairs = ImageIO.read(new File("crosshairwhite.png"));
		} catch (IOException e) {
			System.err.println("File Not Found!");
			e.printStackTrace();
			System.exit(1);
		}


		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB) , new Point(0,0), "none"));

		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(crosshairs, new Point(0,0), "cross"));

		setSize(windowWidth, windowHeight);
		this.addMouseListener(new MouseAdapter() {
			/**
			 * Calls the fire method
			 */
			public void mousePressed(MouseEvent e) {
				///control.click(e);
				ammo--;
			}
		});


		this.addMouseMotionListener(new MouseAdapter() {
			/**
			 * Calls the camera and moves it based on user-mouse movement
			 */
			public void mouseMoved(MouseEvent e) {
				control.move(e);
			}
		});

		this.addMouseWheelListener(new MouseAdapter() {
			/**
			 * If mouse wheel is moved, calls the switchWeapon method
			 */
			public void mouseWheelMoved(MouseWheelEvent e) {

			}
		});

		this.addKeyListener(new KeyListener() {
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

		/*this.addFocusListener(new WindowAdapter() {
		 *//**
		 * Controls are inactive when window loses focus
		 *//*
			public void windowLostFocus(WindowEvent e) {
				control.stopKeys();
			}
		});*/

		////Animator animator = new Animator(canvas);
		//animator.start();
		kFunc.setSpeed(speed);
		kFunc.setMap(world.getMap());
		mFunc.setSpeed(speed);
		//setMusic("What I'm Made Of");

		addGLEventListener(new GLEventListener(){

			@Override
			public void display(GLAutoDrawable drawable) {

				doDisplay(drawable.getGL(), drawable.getWidth(),
						drawable.getHeight());

			}//end display

			@Override
			/**not used*/
			public void displayChanged(GLAutoDrawable drawable,
					boolean modeChanged, boolean deviceChanged) {

			}//end displayChanged

			@Override
			/**
			 * Call the doInit method
			 */
			public void init(GLAutoDrawable drawable) {

				doInit(drawable.getGL());

			}//end init

			@Override
			/**
			 * Call the doReshape method
			 */
			public void reshape(GLAutoDrawable drawable, int x, int y,
					int width, int height) {
				doReshape(drawable.getGL(), width, height);

			}//end reshape

		});//end GLEventListener

	}

	/**
	 * Sets up mouse movement to correspond with character, sets up perspective matrix, calibrates camera movement
	 */
	public void doDisplay(GL myGL, int w, int h) {
		kFunc.setMouse(control.getMouseX(), control.getMouseY());
		kFunc.processKeys(control.getKeysDown());
		kFunc.moveEye(eye);
		mFunc.setMouse(control.getMouseX(), control.getMouseY());
		mFunc.moveEye(eye);
		GLU glu = new GLU();


		myGL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		myGL.glMatrixMode(GL.GL_MODELVIEW);
		myGL.glLoadIdentity();


		//	myGL.glRotated(90,0,0,1);
		/**
		 * Moves the camera to where viewer is looking
		 */
		glu.gluLookAt(eye.getXPosition(), eye.getYPosition(), eye.getZPosition(),
				eye.getXView(), eye.getYView(), eye.getZView(),
				0, 1, 0);
		Lighting.setLightPosition(myGL, new float[] {-0.4f, 0.5f, 0.7f, 0});
		/**
		 * Creates and draws the player environment
		 */
		myGL.glPushMatrix();

		world.makeWorld(myGL);

		myGL.glPopMatrix();
		drawStatics(myGL);
		//start the text renderer, set its color and display text
		renderer.setColor(1,1,.6f,.7f);//RGBA colors
		renderer.beginRendering(w, h);
		//display some basic information
		renderer.draw("Health: "+100, windowWidth-300, windowHeight-80); //get player health
		renderer.draw("Ammo: "+ammo,windowWidth-300,windowHeight-120); //get ammo
		myGL.glClearColor(0f,0f,0f,1f);//so that not all of the shapes have this color
		renderer.endRendering();
	}
	/**
	 *Calls if you change resolution or monitors
	 */
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}
	/**
	 * Initializes lighting
	 */
	public void doInit(GL myGL) {
		renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 30),true,true);
		//myGL.glClearColor(0f,0f,0f,1f);//so that not all of the shapes have this color
		loadTextures(myGL);
		Lighting.light(myGL);
	}
	/**
	 * Called when window is resized (which it won't be)
	 */
	public void doReshape(GL myGL, int w, int h) {
		GLU glu = new GLU();
		myGL.glMatrixMode(GL.GL_PROJECTION);
		myGL.glLoadIdentity();
		glu.gluPerspective(60, 1, 1, 500000); 	

	}

	/**
	 * Jam to your bad background music while you kill velociraptors
	 * @param str the mp3 file name
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
		/*myGL.glBegin(GL.GL_LINES);
		myGL.glColor3f(1, 0, 0);
		myGL.glLineWidth(4);
		myGL.glVertex2d(0,0);
		myGL.glVertex2d(10,0);
		myGL.glVertex2d(0,10);
		myGL.glEnd();*/

		
		myGL.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		myGL.glMatrixMode(GL.GL_PROJECTION);
		myGL.glPushMatrix();
		myGL.glLoadIdentity();
		myGL.glOrtho(0.0, windowWidth, 0.0, windowHeight, -1.0, 1.0);
		myGL.glMatrixMode(GL.GL_MODELVIEW);
		myGL.glPushMatrix();


		myGL.glLoadIdentity();
		myGL.glDisable(GL.GL_LIGHTING);


		myGL.glColor3f(1,1,1);
		myGL.glEnable(GL.GL_TEXTURE_2D);
		//myGL.glBindTexture(GL.GL_TEXTURE_2D, );

		cross.enable();
		cross.bind();
		// Draw a textured quad
		myGL.glBegin(GL.GL_QUADS);
		
		//TODO reposition, resize
		myGL.glTexCoord2f(0, 0); myGL.glVertex3f(0, 0, 0);
		myGL.glTexCoord2f(0, 1); myGL.glVertex3f(0, 100, 0);
		myGL.glTexCoord2f(1, 1); myGL.glVertex3f(100, 100, 0);
		myGL.glTexCoord2f(1, 0); myGL.glVertex3f(100, 0, 0);
		myGL.glEnd();


		myGL.glDisable(GL.GL_TEXTURE_2D);
		myGL.glPopMatrix();


		myGL.glMatrixMode(GL.GL_PROJECTION);
		myGL.glPopMatrix();

		myGL.glMatrixMode(GL.GL_MODELVIEW);
		myGL.glEnable(GL.GL_LIGHTING);

	}
	
	public void loadTextures(GL myGL){
		myGL.glTexParameterf(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR );

		try{
			//load a TextureData object from a picture and then create a texture from it
			cross =TextureIO.newTexture(new File("crosshairwhite.png"),true);
		}//end try
		catch(IOException e){
			System.out.println("File not found!");
			System.exit(1);
		}//end catch
		catch(GLException f){
			System.exit(1);
		}//end catch

	}//end loadTextures
	

}
