package xmilcode.mclib.pixelgeometry2d.circle;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;


// Circle with no center pixel. The center is located at the
// intersection of the four middle pixels of the circle. 
public class CircleWithoutCenterPixel extends BaseCircle
{
    public CircleWithoutCenterPixel()
    {
        this(new Coord2D(0, 0), 0, 0);
    }
    
    
    public CircleWithoutCenterPixel(int radius)
    {
        this(new Coord2D(0, 0),
                radius,
                CirclePixelGenerator.getDefaultSampleRate(radius));
    }
    
    
    public CircleWithoutCenterPixel(Coord2D center, int radius)
    {
        this(center,
                radius,
                CirclePixelGenerator.getDefaultSampleRate(radius));
    }
    
    
    public CircleWithoutCenterPixel(Coord2D center, int radius, int sampleRate)
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
                new CirclePixelGenerator(center, radius, sampleRate, false);
        return gen.generateOutlinePixels();
    }


    @Override
    public boolean hasCenterPixel()
    {
        return false;
    }
}
