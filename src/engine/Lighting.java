package engine;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Lighting {
	
	public static void light(GLAutoDrawable glad) {
		GL gl = glad.getGL();
		gl.glEnable(GL.GL_DEPTH_TEST); 
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glEnable(GL.GL_LIGHTING);
		float ambient[]= {0.5f, 0.5f, 0.5f, 1};
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT , ambient, 0);
		gl.glEnable(GL.GL_LIGHT0);
		float position[]= {-0.4f, 0.5f, 0.7f, 1};
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
		float intensity[]= {1, 1, 1, 1};
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, intensity, 0);
		gl.glEnable(GL.GL_LIGHT1);
		float position2[]= {0, -0.8f, 0.3f, 1};
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position2, 0);
		float intensity2[]= {1, 0, 0, 0};
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, intensity2, 0);
		float specIntensity2[]= {1, 1, 1, 1};
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specIntensity2, 0);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
	}
}