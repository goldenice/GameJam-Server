package gamejam.server.objects;

/**
 * Created by Gerryflap on 2015-03-21.
 */
public class Ship extends PhysicalEntity {
    public static final String CLASS_NAME = "Ship";
    public String username;

    public Ship(float x, float y, float z, float yaw, float pitch, float roll, String username) {
        super(CLASS_NAME, x, y, z, yaw, pitch, roll);
        this.username = username;
    }
}
