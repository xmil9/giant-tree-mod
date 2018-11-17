package xmilcode.mclib.pixelgeometry3d.cylinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.LinearDirection;
import xmilcode.mclib.pixelgeometry2d.circle.BaseCircle;
import xmilcode.mclib.pixelgeometry2d.circle.Circle;
import xmilcode.mclib.pixelgeometry2d.circle.CirclePixelGenerator;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithCenterPixel;
import xmilcode.mclib.pixelgeometry2d.circle.FilledCircleDecorator;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShapeFiller;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;
import xmilcode.mclib.util.MathUtil;


// Base class for cylinder classes.
// Only supports cylinders with a center axis that is parallel to one of
// the coordinate system axes.
public abstract class BaseCylinder extends BaseVoxelShape implements Cylinder
{
    private final Vector3D normedCenterAxis;
    private final Coord3D baseCenter;
    private final Coord3D topCenter;
    private final int height;
    private final Circle base;
    // Indicates that all voxels have been calculated and added to the
    // 3D shape.
    private boolean haveAllVoxels;
    
    
    protected BaseCylinder(
            Vector3D centerAxis,
            Coord3D baseCenter,
            int height,
            Circle base)
    {
        // Do not immediately calculate and pass all voxels of the cylinder to
        // the 3D shape super class because there could be many voxels and we
        // do not actually need all voxels internally. We can implement the
        // other 3D shape operations using geometry. Only if a caller specifically
        // asks for all voxels do we calculate them.  
        super();
        
        if (!Axis3D.isAxisVector(centerAxis))
            throw new IllegalArgumentException("Axis vector expected.");

        this.normedCenterAxis = Vector3D.normalize(centerAxis);
        this.baseCenter = baseCenter;
        this.topCenter = Vector3D.add(baseCenter, Vector3D.muliply(normedCenterAxis, height));
        this.height = height;
        this.base = base;
        this.haveAllVoxels = false;
    }


    @Override
    public Coord3D baseCenter()
    {
        return baseCenter;
    }


    @Override
    public int radius()
    {
        return base.radius();
    }


    @Override
    public int height()
    {
        return height;
    }


    @Override
    public Vector3D centerAxis()
    {
        return normedCenterAxis;
    }

    
    @Override
    public int countVoxels()
    {
        return base.countPixels() * height;
    }

    
    @Override
    public Set<Coord3D> voxels()
    {
        // Lazy initialization of the cylinder's voxels because there could be many!
        if (!haveAllVoxels)
            addAllVoxelsToShape();
        return super.voxels();
    }

    
    private void addAllVoxelsToShape()
    {
        int basePlaneCoord = Vector3D.dotProduct(new Vector3D(baseCenter), normedCenterAxis);
        VoxelShape base3D = CoordUtil3D.mapTo3DPlaneOrthagonalToVector(
                base,
                basePlaneCoord,
                normedCenterAxis);
        
        Set<Coord3D> allVoxels = new HashSet<Coord3D>();
        for (int h = 0; h < height; ++h)
        {
            Vector3D heightOffset = Vector3D.muliply(normedCenterAxis, h);
            
            for (Coord3D baseVoxel : base3D.voxels())
                allVoxels.add(Vector3D.add(baseVoxel, heightOffset));
        }
        
        setVoxels(allVoxels);
        haveAllVoxels = true;
    }
    

    @Override
    public boolean isHit(Coord3D pos)
    {
        // Unoptimized:
        //   Vector3D baseToPosVector = new Vector3D(baseCenter, pos);
        //   int baseDotPosVectors = Vector3D.dotProduct(normedCenterAxis, baseToPosVector);
        // Avoiding the creation of Vector3D objects speeds up the running
        // time of this method by a factor of 10. Since it gets called very
        // often this is worth the decrease in readability.
        // Avg time with Vector3D: 0.001522ms
        // Avg time without:       0.000221ms
        // Optimized:
        int baseToPosVectorX = pos.x - baseCenter.x;
        int baseToPosVectorY = pos.y - baseCenter.y;
        int baseToPosVectorZ = pos.z - baseCenter.z;
        int baseDotPosVectors =
                normedCenterAxis.x * baseToPosVectorX +
                normedCenterAxis.y * baseToPosVectorY +
                normedCenterAxis.z * baseToPosVectorZ;
        
        boolean isAboveBaseHeight = baseDotPosVectors >= 0;
        if (!isAboveBaseHeight)
            return false;
        
        // Unoptimized:
        //   Vector3D topToPosVector = new Vector3D(topCenter, pos);
        //   int topDotPosVectors = Vector3D.dotProduct(normedCenterAxis, topToPosVector);
        // Optimized:
        int topToPosVectorX = pos.x - topCenter.x;
        int topToPosVectorY = pos.y - topCenter.y;
        int topToPosVectorZ = pos.z - topCenter.z;
        int topDotPosVectors =
                normedCenterAxis.x * topToPosVectorX +
                normedCenterAxis.y * topToPosVectorY +
                normedCenterAxis.z * topToPosVectorZ;
        
        boolean isBelowTopHeight = topDotPosVectors <= 0;
        if (!isBelowTopHeight)
            return false;

        Coord2D pos2D = CoordUtil3D.mapTo2DPlaneOrthagonalToVector(
                pos,
                normedCenterAxis);
        return base.isHit(pos2D);
    }
}
