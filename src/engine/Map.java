package engine;

public class Map {

	private double[][] map;

	public Map(int size) {
		map = new double[size][size];
		for(int x = 0; x < size; x++)
			for(int y = 0; y < size; y++)
				map[x][y] = 0;
		generateFlatHmap(size + 1, 4);
		//makeHeightMap(iterations, seed1, seed2, seed3, seed4, variation)
	}

	public void generateFlatHmap(int dimension, double roughness) {
		map = new double[dimension][dimension];
		roughness = roughness * Math.pow(dimension, .5);
		double[][] currentHmap = new double[2][2];
		generateHmap(dimension, roughness, currentHmap);
	}

	public void generateHmap(int dimension, double roughness, double[][] currentHmap) {
		map = new double[dimension][dimension];
		int currentDim = currentHmap.length;		
		while (currentDim < dimension) {
			currentDim = 2 * currentDim - 1;
			currentHmap = fractalize(currentHmap, roughness);
			roughness = roughness * 1 / 2;
		}

		for (int i = 0; i < dimension; i++){
			for (int j = 0; j < dimension; j++) {
				map[i][j] = currentHmap[i][j];
			}
		}
	}

	public double[][] fractalize(double[][] map, double roughness) {
		int dimension = map.length * 2 - 1;
		double[][] finalHmap = new double[dimension][dimension];

		for (int i = 0; i < dimension; i += 2) {
			for (int j = 0; j < dimension; j += 2) {
				finalHmap[i][j] = map[i / 2][j / 2];
			}
		}

		for (int i = 1; i < dimension; i += 2) {
			for (int j = 1; j < dimension; j += 2) {
				double sum = 0;
				sum += finalHmap[i - 1][j - 1];
				sum += finalHmap[i + 1][j - 1];
				sum += finalHmap[i + 1][j + 1];
				sum += finalHmap[i - 1][j + 1];
				finalHmap[i][j] = sum / 4 + roughness * Math.random() - roughness / 2;
			}
		}

		for (int i = 0; i < dimension; i++) {
			for (int j = 1; j < dimension; j += 2) {
				double sum = 0;
				int count = 0;
				if (j > 0) {
					sum += finalHmap[i][j - 1];
					count++;
				}
				if (j < dimension - 1) {
					sum += finalHmap[i][j + 1];
					count++;
				}
				finalHmap[i][j] = sum / count + roughness * Math.random() - roughness / 2;
			}
		}

		for (int i = 1; i < dimension; i += 2) {
			for (int j = 0; j<dimension; j++) {
				double sum = 0;
				int count = 0;
				if (i > 0) {
					sum += finalHmap[i - 1][j];
					count++;
				}
				if (i < dimension - 1)	{
					sum += finalHmap[i + 1][j]; 
					count++;
				}
				finalHmap[i][j] = sum / count + roughness * Math.random() - roughness / 2;
			}
		}

		return finalHmap;
	}

	public double[][] getMap() {
		return map;
	}

	public double getPoint(int x, int z) {
		return map[x][z];
	}

	public double getAverage() {
		double average = 0;
		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				average += map[i][j];
		average /= (map.length * map.length);
		return average;
	}
	/********************************************************************?
	 */

	/**
	 * The first of the square step parts of the diamond-square algorithm for generating the terrain
	 * @param min the minimum value
	 * @param map the map being used
	 * @param size the size of the map
	 * @param max the maximum height
	 * @param variation the maximum variation allowed
	 */
	public static void firstSquareStep(int min, double[][] map, int size, int max, double variation) {
		for (int x = min; x < map.length; x += size) {
			for (int y = 0; y < map.length; y += size) {
				if (y == max) {
					map[x][y] = map[x][0];
					continue;
				}// end if

				int left = x - min;
				int right = x + min;
				int down = y + min;
				int up = 0;

				if (y == 0) {
					up = max - min;
				} else {
					up = y - min;
				}//end else

				// the four corner values
				double val1 = map[left][y]; // left
				double val2 = map[x][up];   // up
				double val3 = map[right][y];// right
				double val4 = map[x][down]; // down

				midpointOffset(val1, val2, val3, val4, variation,
						map, x, y);
			}//end for
		}//end for
	}//end 

