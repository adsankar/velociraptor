package test;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

/**
 * Runner for the basic GL exercises
 * @author Aleksander Sankar
 * 3D Pd. 2
 *
 */
public class SankarRunner {

	private Animator an;
	
	public static void main(String[] args) {
		new SankarRunner();

	}
	
	public SankarRunner(){
		Frame f = new Frame("It's shapes");
		GLCapabilities glCap = new GLCapabilities();
		SankarRotateSq gls = new SankarRotateSq(glCap);
		f.add(gls);
		
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}//end window closing event
		});
		f.setBackground(Color.gray);
		f.setSize(800,800);
		an = new FPSAnimator(gls,60);
		an.start();
		
		
		f.setVisible(true);
	}
/* Comment Questions
 * The parameters to gl.glVertex2f are relative to the center of the panel and to the s.
 * 
 * The two matrices in OpenGL are the model matrix and the projection matrix.
 * The model matrix controls all of the information about the  of the shapes and the projection matrix changes the perspective.
 * 
 */
}//end class
