package gamejam.server.objects;

import java.util.HashMap;
import java.util.Map;

public class World {

	private static World instance = null;

	public static World getInstance() {
		if (instance == null) {
			instance = new World();
			instance.init();
		}
		return instance;
	}

	private World() {
	}

	private Map<Integer, Entity> entityMap = new HashMap<Integer, Entity>();
	private int cursor = 0;

	private void init() {
		new PhysicalEntity("EmptyEntity", 0, 0, 0);
	}

	public int register(Entity entity) {
		entityMap.put(cursor, entity);
		return cursor++;
	}

	public Entity getEntityById(int identifier) {
		return entityMap.get(identifier);
	}

	public float[] getSpawnLocation() {
		return new float[3];
	}

	public Map<Integer, Entity> getEntityMap() {
		return entityMap;
	}

}
