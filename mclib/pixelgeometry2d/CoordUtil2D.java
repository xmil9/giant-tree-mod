package xmilcode.mclib.pixelgeometry2d;

import xmilcode.mclib.util.MathUtil;


public class CoordUtil2D
{
    public static int distanceSquared2D(Coord2D a, Coord2D b)
    {
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        return dx*dx + dy*dy;
    }

    
    public static Coord2D pixelateCoord2D(double x, double y)
    {
        return new Coord2D((int)MathUtil.absFloor(x), (int)MathUtil.absFloor(y));
    }
    
    
    public static boolean isDirectNeighbor(Coord2D a, Coord2D b)
    {
        return ((a.x == b.x && Math.abs(a.y - b.y) == 1) ||
                (a.y == b.y && Math.abs(a.x - b.x) == 1));
    }
    
    
    public static boolean isDiagonalNeighbor(Coord2D a, Coord2D b)
    {
        return (Math.abs(a.x - b.x) == 1 && Math.abs(a.y - b.y) == 1);
    }
    
    
    public static boolean isNeighbor(Coord2D a, Coord2D b)
    {
        return (isDirectNeighbor(a, b) || isDiagonalNeighbor(a, b));
    }
    
    
    public static double calcAngle(Coord2D a, Coord2D b)
    {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.atan2(dy, dx);
    }
}
