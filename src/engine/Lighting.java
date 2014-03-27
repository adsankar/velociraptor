
package engine;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
/**
 * Sets up the lighting
 * @author 930983
 *
 */
public class Lighting {
	/**
	 * Sets up lighting within the 3D environment
	 * @param glad
	 */
	public static void light(GLAutoDrawable glad) {
		GL myGL = glad.getGL();
		myGL.glEnable(GL.GL_DEPTH_TEST); 
		myGL.glDepthFunc(GL.GL_LEQUAL);
		myGL.glEnable(GL.GL_LIGHTING);
		
		myGL.glEnable(GL.GL_FOG);
		myGL.glFogi (GL.GL_FOG_MODE, GL.GL_EXP2);
		myGL.glFogf (GL.GL_FOG_DENSITY, .02f);
		myGL.glFogfv(GL.GL_FOG_COLOR, new float[]{0,0,0, 1}, 0);
		myGL.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);
		float ambient[]= {0.5f, 0.5f, 0.5f, 1};
		myGL.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT , ambient, 0);
		myGL.glEnable(GL.GL_LIGHT0);
		float position[]= {-0.4f, 0.5f, 0.7f, 1};
		myGL.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
		float intensity[]= {1, 1, 1, 1};
		myGL.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, intensity, 0);
		myGL.glEnable(GL.GL_LIGHT1);
		float position2[]= {0, -0.8f, 0.3f, 1};
		myGL.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position2, 0);
		float intensity2[]= {1, 0, 0, 0};
		myGL.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, intensity2, 0);
		float specIntensity2[]= {1, 1, 1, 1};
		myGL.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specIntensity2, 0);
		myGL.glEnable(GL.GL_COLOR_MATERIAL);
		myGL.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
	}
}
