package engine;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class World {
	
	float space;
	Map map;
	int size = 100;
	
	public World() {
		space = 1;
		size++;
		map = new Map(size);
	}
	
	public void makeWorld(GLAutoDrawable glad) {
		GL gl = glad.getGL();
		drawColoredTerrain(gl);
	}
	
	public double[] calcColor(double color) {
		if (color < 0.3)
			return new double[] {0.2, 0.2, 0.5 + color};
		if (color < 0.55)
			return new double[] {color * 3 / 4, 1 * color, 3 / 4 * color};
		return new double[] {1.2 * color, color, color * 6/7};
	}
	
	public void drawColoredTerrain(GL gl){
		
		double[][] hmap = map.getMap();
		int dimension = hmap.length;
		double scale = 	4 * Math.pow(dimension, .5);
		gl.glPointSize(.002f);
		gl.glColor3f(1, 1, 1);
		gl.glBegin(GL.GL_QUADS);
		double threshold = 0.3;
		
		for (int i = 0; i < dimension - 1; i++) {
			for (int j = 0; j < dimension - 1; j++){
				if ((hmap[i][j] + scale / 2) / scale < threshold) {
					gl.glColor3dv(calcColor((hmap[i][j] + scale / 2) / scale), 0);
					gl.glVertex3d(i, threshold * scale - scale / 2, j);
				}
				else {
					gl.glColor3dv(calcColor((hmap[i][j] + scale / 2) / scale), 0);
					gl.glVertex3d(i, hmap[i][j], j);
				}
				if ((hmap[i][j + 1] + scale / 2) / scale < threshold) {
					gl.glColor3dv(calcColor((hmap[i][j + 1] + scale / 2) / scale), 0);
					gl.glVertex3d(i, threshold * scale - scale / 2, j + 1);
				}
				else {
					gl.glColor3dv(calcColor((hmap[i][j + 1] + scale / 2) / scale), 0);
					gl.glVertex3d(i, hmap[i][j + 1], j + 1);
				}
				if ((hmap[i + 1][j + 1] + scale / 2) / scale < threshold) {
					gl.glColor3dv(calcColor((hmap[i + 1][j + 1] + scale / 2) / scale), 0);
					gl.glVertex3d(i + 1, threshold * scale - scale / 2, j + 1);
				}
				else {
					gl.glColor3dv(calcColor((hmap[i + 1][j + 1] + scale / 2) / scale), 0);
					gl.glVertex3d(i + 1, hmap[i + 1][j + 1], j + 1);
				}
				if ((hmap[i + 1][j] + scale / 2) / scale < threshold) {
					gl.glColor3dv(calcColor((hmap[i + 1][j] + scale / 2) / scale), 0);
					gl.glVertex3d(i + 1, threshold * scale - scale / 2, j);
				}
				else {
					gl.glColor3dv(calcColor((hmap[i + 1][j] + scale / 2) / scale), 0);
					gl.glVertex3d(i + 1, hmap[i + 1][j], j);
				}
			}
		}
		gl.glEnd();
	}
	
	public double getAverage() {
		return map.getAverage();
	}
	
	public double[][] getMap() {
		return map.getMap();
	}
}