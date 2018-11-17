package xmilcode.mclib.pixelgeometry2d.shape;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;


//Collection of pixels.
public interface PixelShape
{
    public int countPixels();
    public Set<Coord2D> pixels();
    // Is the given position one of the shape's pixels?
    public boolean isHit(Coord2D pos);
}
