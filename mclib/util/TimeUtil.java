package xmilcode.mclib.util;


public class TimeUtil
{
    public static final int TICKS_PER_SEC = 20;
    
    
    public static float SecondsFromTicks(int ticks)
    {
	return (float)ticks / (float)TICKS_PER_SEC;
    }
    
    
    public static int TicksFromSeconds(float secs)
    {
	return (int)(secs * (float)TICKS_PER_SEC);
    }
}
