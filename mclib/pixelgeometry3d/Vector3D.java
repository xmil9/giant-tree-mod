package xmilcode.mclib.pixelgeometry3d;



public class Vector3D
{
    public final int x;
    public final int y;
    public final int z;
    
    
    public Vector3D()
    {
        this(0, 0, 0);
    }
    
    
    public Vector3D(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    public Vector3D(Vector3D src)
    {
        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
    }

    
    public Vector3D(Coord3D from, Coord3D to)
    {
        this(to.x - from.x, to.y - from.y, to.z - from.z);
    }

    
    public Vector3D(Coord3D to)
    {
        this(to.x, to.y, to.z);
    }
    
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vector3D)
        {
            Vector3D cmp = (Vector3D)obj;
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
        hash = hash * 19 + x;
        hash = hash * 23 + y;
        hash = hash * 29 + z;
        return hash;
    }
    
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y + "," + z + ")";
    }
    
    
    public double length()
    {
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    
    public double lengthSquared()
    {
        return dotProduct(this, this);
    }

    
    // Class/static methods.

    public static Vector3D add(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    
    public static Coord3D add(Coord3D pt, Vector3D v)
    {
        return new Coord3D(pt.x + v.x, pt.y + v.y, pt.z + v.z);
    }
    
    
    public static Vector3D subtract(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    
    
    public static Coord3D subtract(Coord3D pt, Vector3D v)
    {
        return new Coord3D(pt.x - v.x, pt.y - v.y, pt.z - v.z);
    }
    
    
    public static Vector3D subtract(Coord3D a, Coord3D b)
    {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    
    public static Vector3D muliply(Vector3D v, double scalar)
    {
        return new Vector3D((int)(v.x * scalar), (int)(v.y * scalar), (int)(v.z * scalar));
    }
    
    
    public static Vector3D divide(Vector3D v, double scalar)
    {
        return new Vector3D((int)(v.x / scalar), (int)(v.y / scalar), (int)(v.z / scalar));
    }
    
    
    public static Vector3D normalize(Vector3D v)
    {
        double len = v.length();
        if (len == 0)
            return new Vector3D();
        
        return divide(v, len);
    }

    
    public static Vector3D reverse(Vector3D v)
    {
        return new Vector3D(-v.x, -v.y, -v.z);
    }
    
    
    public static int dotProduct(Vector3D a, Vector3D b)
    {
        return a.x*b.x + a.y*b.y + a.z*b.z;
    }
}
