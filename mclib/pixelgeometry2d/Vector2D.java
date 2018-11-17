package xmilcode.mclib.pixelgeometry2d;


public class Vector2D
{
    public final int x;
    public final int y;
    
    
    public Vector2D()
    {
        this(0, 0);
    }
    
    
    public Vector2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    
    public Vector2D(Coord2D pt)
    {
        this.x = pt.x;
        this.y = pt.y;
    }
    
    
    public Vector2D(Coord2D from, Coord2D to)
    {
        this(to.x - from.x, to.y - from.y);
    }
    
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vector2D)
        {
            Vector2D cmp = (Vector2D)obj;
            return (x == cmp.x &&
                    y == cmp.y);
        }
        
        return false;
    }
    
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash = hash * 19 + x;
        hash = hash * 23 + y;
        return hash;
    }
    
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}
