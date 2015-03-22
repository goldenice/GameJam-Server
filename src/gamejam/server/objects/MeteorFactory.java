package gamejam.server.objects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gerryflap on 2015-03-22.
 */
public class MeteorFactory {
    private ArrayList<Meteor> meteors;
    public static final int METEOR_NUM = 5000;
    public static final float CREATION_SCALE = 3000;


    public MeteorFactory(){
        meteors = new ArrayList<Meteor>();


    }

    public void generateMeteors(){
        float[] pos = new float[3];
        Random random = new Random();
        for(int i = 0; i < METEOR_NUM; i++){
            pos[0] = random.nextFloat()*2*CREATION_SCALE-CREATION_SCALE;
            pos[1] = random.nextFloat()*2*CREATION_SCALE-CREATION_SCALE;
            pos[2] = random.nextFloat()*2*CREATION_SCALE-CREATION_SCALE;
            float size = random.nextFloat()*30 + 6f;
            Meteor meteor = new Meteor(pos[0], pos[1], pos[2], random.nextFloat(), random.nextFloat(), random.nextFloat(), size);
            meteors.add(meteor);
        }
    }


    public ArrayList<Meteor> getMeteors(){
        return this.meteors;
    }




}
