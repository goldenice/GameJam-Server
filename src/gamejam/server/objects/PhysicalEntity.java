package gamejam.server.objects;

public class PhysicalEntity extends Entity {

	private String type; // TODO: Do we want this to be a String?

	private float x;
	private float y;
	private float z;

	private float yaw;
	private float pitch;
	private float roll;

	private float velocity;
	
	public PhysicalEntity(String type, float x, float y, float z, float yaw, float pitch, float roll) {
		super(type);
		updatePosition(x, y, z);
		updateRotation(yaw, pitch, roll);
	}
	
	public PhysicalEntity(String type, float x, float y, float z) {
		this(type, x, y, z, 0, 0, 0);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public float getVelocity() {
		return velocity;
	}

	public void updatePosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void updateRotation(float yaw, float pitch, float roll) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}
	
	public void updateVelocity(float velocity) {
		this.velocity = velocity;
	}
}
