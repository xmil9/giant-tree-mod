package xmilcode.mclib.pixelgeometry2d.circle;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.CoordUtil2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;


// Base class for circle classes.
// Only stores the pixels of the circle's outline.
public abstract class BaseCircle extends BasePixelShape implements Circle
{
    private Coord2D center;
    private int radius;
    private int radiusSquared;
    
    
    protected BaseCircle(
            Coord2D center,
            int radius,
            Set<Coord2D> pixels)
    {
        super(pixels);
        this.center = center;
        this.radius = radius;
        this.radiusSquared = radius * radius;
    }
    
    
    @Override // Circle
    public Coord2D center()
    {
        return center;
    }
    
    
    @Override // Circle
    public int radius()
    {
        return radius;
    }
    
    
    @Override // Circle
    public boolean isInside(Coord2D pos)
    {
        // Comparing the distance from the circle's center to the tested
        // position to 'less-or-equal' the radius does not always work for
        // pixelated circles. Instead we compare the distance and radius
        // with the stricter 'less-than' and also check if the circle's outline
        // was hit.
        return (isHit(pos) ||
                CoordUtil2D.distanceSquared2D(center, pos) < radiusSquared);
    }
}
