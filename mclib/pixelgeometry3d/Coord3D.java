package xmilcode.mclib.pixelgeometry3d;

import xmilcode.mclib.pixelgeometry2d.Coord2D;


public class Coord3D
{
    public final int x;
    public final int y;
    public final int z;
    
    public Coord3D()
    {
        this(0, 0, 0);
    }
    
    public Coord3D(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coord3D(Coord3D src)
    {
        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Coord3D)
        {
            Coord3D cmp = (Coord3D)obj;
            return (x == cmp.x &&
                    y == cmp.y &&
                    z == cmp.z);
        }
        
        return false;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash = hash * 13 + x;
        hash = hash * 17 + y;
        hash = hash * 23 + z;
        return hash;
    }
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y + "," + z + ")";
    }
    
    public static Coord3D add(Coord3D a, Coord3D b)
    {
        return new Coord3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    public static Coord3D subtract(Coord3D a, Coord3D b)
    {
        return new Coord3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }
}
