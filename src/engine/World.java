package engine;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.BufferUtil;

public class World {

	Map map;
	int size = 128;
	private FloatBuffer vert;
	private FloatBuffer colors;
	private FloatBuffer norms;
	private float[] nextNormals = new float[3];

	public World() {
		map = new Map(size);
		
	}

	public void makeWorld(GLAutoDrawable glad) {
		GL gl = glad.getGL();
		setupArrays();
		drawMap(gl);
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
	/**
	 * Add the vertices of the appropriate shapes to the vertex, color and normal arrays.
	 */
	public void setupArrays(){
		double[][] height = map.getMap();
		//set up the float buffers for each one
		vert = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));
		colors = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));
		norms = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));


		for(int i = 0 ; i < height.length-1 ; i++){
			for(int j = 0 ; j < height[0].length-1 ; j++){

				//almost the same procedure as before
				vert.put(i);
				vert.put((float) height[i][j]);
				vert.put(j);
				
				getColor(height[i][j]);

				vert.put(i+1);
				vert.put((float) height[i+1][j]);
				vert.put(j);
				
				getColor(height[i+1][j]);

				vert.put(i+1);
				vert.put((float) height[i+1][j+1]);
				vert.put(j+1);
				
				getColor(height[i+1][j+1]);

				//calculate surface normals and add them to the normals array
				calculateNormal(i, j, height[i][j], i+1, j, height[i+1][j], i+1, j+1, height[i+1][j+1]);
				norms.put(nextNormals[0]);
				norms.put(nextNormals[1]);
				norms.put(nextNormals[2]);

				vert.put(i);
				vert.put((float) height[i][j+1]);
				vert.put(j+1);
				
				getColor(height[i][j+1]);

			}//end for
		}//end for

		vert.rewind();
		colors.rewind();
		norms.rewind();
	}// end setupArrays

	/**
	 * Calculate the surface normal given two lines, defined by a total of 9 points 
	 * @param x1 the first x coordinate
	 * @param y1 the first y coordinate
	 * @param z1 the first z coordinate
	 * @param x2 the second x coordinate
	 * @param y2 the second y coordinate
	 * @param z2 the second z coordinate
	 * @param x3 the third x coordinate
	 * @param y3 the third y coordinate
	 * @param z3 the third z coordinate
	 */
	public void calculateNormal(double x1, double y1, double z1,
			double x2, double y2, double z2, double x3, double y3, double z3){
		//cross product calculation
		nextNormals[0] = (float) ((y2-y1)*(z3-z1)-(z2-z1)*(y3-y1));
		nextNormals[1] = (float) ((z2-z1)*(x3-x1)-(x2-x1)*(z3-z1)); 
		nextNormals[2] = (float) ((x2-x1)*(y3-y1)-(y2-y1)*(x3-x1));

	}//end calculateNormal

	/**
	 * Set the color so that it corresponds to the height
	 * @param height the height for which the color is calculated for
	 */
	public void getColor(double height){
		if(height < 0){//highest "snow"
			colors.put(1f);
			colors.put(1f);
			colors.put(1f);
		}else if( height < 30){//in the middle, green "forest"
			colors.put(0f);
			colors.put(.5f);
			colors.put(0f);
		}else{//lowest "body of water"
			colors.put(0f);
			colors.put(0.3f);
			colors.put(1f);
		}// end else
	}//end getColor

	/**
	 * Draw the terrain map using vertex arrays
	 * @param myGL the GL being used
	 */
	public void drawMap(GL myGL){
		//enable the arrays
		myGL.glEnableClientState(GL.GL_COLOR_ARRAY);
		myGL.glEnableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glEnableClientState(GL.GL_NORMAL_ARRAY);

		//designate the pointers
		myGL.glNormalPointer(GL.GL_FLOAT, 0, norms);
		myGL.glColorPointer(3, GL.GL_FLOAT, 0, colors);
		myGL.glVertexPointer(3, GL.GL_FLOAT, 0, vert);
		myGL.glDrawArrays(GL.GL_QUADS,0,vert.capacity()/3);

		//disable arrays when done
		myGL.glDisableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glDisableClientState(GL.GL_COLOR_ARRAY);
		myGL.glDisableClientState(GL.GL_NORMAL_ARRAY);
	}//end drawMap



	public double[][] getMap() {
		return map.getMap();
	}
}