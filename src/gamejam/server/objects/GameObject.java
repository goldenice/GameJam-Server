package gamejam.server.objects;

public class GameObject {
	
	private World objyard;
	private int objectId;
	private String type;
	
	public GameObject(String type) {
		this.type = type;
		objyard = World.getInstance();
		objectId = getObjectYard().register(this);
	}
	
	protected World getObjectYard() {
		return objyard;
	}
	
	public int getObjectId() {
		return objectId;
	}
	
	public String getType() {
		return type;
	}
	
}
