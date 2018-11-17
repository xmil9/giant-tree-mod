package xmilcode.mclib.plants;

import java.util.Random;


// Growth speed of plants.
public enum GrowthSpeed
{
    VERY_SLOW(1),
    SLOW(3),
    NORMAL(5),
    FAST(7),
    VERY_FAST(10);
    
    private final int speed;
    
    private GrowthSpeed(int speed)
    {
	this.speed = speed;
    }
    
    public boolean sample(Random rand)
    {
	return (rand.nextInt(VERY_FAST.speed) < speed);
    }
}
