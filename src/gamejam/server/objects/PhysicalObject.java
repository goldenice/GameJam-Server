package gamejam.server.objects;

public class PhysicalObject extends GameObject {

	private String type;
	private float x;
	private float y;
	private float z;
	
	public PhysicalObject(String type, float x, float y, float z, float rotation) {
		this(type, x, y, z);
		// TODO: implement
	}
	
	public PhysicalObject(String type, float x, float y, float z) {
		super(type);
		updatePosition(x, y, z);
	}
	
	public void updatePosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() { return x;	}
	public float getY() { return y;	}
	public float getZ() { return z;	}
	
	public void updateRotation() {
		// TODO: implement
	}
	
	public void updateSpeed() {
		// TODO: implement
	}
	
}
