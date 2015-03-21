package gamejam.server.objects;

public class Entity {
	
	private World objyard;
	private int identifier;
	private String type;
	
	public Entity(String type) {
		this.type = type;
		objyard = World.getInstance();
		identifier = getObjectYard().register(this);
	}
	
	protected World getObjectYard() {
		return objyard;
	}
	
	public int getObjectId() {
		return identifier;
	}
	
	public String getType() {
		return type;
	}
	
}
