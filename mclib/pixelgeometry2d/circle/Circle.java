package xmilcode.mclib.pixelgeometry2d.circle;

import java.util.List;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;


// A pixelated circle.
public interface Circle extends PixelShape
{
    public Coord2D center();
    public int radius();
    // Is the given position touching the outline or inside of the circle?
    public boolean isInside(Coord2D pos);
    public boolean hasCenterPixel();
}
