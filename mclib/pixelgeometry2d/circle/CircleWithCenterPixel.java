package xmilcode.mclib.pixelgeometry2d.circle;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;


// Circle with a center pixel.
public class CircleWithCenterPixel extends BaseCircle
{
    public CircleWithCenterPixel()
    {
        this(new Coord2D(0, 0), 0, 0);
    }
    
    
    public CircleWithCenterPixel(int radius)
    {
        this(new Coord2D(0, 0),
                radius,
                CirclePixelGenerator.getDefaultSampleRate(radius));
    }
    
    
    public CircleWithCenterPixel(Coord2D center, int radius)
    {
        this(center,
                radius,
                CirclePixelGenerator.getDefaultSampleRate(radius));
    }
    
    
    public CircleWithCenterPixel(Coord2D center, int radius, int sampleRate)
    {
        super(center,
                radius,
                generatePixels(center, radius, sampleRate));
    }
    
    
    private static Set<Coord2D> generatePixels(
            Coord2D center,
            int radius,
            int sampleRate)
    {
        CirclePixelGenerator gen =
                new CirclePixelGenerator(center, radius, sampleRate, true);
        return gen.generateOutlinePixels();
    }


    @Override
    public boolean hasCenterPixel()
    {
        return true;
    }
}
