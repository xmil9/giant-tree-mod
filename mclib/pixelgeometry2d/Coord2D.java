package xmilcode.mclib.pixelgeometry2d;



public class Coord2D
{
    public final int x;
    public final int y;
    
    public Coord2D()
    {
        this(0, 0);
    }
    
    public Coord2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Coord2D(Coord2D src)
    {
        this.x = src.x;
        this.y = src.y;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Coord2D)
        {
            Coord2D cmp = (Coord2D)obj;
            return (x == cmp.x &&
                    y == cmp.y);
        }
        
        return false;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash = hash * 13 + x;
        hash = hash * 17 + y;
        return hash;
    }
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
    
    public static Coord2D add(Coord2D a, Coord2D b)
    {
        return new Coord2D(a.x + b.x, a.y + b.y);
    }
    
    // The result of subtracting two points is really a vector but
    // returning a point can be useful, too, and it avoids a circular
    // dependency on the vector class.
    // To get a vector as result use the ctor of the vector class.
    public static Coord2D subtract(Coord2D a, Coord2D b)
    {
        return new Coord2D(a.x - b.x, a.y - b.y);
    }
}
