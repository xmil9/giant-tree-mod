package xmilcode.mclib.pixelgeometry2d;


public class VectorUtil2D
{
    public static Vector2D calcPerpendicular(Vector2D v)
    {
        return new Vector2D(v.y, -v.x);
    }

    
    public static Vector2D calcNormal(Vector2D v)
    {
        return new Vector2D(v.y, -v.x);
    }

    
    public static Vector2D calcNormal(Coord2D from, Coord2D to)
    {
        Vector2D edge = new Vector2D(from, to);
        return new Vector2D(edge.y, -edge.x);
    }
    
    
    public static int dotProduct(Vector2D a, Vector2D b)
    {
        return a.x * b.x + a.y * b.y;
    }
}
