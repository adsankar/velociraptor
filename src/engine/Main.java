
package engine;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.j2d.TextRenderer;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

/**
 * 
 * @author 929837
 *
 */
public class Main extends GLCanvas{
	//TODO fix per vertex normals, crouch, run, enemies, spawn, shoot, help window

	private static Component frame;

	private Texture cross;
	private Texture wall1;
	private Texture wall2;
	private Texture wall3;
	private Texture wall4;
	private Texture sky;
	private FloatBuffer weaponVert;
	//private BufferedImage crosshairs = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

	//private float zoom = 0;
	private float speed = 80f;
	private final int crossSize  = 50;
	private final int ENEMY_DELAY_TIME = 30;
	private TextRenderer renderer;
	private String currentTrack = "";
	private MP3 mp3;
	private static int windowWidth;
	private static int windowHeight;
	private World world;
	private Control control;
	private KeyFunctions kFunc;
	private MouseFunctions mFunc;
	private Player p;
	private static Animator an;
	private Timer enemyTimer;

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
		control = new Control();
		kFunc = new KeyFunctions();
		mFunc = new MouseFunctions();
		p = new Player();


		/*try {
			crosshairs = ImageIO.read(new File("crosshairwhite.png"));
		} catch (IOException e) {
			System.err.println("File Not Found!");
			e.printStackTrace();
			System.exit(1);
		}*/

