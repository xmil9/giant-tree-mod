package xmilcode.mclib.pixelgeometry2d.triangle;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;


public interface Triangle extends PixelShape
{
    public Coord2D vertex(int idx);
    // Aka center of mass. Is always inside the triangle.
    public Coord2D centroid();
    // Is the given position touching the outline or inside of the triangle?
    public boolean isInside(Coord2D pos);
}