	/**
	 * The diamond part of the diamond-square algorithm for generating the terrain
	 * @param min the minimum value
	 * @param size the size of the map used
	 * @param map the map used
	 * @param variation the maximium variation allowed
	 */
	public static void diamondStep(int min, int size, double[][] map, double variation) {
		for (int x = min; x < (map.length - min); x += size) {
			for (int y = min; y < (map.length - min); y += size) {
				int left = x - min;
				int right = x + min;
				int up = y - min;
				int down = y + min;

				// the four corner values
				double val1 = map[left][up];   // upper left
				double val2 = map[left][down]; // lower left
				double val3 = map[right][up];  // upper right
				double val4 = map[right][down];// lower right

				midpointOffset(val1, val2, val3, val4, variation,
						map, x, y);
			}//end for
		}//end for
	}//end diamondStep

	/**
	 * Process for generating the final height map using the diamond-square algorithm
	 * @param iterations the number of passes
	 * @param seed1 the first corner seed value
	 * @param seed2 the second corner seed value
	 * @param seed3 the third corner seed value
	 * @param seed4 the fourth corner seed value
	 * @param variation the maximum variation allowed
	 * @return the completed height map
	 */
	public static double[][] makeHeightMap(int iterations, double seed1, double seed2, double seed3, double seed4, double variation) {
		if (iterations < 1 || variation < 0) {
			return null;
		}// end if

		int size = (1 << iterations) + 1;
		double[][] map = new double[size][size];
		final int maxIndex = map.length - 1;

		// seed the corners
		map[0][0] = seed1;
		map[0][maxIndex] = seed2;
		map[maxIndex][0] = seed3;
		map[maxIndex][maxIndex] = seed4;

		for (int i = 1; i <= iterations; i++) {
			int min = maxIndex >> i;// Minimum coordinate of the current map spaces
		size = min << 1;// Area surrounding the current place in the map

		diamondStep(min, size, map, variation);
		firstSquareStep(min, map, size, maxIndex, variation);
		secondSquareStep(map, size, min, maxIndex, variation);

		variation/=2;// Divide variation by 2
		}//end for

		//the final iteration
		map[0][0] = (map[0][1]+map[1][1]+map[1][0])/3;
		map[0][maxIndex] = (map[0][maxIndex-1]+map[1][maxIndex]+map[1][maxIndex-1])/3;
		map[maxIndex][0] = (map[maxIndex][1]+map[maxIndex-1][1]+map[maxIndex-1][0])/3;
		map[maxIndex][maxIndex] = (map[maxIndex-1][maxIndex-1]+map[maxIndex-1][maxIndex]+map[maxIndex][maxIndex-1])/3;

		return map;
	}//end makeHeightMap

	/**
	 * Offset the middle of a segment so that variation is produced in the height map
	 * @param seed1 the first corner seed value
	 * @param seed2 the second corner seed value
	 * @param seed3 the third corner seed value
	 * @param seed4 the fourth corner seed value
	 * @param variation
	 * @param map the map being used
	 * @param x the x coordinate of where you are
	 * @param y the y coordinate of where you are
	 */
	public static void midpointOffset(double seed1, double seed2, double seed3,
			double seed4, double variation, double[][] map, int x, int y) {
		double avg = (seed1 + seed2 + seed3 + seed4)/4;// average
		double var = (betterRandom() * variation);//random-valued offset
		map[x][y] = avg + var;
	}//end midpointOffset

	/**
	 * The second of the square step parts of the diamond-square algorithm for generating the terrain
	 * @param map the map being used 
	 * @param size the size of the map
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param variation the maximum variation allowed
	 */
	public static void secondSquareStep(double[][] map, int size, int min,
			int max, double variation) {
		for (int x = 0; x < map.length; x += size) {
			for (int y = min; y < map.length; y += size) {
				if (x == max) {
					map[x][y] = map[0][y];
					continue;
				}// end if

				int left = 0;
				int right = x + min;
				int down = y + min;
				int up = y - min;

				if (x == 0) {
					left = max - min;
				} else {
					left = x - min;
				}// end else

				// the four corner values
				double corner1 = map[left][y]; // left
				double corner2 = map[x][up];   // up
				double corner3 = map[right][y];// right
				double corner4 = map[x][down]; // down

				midpointOffset(corner1, corner2, corner3, corner4, variation,
						map, x, y);
			}//end for
		}//end for
	}//end 

	/**
	 * Give a random number between positive and negative 1
	 * @return the random value
	 */
	public static double betterRandom() {
		double sign = Math.random();
		if (sign>0.5){
			return -Math.random();
		}// end if
		return Math.random();
	}//end betterRandom

	
}