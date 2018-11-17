package xmilcode.mclib.pixelgeometry3d.cone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.LinearDirection;
import xmilcode.mclib.pixelgeometry2d.circle.Circle;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithCenterPixel;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithoutCenterPixel;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class ConeVoxelGenerator
{
    private class BaseAxes
    {
        public final Vector3D a;
        public final Vector3D b;
        
        public BaseAxes(Vector3D orthogonalVector)
        {
            if (!Axis3D.isAxisVector(orthogonalVector))
                throw new IllegalArgumentException();
            
            if (orthogonalVector.x != 0)
            {
                a = new Vector3D(0, 1, 0);
                b = new Vector3D(0, 0, 1);
            }
            else if (orthogonalVector.y != 0)
            {
                a = new Vector3D(1, 0, 0);
                b = new Vector3D(0, 0, 1);
            }
            else
            {
                a = new Vector3D(1, 0, 0);
                b = new Vector3D(0, 1, 0);
            }
        }
    }
    
    
    private Cone target;
    
    
    public ConeVoxelGenerator(Cone target)
    {
        this.target = target;
    }
    
    
    public Set<Coord3D> generateVoxels()
    {
        Set<Coord3D> voxels = new HashSet<Coord3D>();
        
        // Hit test all voxels in the cube that the cone is inside of.

        BaseAxes baseAxes = new BaseAxes(target.centerAxis());
        
        for (int h = 0; h < target.height(); ++h)
        {
            Vector3D heightOffset = Vector3D.muliply(target.centerAxis(), h);
            
            for (int a = -target.radius() - 1; a < target.radius() + 1; ++a)
            {
                Vector3D baseOffsetA = Vector3D.muliply(baseAxes.a, a);
                
                for (int b = -target.radius() - 1; b < target.radius() + 1; ++b)
                {
                    Vector3D baseOffsetB = Vector3D.muliply(baseAxes.b, b);
                    
                    Coord3D tested =
                            Vector3D.add(
                                    Vector3D.add(
                                            Vector3D.add(target.baseCenter(), heightOffset),
                                            baseOffsetA),
                                    baseOffsetB);
                    
                    if (target.isHit(tested))
                        voxels.add(tested);
                }
            }
        }
        
        return voxels;
    }
}
