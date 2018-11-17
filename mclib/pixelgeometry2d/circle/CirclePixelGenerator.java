package xmilcode.mclib.pixelgeometry2d.circle;

import java.util.HashSet;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.CoordUtil2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShapeFiller;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;


// Generates the pixels that lie on the outline of a given circle. 
public class CirclePixelGenerator
{
    private Coord2D center;
    private int radius;
    private int sampleRate;
    private boolean withCenterPixel;
    
    
    public CirclePixelGenerator(
            Coord2D center,
            int radius,
            int sampleRate,
            boolean withCenterPixel)
    {
        this.center = center;
        this.radius = radius;
        this.sampleRate = sampleRate;
        this.withCenterPixel = withCenterPixel;
    }


    public static int getDefaultSampleRate(int radius)
    {
        return 24 * radius;
    }
    
    
    public Set<Coord2D> generateOutlinePixels()
    {
        // Ordered list of the samples.
        HashSet<Coord2D> samples = new HashSet<Coord2D>();

        if (radius > 0 && sampleRate > 0)
        {
            double sampleDeltaAngleInRad = 2*Math.PI / sampleRate;

            for (double sampleAngleInRad = 0.0;
                    sampleAngleInRad < 2*Math.PI;
                    sampleAngleInRad += sampleDeltaAngleInRad)
            {
                samples.add(sampleCirlceAtAngle(sampleAngleInRad));
            }
        }
        else if (radius == 0)
        {
            samples.add(new Coord2D(center));
        }
        
        return samples;
    }
    
    
    private Coord2D sampleCirlceAtAngle(double angleInRad)
    {
        double dx = radius * Math.cos(angleInRad);
        double dy = radius * Math.sin(angleInRad);
        
        // Shift coordinates to set the cirle's center to the left,
        // bottom corner of the center pixel instead of its middle.
        if (!withCenterPixel)
        {
            dx -= 0.5;
            dy -= 0.5;
        }
        
        Coord2D posOnCircle = CoordUtil2D.pixelateCoord2D(dx, dy);
        return Coord2D.add(center, posOnCircle);
    }
    
    
    public Set<Coord2D> generateFillPixels()
    {
        BasePixelShape circleOutline = new BasePixelShape(generateOutlinePixels());
        PixelShapeFiller filler = new PixelShapeFiller(circleOutline, center, false);
        return filler.fill().pixels();
    }
}
