package xmilcode.mclib.pixelgeometry2d.shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Collection of pixels without any specific shape.
public class BasePixelShape implements PixelShape
{
    private Set<Coord2D> pixels;
    
    
    public BasePixelShape()
    {
        this(new HashSet<Coord2D>());
    }
    
    
    public BasePixelShape(Set<Coord2D> pixels)
    {
        this.pixels = pixels;
    }
    
    
    public BasePixelShape(BasePixelShape src)
    {
        this(new HashSet<Coord2D>(src.pixels()));
    }
    
    
    @Override
    public int countPixels()
    {
        return pixels.size();
    }
    
    
    @Override
    public Set<Coord2D> pixels()
    {
        return pixels;
    }
    
    
    @Override
    public boolean isHit(Coord2D pos)
    {
        return pixels.contains(pos);
    }
}
