package xmilcode.mclib.util;

import net.minecraft.world.World;

public class LightUtil
{
    public static final int MIN_LIGHT = 0; 
    public static final int MAX_LIGHT = 14;
    
    
    public static boolean isDaylight(int light)
    {
	return (light >= 9);
    }
}
