package xmilcode.mclib.util;


public class MathUtil
{
    public static int clamp(int value, int min, int max)
    {
        return Math.max(min, Math.min(max, value));    
    }
    
    
    public static float clamp(float value, float min, float max)
    {
        return Math.max(min, Math.min(max, value));    
    }
    
    
    public static double absCeil(double value)
    {
        double sign = (value >= 0) ? 1 : -1;
        return sign * Math.ceil(Math.abs(value));
    }
    
    
    public static double absFloor(double value)
    {
        double sign = (value >= 0) ? 1 : -1;
        return sign * Math.floor(Math.abs(value));
    }
    
    
    // Contracts a given value to be closer to zero by a given
    // amount.
    public static double contract(double value, double by)
    {
        double sign = (value >= 0) ? 1 : -1;
        return sign * Math.max(0, Math.abs(value) - by);
    }
    
    
    // Expands a given value to be further from zero by a given
    // amount.
    public static double expand(double value, double by)
    {
        double sign = (value >= 0) ? 1 : -1;
        return sign * Math.max(0, Math.abs(value) + by);
    }
    
    
    public static boolean isInRightOpenRange(int val, int min, int max)
    {
        return val >= min && val < max;
    }
    
    
    public static int min(int a, int b, int c)
    {
        return Math.min(a, Math.min(b, c));
    }
    
    
    public static int max(int a, int b, int c)
    {
        return Math.max(a, Math.max(b, c));
    }
}
