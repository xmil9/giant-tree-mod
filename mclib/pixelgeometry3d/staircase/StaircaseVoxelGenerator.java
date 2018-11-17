package xmilcode.mclib.pixelgeometry3d.staircase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.AngularComparator2D;
import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.CoordUtil2D;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShapeOutliner;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;


public class StaircaseVoxelGenerator
{
    private PixelShape base;
    private Coord2D innerBasePos;
    private int baseYPos3D;
    private int height;

    
    public StaircaseVoxelGenerator(
            PixelShape base,
            Coord2D innerBasePos,
            int baseYPos3D,
            int height)
    {
        this.base = base;
        this.innerBasePos = innerBasePos;
        this.baseYPos3D = baseYPos3D;
        this.height = height;
    }
    
    
    public Set<Coord3D> generateVoxels()
    {
        PixelShapeOutliner outliner = new PixelShapeOutliner(base, innerBasePos);
        return extrude(outliner.outlineSolidOnOutside());
    }
    
    
    private Set<Coord3D> extrude(PixelShape flat)
    {
        Set<Coord3D> extruded = new HashSet<Coord3D>();
        int basePixelIdx = 0;
        
        // To assign correct consecutive height values to each pixel
        // we have to sort the pixel according to some criteria.
        // The pixels angle seems to work well but possibly not for
        // all shapes. We might have to refactor this to pass the
        // sorting criteria as paramter.
        List<Coord2D> flatSorted = new ArrayList<Coord2D>(flat.pixels());
        Collections.sort(flatSorted, new AngularComparator2D(innerBasePos));
        
        for (int h = 0; h < height; ++h)
        {
            Coord3D voxel = CoordUtil3D.mapTo3DXZPlane(
                    flatSorted.get(basePixelIdx),
                    baseYPos3D + h);
            extruded.add(voxel);
            
            if (++basePixelIdx >= flat.pixels().size())
                basePixelIdx = 0;
        }
        
        return extruded;
    }
}
