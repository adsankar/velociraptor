package engine;

public class MouseFunctions {
	
	public float mx;
	public float my;
	public float speed = 1;
	
	public MouseFunctions() {
	}
	
	public void setSpeed(float f) {
		speed = f;
	}
	
	public void setMouse(float x, float y) {
		mx = x;
		my = y;
	}
	
	public Eye moveEye(Eye eye) {
		eye.setView((float) (eye.getXPosition() + (Math.sin(mx * .01f) * 3)),
				(-my * .01f) + eye.getYPosition(),
				(float) (eye.getZPosition() - (Math.cos(mx * .01f) * 3)));
		return eye;
	}
}