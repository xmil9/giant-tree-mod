package xmilcode.mclib.pixelgeometry2d.circle;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShapeFiller;



public class FilledCircleDecorator implements Circle
{
    private static interface HitStrategy
    {
        public boolean isHit(Coord2D pos);
    }
    
    
    // Checks for hits by calculating the geometrical relationship of
    // the passed pixel to the circle.
    // Should be faster for circles with many pixels.
    private static class GeometricalHitStategy implements HitStrategy
    {
        private Circle target;
        
        public GeometricalHitStategy(Circle target)
        {
            this.target = target;
        }

        @Override
        public boolean isHit(Coord2D pos)
        {
            return target.isInside(pos);
        }
    }
    
    
    // Checks for hits by testing if the passed pixel is a member
    // of the shape's pixel set.
    // Should be faster for circles with few pixels.
    private static class MembershipHitStategy implements HitStrategy
    {
        private PixelShape target;
        
        public MembershipHitStategy(PixelShape target)
        {
            this.target = target;
        }

        @Override
        public boolean isHit(Coord2D pos)
        {
            return target.isHit(pos);
        }
    }
    
    
    private Circle decorated;
    private PixelShape filled;
    private HitStrategy hitStrategy;
    
    
    public FilledCircleDecorator(Circle decorated)
    {
        this.decorated = decorated;
        this.filled = fill(decorated);
        this.hitStrategy = chooseHitStrategy(filled, decorated);
    }
    
    private static HitStrategy chooseHitStrategy(PixelShape filled, Circle decorated)
    {
        // Radius threshold Value is just a guess! Not backed by measurements.
        return decorated.radius() < 20 ?
                new MembershipHitStategy(filled) : new GeometricalHitStategy(decorated);
    }
    
    @Override
    public int countPixels()
    {
        return filled.countPixels();
    }

    @Override
    public Set<Coord2D> pixels()
    {
        return filled.pixels();
    }

    @Override
    public boolean isHit(Coord2D pos)
    {
        return hitStrategy.isHit(pos);
    }

    @Override
    public Coord2D center()
    {
        return decorated.center();
    }

    @Override
    public int radius()
    {
        return decorated.radius();
    }

    @Override
    public boolean isInside(Coord2D pos)
    {
        return decorated.isInside(pos);
    }

    @Override
    public boolean hasCenterPixel()
    {
        return decorated.hasCenterPixel();
    }

    private static PixelShape fill(Circle decorated)
    {
        PixelShapeFiller filler = new PixelShapeFiller(decorated, decorated.center());
        return filler.fill();
    }
}
