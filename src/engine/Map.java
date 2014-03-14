package engine;

public class Map {
	
	private double[][] map;
	
	public Map(int size) {
		map = new double[size][size];
		for(int x = 0; x < size; x++)
			for(int y = 0; y < size; y++)
				map[x][y] = 0;
		generateFlatHmap(size + 1, 4);
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
}