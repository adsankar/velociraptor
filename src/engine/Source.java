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

pent

public class Source extends JFrame {
	
	World world = new World();
	Eye eye = new Eye();
	Control control = new Control();
	KeyFunctions kFunc = new KeyFunctions();
	MouseFunctions mFunc = new MouseFunctions();
	float zoom = 0;
	float speed = 15f;
	String currentTrack = "";
	MP3 mp3;
	
	public static void main(String[] args) {
		Source frame = new Source();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public Source() {
		setTitle("Velociraptor Hunter");
		setUndecorated(true);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB) , new Point(0,0), "none"));
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		GraphicListener listener = new GraphicListener();
		GLCanvas canvas = new GLCanvas(new GLCapabilities());
		canvas.addGLEventListener(listener);
		getContentPane().add(canvas);
		
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				///control.click(e);
			}
		});
		
			
		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				control.move(e);
			}
		});
		
		canvas.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				
			}
		});
		
		addKeyListener(new KeyListener() {
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
	
	public class GraphicListener implements GLEventListener {
		public void display(GLAutoDrawable glad) {
			kFunc.setMouse(control.getMouseX(), control.getMouseY());
			kFunc.processKeys(control.getKeysDown());
			eye = kFunc.moveEye(eye);
			mFunc.setMouse(control.getMouseX(), control.getMouseY());
			eye = mFunc.moveEye(eye);
			GL gl = glad.getGL();
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
			GLU glu = new GLU();
			
			glu.gluLookAt(eye.getXPosition(), eye.getYPosition(), eye.getZPosition(),
					eye.getXView(), eye.getYView(), eye.getZView(),
					0, 1, 0);
			world.makeWorld(glad);
		}
		
		public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		}
		
		public void init(GLAutoDrawable glad) {
			Lighting.light(glad);
		}
		
		public void reshape(GLAutoDrawable glad, int arg1, int arg2, int arg3, int arg4) {
			GLU glu = new GLU();
			GL gl = glad.getGL();
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			glu.gluPerspective(60, 1, 1, 500000); 	
		}
	}
	
	public void setMusic(String str) {
		if(!str.equals(currentTrack)) {
			mp3 = new MP3("/Soundtrack/" + str + ".mp3");
			mp3.play();
			currentTrack = str;
		}
	}
	
	public void stopMusic() {
		mp3.close();
		currentTrack = "";
	}
	
}