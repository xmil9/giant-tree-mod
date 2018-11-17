package xmilcode.mclib.util;

import java.util.Random;

import xmilcode.mclib.datastructures.ClosedRange;


public class RandomUtil
{
    // Random integer in [a, b];
    public static int randInt(int a, int b, Random rand)
    {
        return a + rand.nextInt(b - a + 1);
    }

    public static int randInt(ClosedRange<Integer> range, Random rand)
    {
        return randInt(range.min, range.max, rand);
    }
    
    public static boolean sampleRandEvent(float probability, Random rand)
    {
        return (rand.nextFloat() < MathUtil.clamp(probability, 0.0F, 1.0F));
    }
}
