package gamejam.server.objects;

import java.util.HashMap;
import java.util.Map;

public class World {
	
	/***** SINGLETON CODE *****/
		
		private static World instance = null;
		
		public static World getInstance() {
			if (instance == null) {
				instance = new World();
				instance.init();
			}
			return instance;
		}
		
		/**
		 * Prevent initialization of the object forming a singleton
		 */
		private World() {}
	
	/***** END SINGLETON CODE *****/
		
	private Map<Integer, GameObject> objects = new HashMap<Integer, GameObject>();
	private int pointer = 0;
	
	private void init() {
		register(new PhysicalObject("NOTHING", 0f, 0f, 0f));
	}
	
	/**
	 * Registers a new object with the yard
	 * @param obj
	 * @return
	 */
	public int register(GameObject obj) {
		int objpointer = pointer;
		pointer++;
		objects.put(objpointer, obj);
		return objpointer;
	}
	
	/**
	 * Fetch an object by ID
	 * @param id
	 * @return
	 */
	 public GameObject fetchById(int id) {
		return objects.get(id);
	 }
	 
	 /**
	  * Generate spawn position
	  * @return float[]
	  */
	 public float[] getSpawnLocation() {
		 // TODO: implement
		 return new float[3];
	 } 
	 
	 /**
	  * Get all objects
	  * @return Map<Integer, GameObject>
	  */
	 public Map<Integer, GameObject> getMap() {
		return objects; 
	 }
	 
}
