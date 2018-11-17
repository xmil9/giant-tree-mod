package xmilcode.mclib.pixelgeometry2d.triangle;

import xmilcode.mclib.pixelgeometry2d.Coord2D;


public class TriangleUtil
{
    public static Coord2D calcCentroid(Coord2D v1, Coord2D v2, Coord2D v3)
    {
        int cx = (int)((double)(v1.x + v2.x + v3.x) / 3.0);
        int cy = (int)((double)(v1.y + v2.y + v3.y) / 3.0);
        
        return new Coord2D(cx, cy);
    }
}
