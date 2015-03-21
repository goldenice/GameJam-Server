package gamejam.server.objects;

/**
 * Created by Gerryflap on 2015-03-21.
 */
public class Meteor extends PhysicalEntity {
    public static final String CLASS_NAME = "Meteor";

    public Meteor(float x, float y, float z, float yaw, float pitch, float roll) {
        super(CLASS_NAME, x, y, z, yaw, pitch, roll);
    }
}
