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
		Vector3 normal;

		//TODO, smoother map by repetition, each vertex (length and width-1)
		for(int i = 0 ; i < height.length-1 ; i++){
			for(int j = 0 ; j < height[0].length-1 ; j++){

				//almost the same procedure as before
				vert.put(i*TILE_SCALE);
				vert.put((float) height[i][j]);
				vert.put(j*TILE_SCALE);

				getColor(height[i][j]); 
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
				norms.put(normal.getZ());

				vert.put((i+1)*TILE_SCALE);
				vert.put((float) height[i+1][j]);
				vert.put(j*TILE_SCALE);

				getColor(height[i+1][j]);


				if (isInRange(j+1)&&isInRange(i+1)){ 
					Vector3 a = new Vector3(i-1,j,(int)height[i-1][j]);
					Vector3 b = new Vector3(i-1,j+1,(int)height[i-1][j+1]);
					normal = Vector3.cross(a, b);
				}
				else normal = new Vector3();
				
				norms.put(normal.getX());
				norms.put(normal.getY());
				norms.put(normal.getZ());

				vert.put((i+1)*TILE_SCALE);
				vert.put((float) height[i+1][j+1]);
				vert.put((j+1)*TILE_SCALE);

				getColor(height[i+1][j+1]);


				if (isInRange(j+1)&&isInRange(i+1)){
					Vector3 a = new Vector3(i-1,j-1,(int)height[i-1][j-1]);
					Vector3 b = new Vector3(i,j-1,(int)height[i][j-1]);
					normal = Vector3.cross(a, b);
				}
				else normal = new Vector3();
				
				norms.put(normal.getX());
				norms.put(normal.getY());
				norms.put(normal.getZ());

				vert.put(i*TILE_SCALE);
				vert.put((float) height[i][j+1]);
				vert.put((j+1)*TILE_SCALE);

				getColor(height[i][j+1]);

				if (isInRange(j+1)&&isInRange(i+1)){
					Vector3 a = new Vector3(i-1,j,(int)height[i-1][j]);
					Vector3 b = new Vector3(i+1,j-1,(int)height[i+1][j-1]);
					normal = Vector3.cross(a, b);
				}
				else normal = new Vector3();
				
				norms.put(normal.getX());
				norms.put(normal.getY());
				norms.put(normal.getZ());

			}//end for
		}//end for

		vert.rewind();
		colors.rewind();
		norms.rewind();
		
		//for (int)
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