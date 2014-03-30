package engine;

public class Vector3 {

	private float x;
	private float y;
	private float z;

	public Vector3(){
		x=0;
		y=0;
		z=0;
	}

	public Vector3(float newX, float newY, float newZ){
		x = newX;
		y = newY;
		z = newZ;
	}

	public static Vector3 cross(Vector3 a, Vector3 b){

		return new Vector3(a.getY()*b.getZ()-a.getZ()*b.getY(),
				a.getZ()*b.getX()-a.getX()*b.getZ(),
				a.getX()*b.getY()-a.getY()*b.getX());
	}
	
	public static Vector3 vectorSubtract(Vector3 a, Vector3 b){
		return new Vector3(b.getX()-a.getX(),b.getY()-a.getY(),b.getZ()-a.getZ());
	}
	
	public static Vector3 calcAvgNormal(Vector3 a, Vector3 b, Vector3 c, Vector3 d, Vector3 e){
		//sub
		Vector3 ca = vectorSubtract(c, a);
		Vector3 cb = vectorSubtract(c, b);
		Vector3 cd = vectorSubtract(c, d);
		Vector3 ce = vectorSubtract(c, e);
		
		Vector3 acb = cross(ca,cb);
		Vector3 bce = cross(cb,ce);
		Vector3 ecd = cross(ce, cd);
		Vector3 dca = cross(cd, ca);
		
		float avgX = (acb.getX()+bce.getX()+ecd.getX()+dca.getX())/4;
		float avgY = (acb.getY()+bce.getY()+ecd.getY()+dca.getY())/4;
		float avgZ = (acb.getZ()+bce.getZ()+ecd.getZ()+dca.getZ())/4;
		return new Vector3(avgX, avgY, avgZ);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	

}
