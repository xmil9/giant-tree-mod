package xmilcode.mclib.pixelgeometry2d;


public enum LinearDirection
{
    INCREASING(1),
    DECREASING(-1);
    
    private int sign;
    
    private LinearDirection(int sign)
    {
        this.sign = sign;
    }
    
    public int sign()
    {
        return sign;
    }
    
    public LinearDirection opposite()
    {
        return (sign == 1) ? DECREASING : INCREASING;
    }
}
