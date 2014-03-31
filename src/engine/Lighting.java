
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
	 * @param myGL
	 */
	public static void light(GL myGL) {
	
		myGL.glEnable(GL.GL_DEPTH_TEST); 
		myGL.glDepthFunc(GL.GL_LEQUAL);
		myGL.glEnable(GL.GL_LIGHTING);
		
		myGL.glEnable(GL.GL_NORMALIZE);
		myGL.glEnable (GL.GL_BLEND); 
		myGL.glEnable (GL.GL_POLYGON_SMOOTH); 
		myGL.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST); 
		myGL.glShadeModel(GL.GL_SMOOTH);
		myGL.glDisable(GL.GL_COLOR_MATERIAL);
		myGL.glEnable(GL.GL_LIGHT0);
		
		float ambient[]= {0.2f, 0.2f, 0.2f, 1};
		float diffuse[] = {0.8f,0.8f,0.8f,1};
		float specular[]= {1, 1, 1, 1};
		
		
		myGL.glEnable(GL.GL_FOG);
		myGL.glFogi (GL.GL_FOG_MODE, GL.GL_EXP2);
		myGL.glFogf (GL.GL_FOG_DENSITY, .02f);
		myGL.glFogfv(GL.GL_FOG_COLOR, new float[]{0,0,0, 1}, 0);
		myGL.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);
		
		myGL.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT , ambient, 0);
		
		
		
		myGL.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
		myGL.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
	
		myGL.glEnable(GL.GL_COLOR_MATERIAL);
		myGL.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
	}
	
	public static void setLightPosition(GL myGL, float[] pos){
		myGL.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
	}
}
