package engine;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

/**
 * 
 * @author Aleksander
 *
 */
public class World {

	private static Map map;
	private int size = 256;
	private FloatBuffer vert;
	private FloatBuffer colors;
	private FloatBuffer norms;
	private float[] nextNormals = new float[3];
	private final float TILE_SCALE = 1f;

	/**
	 * 
	 */
	public World() {
		map = new Map(size);

	}

	/**
	 * 
	 * @param myGL
	 */
	public void makeWorld(GL myGL) {
		setupArrays();
		drawMap(myGL);
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
		Vector3 normal = new Vector3();
		Vector3 a, b, c, d, e;

		//TODO, smoother map by repetition, each vertex (length and width-1)
		for(int i = 0 ; i < height.length-1 ; i++){
			for(int j = 0 ; j < height[0].length-1 ; j++){

				if (i+2<height.length && j+2<height.length && i>0 && j>0){
					a = new Vector3(i-1,j, (float)(height[i-1][j]));
					b = new Vector3(i, j-1, (float)(height[i][j-1]));
					c = new Vector3(i, j, (float)(height[i][j]));
					d = new Vector3(i+1, j, (float)(height[i+1][j]));
					e = new Vector3(i, j+1, (float)(height[i][j+1]));
					normal = Vector3.calcAvgNormal(a, b, c, d, e);
					//normal = Vector3.normalize(normal);
					norms.put(normal.getX());
					norms.put(normal.getY());
					norms.put(normal.getZ());

					//i+1
					a = new Vector3(i,j, (float)(height[i][j]));
					b = new Vector3(i+1, j-1, (float)(height[i+1][j-1]));
					c = new Vector3(i+1, j, (float)(height[i+1][j]));
					d = new Vector3(i+2, j, (float)(height[i+2][j]));
					e = new Vector3(i+1, j+1, (float)(height[i+1][j+1]));
					normal = Vector3.calcAvgNormal(a, b, c, d, e);
					//normal = Vector3.normalize(normal);
					norms.put(normal.getX());
					norms.put(normal.getY());
					norms.put(normal.getZ());

					//i+1, j+1
					a = new Vector3(i,j+1, (float)(height[i][j+1]));
					b = new Vector3(i+1, j, (float)(height[i+1][j]));
					c = new Vector3(i+1, j+1, (float)(height[i+1][j+1]));
					d = new Vector3(i+2, j+1, (float)(height[i+2][j+1]));
					e = new Vector3(i+1, j+2, (float)(height[i+1][j+2]));
					normal = Vector3.calcAvgNormal(a, b, c, d, e);
					//normal = Vector3.normalize(normal);
					norms.put(normal.getX());
					norms.put(normal.getY());
					norms.put(normal.getZ());

					//j+1
					a = new Vector3(i-1,j+1, (float)(height[i-1][j+1]));
					b = new Vector3(i, j, (float)(height[i][j]));
					c = new Vector3(i, j+1, (float)(height[i][j+1]));
					d = new Vector3(i+1, j+1, (float)(height[i+1][j+1]));
					e = new Vector3(i, j+2, (float)(height[i][j+2]));
					normal = Vector3.calcAvgNormal(a, b, c, d, e);
					//normal = Vector3.normalize(normal);
					norms.put(normal.getX());
					norms.put(normal.getY());
					norms.put(normal.getZ());


					/*calculateNormal(i, j, height[i][j], i+1, j, height[i+1][j], i, j+1, height[i][j+1]);
				norms.put(nextNormals[0]);
				norms.put(nextNormals[1]);
				norms.put(nextNormals[2]);

				calculateNormal(i+1, j, height[i+1][j], i+2, j, height[i+2][j], i+1, j+1, height[i+1][j+1]);
				norms.put(nextNormals[0]);
				norms.put(nextNormals[1]);
				norms.put(nextNormals[2]);



				calculateNormal(i+1, j+1, height[i+1][j+1], i+2, j+1, height[i+2][j+1], i+2, j+2, height[i+2][j+2]);
				norms.put(nextNormals[0]);
				norms.put(nextNormals[1]);
				norms.put(nextNormals[2]);

				//calculate surface normals and add them to the normals array
				calculateNormal(i, j+1, height[i][j+1], i+1, j+1, height[i+1][j+1], i+1, j+2, height[i+1][j+2]);
				norms.put(nextNormals[0]);
				norms.put(nextNormals[1]);
				norms.put(nextNormals[2]);*/
				}
				else {
					//the border
					//calculateNormal(x1, y1, z1, x2, y2, z2, x3, y3, z3);
					norms.put(1);
					norms.put(1);
					norms.put(1);
				}

				//almost the same procedure as before
				vert.put(i*TILE_SCALE);
				vert.put((float) height[i][j]);
				vert.put(j*TILE_SCALE);

				getColor(height[i][j]); 




				/*	if (i >0 && i<height.length+1 && j>0 && j<height.length+1){
				Vector3 a = new Vector3(0,0, (float)(height[i][j]));
				Vector3 b = new Vector3(0, 1, (float)(height[i][j+1]));
				Vector3 c = new Vector3(1, 0, (float)(height[i+1][j]));
				Vector3 d = new Vector3(0, -1, (float)(height[i][j-1]));
				Vector3 e = new Vector3(-1, 0, (float)(height[i-1][j]));
				normal = Vector3.calcAvgNormal(a, b, c, d, e);

//TODO fix normals
				if (isInRange(j+1)&&isInRange(i+1)){
					Vector3 a = new Vector3(i,j+1,(int)height[i][j+1]);
					Vector3 b = new Vector3(i+1,j,(int)height[i+1][j]);
					//normal = Vector3.cross(a, b);
					normal = calcAvcalcAvgNormal()

				}
				else normal = new Vector3();

				//calculateNormal(i, j, height[i][j], i+1, j, height[i+1][j], i+1, j+1, height[i+1][j+1]);


				norms.put(normal.getX());
				norms.put(normal.getY());
				norms.put(normal.getZ());*/

				vert.put((i+1)*TILE_SCALE);
				vert.put((float) height[i+1][j]);
				vert.put(j*TILE_SCALE);

				getColor(height[i+1][j]);


				/*if (isInRange(j+1)&&isInRange(i+1)){ 
					Vector3 a = new Vector3(i-1,j,(int)height[i-1][j]);
					Vector3 b = new Vector3(i-1,j+1,(int)height[i-1][j+1]);
					normal = Vector3.cross(a, b);
				}
				else normal = new Vector3();*/


				/*norms.put(normal.getX());
				norms.put(normal.getY());
				norms.put(normal.getZ());
				 */
				vert.put((i+1)*TILE_SCALE);
				vert.put((float) height[i+1][j+1]);
				vert.put((j+1)*TILE_SCALE);

				getColor(height[i+1][j+1]);



				/*	norms.put(normal.getX());


				if (isInRange(j+1)&&isInRange(i+1)){
					Vector3 a = new Vector3(i-1,j-1,(int)height[i-1][j-1]);
					Vector3 b = new Vector3(i,j-1,(int)height[i][j-1]);
					normal = Vector3.cross(a, b);
				}
				else normal = new Vector3();

				norms.put(normal.getX());

				norms.put(normal.getY());
				norms.put(normal.getZ());*/

				vert.put(i*TILE_SCALE);
				vert.put((float) height[i][j+1]);
				vert.put((j+1)*TILE_SCALE);

				getColor(height[i][j+1]);



				/*	norms.put(normal.getX());

				if (isInRange(j+1)&&isInRange(i+1)){
					Vector3 a = new Vector3(i-1,j,(int)height[i-1][j]);
					Vector3 b = new Vector3(i+1,j-1,(int)height[i+1][j-1]);
					normal = Vector3.cross(a, b);
				}
				else normal = new Vector3();

				norms.put(normal.getX());

				norms.put(normal.getY());
				norms.put(normal.getZ());*/

			}//end for
		}//end for

		vert.rewind();
		colors.rewind();
		norms.rewind();

		for (int i=0; i<norms.capacity()-9; i+=6){
			norms.put(i, (norms.get(i)+norms.get(i+3)+norms.get(i+6))/3);
			norms.put(i+1, (norms.get(i+1)+norms.get(i+4)+norms.get(i+7))/3);
			norms.put(i+2, (norms.get(i+2)+norms.get(i+5)+norms.get(i+8))/3);
		}
	}// end setupArrays

