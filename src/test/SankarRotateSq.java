package test;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * 2D Exercises
 * @author Aleksander Sankar
 * 3D Graphics Pd. 2
 * Mr. Fowler
 */
public class SankarRotateSq extends GLCanvas {
	
	//private variables for handling mouse clicks, key events
	private int rotateAngle = 0;
	private boolean shape = true;
	private boolean isRunning = true;
	private int colorSwitch = 0;
	private int rotAxis = 0;
	private double scaling = 1;
	private boolean smashing = false;
	private double smashOffset = 0;

	
	/**
	 * Constructor which creates the GLCanvas and adds the required listeners to it.
	 * @param glc the GLCapabilities used
	 */
	public SankarRotateSq(GLCapabilities glc) {
		super(glc);

		//add a mouse listener
		this.addMouseListener(new MouseListener() {
			/**not used*/
			public void mouseReleased(MouseEvent arg0) {
			}
			/**not used*/
			public void mousePressed(MouseEvent arg0) {
			}
			/**not used*/
			public void mouseExited(MouseEvent arg0) {
			}
			/**not used*/
			public void mouseEntered(MouseEvent arg0) {
			}

			/**
			 * When user clicks, color changes
			 */
			public void mouseClicked(MouseEvent e) {
				colorSwitch++;
				if (colorSwitch > 6) {
					colorSwitch = 0;
				}

			}
		});//end mouse listener

		//add a key adapter to handle key events
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				//quit if Q is pressed
				if (e.getKeyChar() == 'q') {
					System.err.print("Q PRESSED: QUITTING");
					System.exit(1);
				}
				
				//change shape (to triangle) and back when T is pressed
				if (e.getKeyChar() == 't') {
					System.out.println("Shape Changed!");
					shape = !shape;
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

				//start the smash effect when Esc is pressed
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {// esc key
					// System.out.println("Smash!");
					smashing = !smashing;

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
	 * Incrementor for the smash effect
	 */
	public void smash() {
		smashOffset += .1;
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

		//process of smashing
		//triangle pieces move away from the center 
		if (smashing) {

			smash();
			myGL.glColor3d(1, 0, 0);
			myGL.glBegin(GL.GL_TRIANGLES);

			myGL.glVertex2d(0 - smashOffset / 3, 0 + smashOffset);// ^\
			myGL.glVertex2d(0 - smashOffset / 3, 1 + smashOffset);
			myGL.glVertex2d(-1 - smashOffset / 3, 1 + smashOffset);

			myGL.glVertex2d(0 - smashOffset, 0 + smashOffset / 3);// \^
			myGL.glVertex2d(-1 - smashOffset, 0 + smashOffset / 3);
			myGL.glVertex2d(-1 - smashOffset, 1 + smashOffset / 3);

			myGL.glVertex2d(0 - smashOffset, 0 - smashOffset / 3);// /v
			myGL.glVertex2d(-1 - smashOffset, 0 - smashOffset / 3);
			myGL.glVertex2d(-1 - smashOffset, -1 - smashOffset / 3);

			myGL.glVertex2d(0 - smashOffset / 3, 0 - smashOffset);// v/
			myGL.glVertex2d(0 - smashOffset / 3, -1 - smashOffset);
			myGL.glVertex2d(-1 - smashOffset / 3, -1 - smashOffset);

			myGL.glVertex2d(0 + smashOffset / 3, 0 - smashOffset);// v\
			myGL.glVertex2d(0 + smashOffset / 3, -1 - smashOffset);
			myGL.glVertex2d(1 + smashOffset / 3, -1 - smashOffset);

			myGL.glVertex2d(0 + smashOffset, 0 - smashOffset / 3);// \v
			myGL.glVertex2d(1 + smashOffset, 0 - smashOffset / 3);
			myGL.glVertex2d(1 + smashOffset, -1 - smashOffset / 3);

			myGL.glVertex2d(1 + smashOffset, 0 + smashOffset / 3);// /^
			myGL.glVertex2d(0 + smashOffset, 0 + smashOffset / 3);
			myGL.glVertex2d(1 + smashOffset, 1 + smashOffset / 3);

			myGL.glVertex2d(0 + smashOffset / 3, 0 + smashOffset);// ^/
			myGL.glVertex2d(0 + smashOffset / 3, 1 + smashOffset);
			myGL.glVertex2d(1 + smashOffset / 3, 1 + smashOffset);

			myGL.glEnd();
			if (smashOffset >= 4) {//when triangles of screen, reset
				smashOffset = 0;
				smashing = false;
			}

		} else {

			//regular (non-smashing) effects
			if (shape) {
				myGL.glBegin(GL.GL_QUADS);

				myGL.glVertex2d(-1, -1);
				switch (colorSwitch) {//change the gradient color
				case 0:
					myGL.glColor3d(0, 0, 1);
					break;
				case 1:
					myGL.glColor3d(0, 1, 1);
					break;
				case 2:
					myGL.glColor3d(1, 1, 1);
					break;
				case 3:
					myGL.glColor3d(1, 0, 0);
					break;
				case 4:
					myGL.glColor3d(1, 0, 1);
					break;
				case 5:
					myGL.glColor3d(1, 1, 0);
					break;
				case 6:
					myGL.glColor3d(0, 1, 0);
					break;
				default:
					myGL.glColor3d(0, 1, 0);
					break;
				}
				//draw the square
				myGL.glVertex2d(-1, 1);
				myGL.glVertex2d(1, 1);
				myGL.glVertex2d(1, -1);
				myGL.glPushMatrix();
				myGL.glEnd();
			} else {
				//draw the triangle 
				myGL.glColor3d(0, 1, 0);
				myGL.glBegin(GL.GL_TRIANGLES);
				myGL.glVertex2d(-1, -1);
				myGL.glVertex2d(-1, 1);
				myGL.glVertex2d(.5, 1);
				myGL.glPushMatrix();
				myGL.glEnd();
			}//end else
		}//end if
	}//end doDisplay
}//end class