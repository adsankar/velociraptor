package test;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

public class ObjDemo extends GLCanvas {

	private static Animator an;
	
	private boolean isRunning = true;
	private int rotAxis = 0;
	private float scaling = 1;
	private int rotateAngle = 0;
	
	private float[] vert;
	private float[] ele;
	private float[] nor;

	public static void main(String[] args) {
		Frame f = new Frame("It's a Monkey!");
		GLCapabilities glCap = new GLCapabilities();
		ObjDemo o = new ObjDemo(glCap);
		f.add(o);
		
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}//end window closing event
		});
		f.setBackground(Color.gray);
		f.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		an = new FPSAnimator(o,60);
		an.start();
		
		
		f.setVisible(true);

	}

	public ObjDemo(GLCapabilities glc){
		super(glc);
		//add a key adapter to handle key events
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				//quit if Q is pressed
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.err.print("ESC PRESSED: QUITTING");
					System.exit(1);
				}


				//pause the animation when space is pressed, unpause after second space
				if (e.getKeyChar() == ' ') {
					isRunning = !isRunning;
				}

				//change the axis of rotation when R is pressed
				if (e.getKeyChar() == 'r') {
					rotAxis++;
					if (rotAxis >= 6) {
						rotAxis = 0;
					}
				}

				//zoom in when up arrow is pressed
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					bigger();
				}

				//zoom out when down arrow is pressed
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					smaller();
				}
			}
		});//end key adapter


		//add the GLEventListener
		addGLEventListener(new GLEventListener() {

			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y,
					int width, int height) {
				doReshape(drawable.getGL(), width, height);

			}

			@Override
			public void init(GLAutoDrawable drawable) {
				/**not used*/

			}

			@Override
			public void displayChanged(GLAutoDrawable drawable,
					boolean modeChanged, boolean deviceChanged) {
				/**not used*/

			}

			@Override
			public void display(GLAutoDrawable drawable) {
				rotation();
				doDisplay(drawable.getGL(), drawable.getWidth(),
						drawable.getHeight());

			}
		});
	}

	/**
	 * zoom in by increasing shape scale
	 */
	public void bigger() {
		scaling += .01;
	}

	/**
	 * zoom out by decreasing shape
	 */
	public void smaller() {
		scaling -= .01;
		if (scaling < .01) {//don't get too small
			scaling = 0;
		}
	}

	/**
	 * Called when the frame is reshaped
	 * @param myGL
	 * @param w
	 * @param h
	 */
	public void doReshape(GL myGL, int w, int h) {
		GLU myglu = new GLU();
		myGL.glMatrixMode(GL.GL_PROJECTION);
		myGL.glLoadIdentity();
		myglu.gluOrtho2D(-3, 3, -3, 3);
		myGL.glViewport(0, 0, w, h);
	}

	/**
	 * Increment the angle so that the shape is constantly rotating
	 */
	public void rotation() {
		if (isRunning) {
			rotateAngle += 1;
			rotateAngle = rotateAngle % 360;
		}
	}

	/**
	 * called when <code>display()</code> is called
	 * @param myGL
	 * @param w
	 * @param h
	 */
	public void doDisplay(GL myGL, int w, int h) {
		myGL.glShadeModel(GL.GL_SMOOTH);
		myGL.glClear(GL.GL_COLOR_BUFFER_BIT);
		myGL.glMatrixMode(GL.GL_MODELVIEW);
		myGL.glLoadIdentity();
		myGL.glColor3d(0, 0, 1);
		myGL.glScaled(scaling, scaling, scaling);//for zoom in/zoom out effects

		//change axis of rotation
		if (rotAxis == 0) {
			myGL.glRotatef(rotateAngle, 0, 0, 1);
		}

		if (rotAxis == 1) {
			myGL.glRotatef(rotateAngle, 0, 1, 0);
		}
		if (rotAxis == 2) {
			myGL.glRotatef(rotateAngle, 1, 0, 0);
		}
		if (rotAxis == 3) {
			myGL.glRotatef(rotateAngle, 1, 1, 0);
		}
		if (rotAxis == 4) {
			myGL.glRotatef(rotateAngle, 1, 0, 1);
		}
		if (rotAxis == 5) {
			myGL.glRotatef(rotateAngle, 1, 1, 1);
		}

	}//end doDisplay
	
	public void loadInfo(){
		
	}
	
	public void drawShape(GL myGL){
		
	}
	
}