	public boolean isInRange(int a){
		if (a>1 && a<size-1 ){
			return true;
		}

		else return false;
	}

	/**
	 * Calculate the surface normal given two lines, defined by a total of 9 values
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
		if(height < 0){ //lowest "body of water"
			colors.put(0f);
			colors.put(0.3f);
			colors.put(1f);
		}else if( height < 30){//in the middle, green "forest"
			colors.put(0f);
			colors.put(.5f);
			colors.put(0f);
		}else{//highest "snow"
			colors.put(1f);
			colors.put(1f);
			colors.put(1f);
		}// end else
	}//end getColor

	/**
	 * Draw the terrain map using vertex arrays
	 * @param myGL the GL being used
	 */
	public void drawMap(GL myGL){
		//enable the arrays
		myGL.glEnable(GL.GL_NORMALIZE);
		myGL.glEnableClientState(GL.GL_COLOR_ARRAY);
		myGL.glEnableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glEnableClientState(GL.GL_NORMAL_ARRAY);

		//designate the pointers
		myGL.glNormalPointer(GL.GL_FLOAT, 0, norms);
		myGL.glColorPointer(3, GL.GL_FLOAT, 0, colors);
		myGL.glVertexPointer(3, GL.GL_FLOAT, 0, vert);

		//myGL.glDrawArrays(GL.GL_LINES,0,vert.capacity()/3);
		myGL.glDrawArrays(GL.GL_QUADS,0,vert.capacity()/3);

		//disable arrays when done
		myGL.glDisableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glDisableClientState(GL.GL_COLOR_ARRAY);
		myGL.glDisableClientState(GL.GL_NORMAL_ARRAY);
	}//end drawMap


	/**
	 * 
	 * @return map
	 */
	public static double[][] getMap() {
		return map.getMap();
	}
}
