package engine;

public class Eye {
	
	public float atX = 0;
	public float atY = 0;
	public float atZ = 0;
	public float lookX = 0;
	public float lookY = 0;
	public float lookZ = 0;
	public float height = 0;
	public float force = 0;
	
	public Eye() {
	}
	
	public void setPosition(float x, float y, float z) {
		atX = x;
		atY = y;
		atZ = z;
	}
	
	public void setView(float x, float y, float z) {
		lookX = x;
		lookY = y;
		lookZ = z;
	}
	
	public void setHeight(double h) {
		height = (float) h;
	}
	
	public void setForce(float f) {
		force = f;
	}
	
	public float getXPosition() {
		return atX;
	}
	
	public float getYPosition() {
		return atY;
	}
	
	public float getZPosition() {
		return atZ;
	}
	
	public float getXView() {
		return lookX;
	}
	
	public float getYView() {
		return lookY;
	}
	
	public float getZView() {
		return lookZ;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getForce() {
		return force;
	}
}