		enemyTimer = new Timer (ENEMY_DELAY_TIME, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Enemy.drawEnemy(256*Math.random(),256*Math.random(),getGL());

				//	Enemy.drawEnemy(250,250,getGL());

				//Enemy.drawEnemy(250,250,getGL());
				drawEnemy();

			}

		});
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB) , new Point(0,0), "none"));

		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(crosshairs, new Point(0,0), "cross"));

		setSize(windowWidth, windowHeight);
		this.addMouseListener(new MouseAdapter() {
			/**
			 * Calls the fire method
			 */
			public void mousePressed(MouseEvent e) {
				///control.click(e);
				p.fire();
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
				p.switchWeapons(e.getWheelRotation());
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
					if(e.getKeyCode() == KeyEvent.VK_H) {
						helpWindow();
					}
				}
			}
			public void keyReleased(KeyEvent e) {
				control.releaseKey(e);
			}

			public void keyTyped(KeyEvent arg0) {				
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

		kFunc.setSpeed(speed);
		kFunc.setMap(World.getMap());
		mFunc.setSpeed(speed);
		//setMusic("What I'm Made Of");
		enemyTimer.start();

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
		kFunc.moveEye(p);
		mFunc.setMouse(control.getMouseX(), control.getMouseY());
		mFunc.eye(p);
		GLU glu = new GLU();


		myGL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		myGL.glMatrixMode(GL.GL_MODELVIEW);
		myGL.glLoadIdentity();


		//	myGL.glRotated(90,0,0,1);
		/**
		 * Moves the camera to where viewer is looking
		 */
		glu.gluLookAt(p.getXPosition(), p.getYPosition(), p.getZPosition(),
				p.getXView(), p.getYView(), p.getZView(),
				0, 1, 0);
		Lighting.setLightPosition(myGL, new float[] {-0.4f, 0.5f, 0.7f, 0});
		/**
		 * Creates and draws the player environment
		 */
		myGL.glPushMatrix();

		world.makeWorld(myGL);
		setWalls(myGL);
		myGL.glPopMatrix();
		drawStatics(myGL);
		//start the text renderer, set its color and display text
		renderer.setColor(1,1,.6f,.7f);//RGBA colors
		renderer.beginRendering(w, h);
		//display some basic information
		renderer.draw("Health: "+Player.getHealth(), windowWidth-300, windowHeight-80); //get player health
		renderer.draw("Ammo: "+Player.getAmmo(),windowWidth-300,windowHeight-120); //get ammo
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
		myGL.glClearColor(0f,0f,0f,1f);//so that not all of the shapes have this color
		loadVertexData(new File ("axe.txt"));
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

	public void helpWindow(){
		JOptionPane.showMessageDialog(frame, "info goes here", "Help", JOptionPane.INFORMATION_MESSAGE);
	}

	public void drawStatics(GL myGL){
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

		cross.enable();
		cross.bind();
		// Draw a textured quad
		myGL.glBegin(GL.GL_QUADS);

		myGL.glTexCoord2f(0, 0);
		myGL.glVertex3f(windowWidth/2-crossSize, windowHeight/2-crossSize, 0);
		myGL.glTexCoord2f(0, 1);
		myGL.glVertex3f(windowWidth/2-crossSize, windowHeight/2+crossSize, 0);
		myGL.glTexCoord2f(1, 1);
		myGL.glVertex3f(windowWidth/2+crossSize, windowHeight/2+crossSize, 0);
		myGL.glTexCoord2f(1, 0);
		myGL.glVertex3f(windowWidth/2+crossSize, windowHeight/2-crossSize, 0);

		myGL.glEnd();
		myGL.glTranslated(0, 5, 0);
		drawWeapon(myGL);


		myGL.glDisable(GL.GL_TEXTURE_2D);
		myGL.glPopMatrix();



		myGL.glMatrixMode(GL.GL_PROJECTION);
		myGL.glPopMatrix();

		myGL.glMatrixMode(GL.GL_MODELVIEW);
		myGL.glEnable(GL.GL_LIGHTING);

		myGL.glPushMatrix();

		myGL.glTranslated(10, 25, 0);

		drawWeapon(myGL);
		myGL.glPopMatrix(); 
		myGL.glEnd();



	}

	public void setWalls(GL myGL){
		//TODO fix this

		myGL.glEnable(GL.GL_TEXTURE_2D);
		myGL.glClearColor(0,0,0,1);
		//myGL.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE,new float[]{1,1,1,1 },0);
		myGL.glColor3d(1, 1, 1);

		//TODO other wall textures

		wall1.enable();
		wall1.bind();
		myGL.glBegin(GL.GL_QUADS);

		myGL.glTexCoord2f(0, 0);
		myGL.glVertex3f(256, -50, 0);
		myGL.glTexCoord2f(0, 1);
		myGL.glVertex3f(0,-50, 0);
		myGL.glTexCoord2f(1, 1);
		myGL.glVertex3f(0,120, 0);
		myGL.glTexCoord2f(1, 0);
		myGL.glVertex3f(256, 120, 0);

		wall1.disable();

		wall2.enable();
		wall2.bind();
		myGL.glTexCoord2f(0, 0);
		myGL.glVertex3f(0, -50, 256);
		myGL.glTexCoord2f(0, 1);
		myGL.glVertex3f(0,-50, 0);
		myGL.glTexCoord2f(1, 1);
		myGL.glVertex3f(0,120, 0);
		myGL.glTexCoord2f(1, 0);
		myGL.glVertex3f(0,120, 256);

		wall2.disable();

		wall1.enable();
		wall1.bind();
		myGL.glTexCoord2f(0, 0);
		myGL.glVertex3f(0, -50, 256);
		myGL.glTexCoord2f(0, 1);
		myGL.glVertex3f(256,-50, 256);
		myGL.glTexCoord2f(1, 1);
		myGL.glVertex3f(256,120, 256);
		myGL.glTexCoord2f(1, 0);
		myGL.glVertex3f(0, 120, 256);

		wall1.disable();

		wall1.enable();
		wall1.bind();
		myGL.glTexCoord2f(0, 0);
		myGL.glVertex3f(256, -50, 0);
		myGL.glTexCoord2f(0, 1);
		myGL.glVertex3f(256,-50, 256);
		myGL.glTexCoord2f(1, 1);
		myGL.glVertex3f(256,120, 256);
		myGL.glTexCoord2f(1, 0);
		myGL.glVertex3f(256, 120, 0);

		wall1.disable();

		wall1.enable();
		wall1.bind();
		myGL.glTexCoord2f(0, 0);
		myGL.glVertex3f(0, 120, 0);
		myGL.glTexCoord2f(0, 1);
		myGL.glVertex3f(256,120, 0);
		myGL.glTexCoord2f(1, 1);
		myGL.glVertex3f(256, 120, 256);
		myGL.glTexCoord2f(1, 0);
		myGL.glVertex3f(0,120, 256);

		wall1.disable();

		myGL.glEnd();

		myGL.glDisable(GL.GL_TEXTURE_2D);
		//myGL.glEnable(GL.GL_COLOR_MATERIAL);
	}

	public void loadVertexData(File myFile){
		ArrayList<Float> x = new ArrayList<Float>();
		FileInputStream myStream =null;
		try{ myStream = new FileInputStream(myFile);
		}catch (FileNotFoundException ex){
			System.out.println("No File Here!");
			System.exit(0);//end process
		}
		int count =0;
		Scanner myScanner = new Scanner(myStream);
		while (myScanner.hasNext()){
			count++;
			String temp;
			//float f1, f2, f3;
			temp = myScanner.nextLine().trim();
			if (temp.substring(0,1).equals("v") && count>36){
				//String one = temp.substring(2,temp.indexOf(' '));
				temp = temp.substring(2,temp.length());
				int firstSpace = temp.indexOf(" ");
				//TODO fix here
				int secondSpace = temp.lastIndexOf(" ");
				x.add(Float.parseFloat(temp.substring(0,firstSpace)));
				x.add(Float.parseFloat(temp.substring(firstSpace+1,secondSpace)));
				x.add(Float.parseFloat(temp.substring(secondSpace+1,temp.length())));
			}
		}


		weaponVert = BufferUtil.newFloatBuffer(x.size());
		for(Float f: x){
			weaponVert.put(f);
			//	System.out.println(f);
		}
		weaponVert.rewind();
	}

	public void drawWeapon(GL myGL){
		//	myGL.glBegin(GL.GL_TRIANGLES);
		myGL.glColor3f(0.8f, 0.8f, 0.8f);


		//	FloatBuffer colors = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));
		//	FloatBuffer norms = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));

		//	myGL.glEnableClientState(GL.GL_COLOR_ARRAY);
		myGL.glEnableClientState(GL.GL_VERTEX_ARRAY);

		//	myGL.glNormalPointer(GL.GL_FLOAT, 0, vert);
		//	myGL.glColorPointer(3, GL.GL_FLOAT, 0, colors);
		myGL.glVertexPointer(3, GL.GL_FLOAT, 0, weaponVert);

		myGL.glDrawArrays(GL.GL_TRIANGLE_STRIP,0,weaponVert.capacity()/3);

		//disable arrays when done
		myGL.glDisableClientState(GL.GL_VERTEX_ARRAY);

		/*	myGL.glVertex3d(0,0,0);
		myGL.glVertex3d(0,10,0);
		myGL.glVertex3d(10,0,0);
		myGL.glVertex3d(10,10,0);*/

		myGL.glEnd();
	}

	public void loadTextures(GL myGL){
		myGL.glTexParameterf(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR );
		myGL.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
		myGL.glGenerateMipmapEXT(GL.GL_TEXTURE_2D);
		//myGL.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_BORDER_COLOR, GL.GL_REPEAT);
		//	myGL.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
		try{
			//load a TextureData object from a picture and then create a texture from it
			cross =TextureIO.newTexture(new File("crosshairwhite.png"),true);
			wall1 =TextureIO.newTexture(new File("mount.jpg"),true);
			wall2 =TextureIO.newTexture(new File("wal2.jpg"),true);
			wall3 =TextureIO.newTexture(new File("mount.jpg"),true);
			wall4 =TextureIO.newTexture(new File("mount.jpg"),true);
			sky =TextureIO.newTexture(new File("mount.jpg"),true);
		}//end try
		catch(IOException e){
			System.out.println("File not found!");
			System.exit(1);
		}//end catch
		catch(GLException f){
			System.exit(1);
		}//end catch

	}//end loadTextures
	
	public void drawEnemy(){
		//GLUT glut = new GLUT();
		GL myGL = getGL();
		myGL.glPushMatrix();
		double x = 256*Math.random();
		double y = 256*Math.random();
		myGL.glTranslated(x, World.getMap()[(int)x][(int)y], y);
		
		myGL.glColor3d(1, 0, 0);
		
		myGL.glBegin(GL.GL_TRIANGLES);
		
		myGL.glVertex3d(0,40,0);
		myGL.glVertex3d(0,-40,0);
		myGL.glVertex3d(0,0,40);
		
	//glut.glutSolidCube(3);
		myGL.glPopMatrix();
		myGL.glEnd();
		//TODO fix this
		//System.out.println("test");
	}


}
