package xmilcode.mclib.datastructures;



public class ClosedRange<T extends Comparable<T>>
{
    public final T min;
    public final T max;
    
    
    public ClosedRange(T min, T max)
    {
        this.min = (min.compareTo(max) <= 0) ? min : max;
        this.max = (min.compareTo(max) <= 0) ? max : min;
    }
    
    
    public boolean isInRange(T value)
    {
        return (min.compareTo(value) <= 0 &&
                max.compareTo(value) >= 0); 
    }
}
