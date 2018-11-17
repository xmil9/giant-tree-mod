package xmilcode.mclib.pixelgeometry3d.staircase;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;


// 3D shape that wraps a path of voxels around a given 2D shape that is
// extrapolated into 3D space.
public class Staircase extends BaseVoxelShape
{
    private final int baseYPos3D; 
    private final int height;


    public Staircase()
    {
        this(new BasePixelShape(), new Coord2D(), 0, 0);
    }
    
    
    public Staircase(
            BasePixelShape base,
            Coord2D innerBasePos,
            int baseYPos3D,
            int height)
    {
        super(generateStaircaseVoxels(
                base,
                innerBasePos,
                baseYPos3D,
                height));
        this.baseYPos3D = baseYPos3D;
        this.height = height;
    }
    
    
    private static Set<Coord3D> generateStaircaseVoxels(
            BasePixelShape base,
            Coord2D innerBasePos,
            int baseYPos3D,
            int height)
    {
        StaircaseVoxelGenerator gen = new StaircaseVoxelGenerator(
                base,
                innerBasePos,
                baseYPos3D,
                height);
        return gen.generateVoxels();
    }
    
    
    public int baseYPosition()
    {
        return baseYPos3D;
    }
    
    
    public int height()
    {
        return height;
    }
}
