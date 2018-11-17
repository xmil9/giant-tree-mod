package xmilcode.mclib.pixelgeometry3d.triangularprism;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.triangle.Triangle;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;
import xmilcode.mclib.util.MathUtil;


// Base class for triangular prism classes.
// Only supports prisms with a base normal axis that is parallel to one of
// the coordinate system axes.
public class BaseTriangularPrism extends BaseVoxelShape implements TriangularPrism
{
    private final Vector3D baseNormalAxis;
    private final Coord3D baseVertices[];
    private final int height;
    private final Triangle base;
    private final Coord3D topVertex;
    // Indicates that all voxels have been calculated and added to the
    // 3D shape.
    private boolean haveAllVoxels;
    
    
    protected BaseTriangularPrism(
            Vector3D baseNormalAxis,
            Coord3D baseVertex1,
            Coord3D baseVertex2,
            Coord3D baseVertex3,
            int height,
            Triangle base)
    {
        if (!Axis3D.isAxisVector(baseNormalAxis))
            throw new IllegalArgumentException("Axis vector expected.");

        this.baseNormalAxis = Vector3D.normalize(baseNormalAxis);
        this.baseVertices = new Coord3D[3];
        this.baseVertices[0] = baseVertex1;
        this.baseVertices[1] = baseVertex2;
        this.baseVertices[2] = baseVertex3;
        this.height = height;
        this.base = base;
        this.topVertex = Vector3D.add(baseVertex1, Vector3D.muliply(this.baseNormalAxis, height)); 
        this.haveAllVoxels = false;
    }


    @Override
    public Coord3D baseVertex(int idx)
    {
        return baseVertices[idx];
    }

    
    @Override
    public int height()
    {
        return height;
    }


    @Override
    public Vector3D baseNormalAxis()
    {
        return baseNormalAxis;
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
        int basePlaneCoord = Vector3D.dotProduct(new Vector3D(baseVertex(0)), baseNormalAxis);
        VoxelShape base3D = CoordUtil3D.mapTo3DPlaneOrthagonalToVector(
                base,
                basePlaneCoord,
                baseNormalAxis);
        
        Set<Coord3D> allVoxels = new HashSet<Coord3D>();
        for (int h = 0; h < height; ++h)
        {
            Vector3D heightOffset = Vector3D.muliply(baseNormalAxis, h);
            
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
        //   Vector3D baseToPosVector = new Vector3D(baseVertex(0), pos);
        //   int baseDotPosVectors = Vector3D.dotProduct(baseNormalAxis, baseToPosVector);
        // Avoiding the creation of Vector3D objects speeds up the running
        // time considerably (see BaseCylinder.java for measurements).
        // Since this method gets called very often this is worth the
        // decrease in readability.
        // Optimized:
        int baseToPosVectorX = pos.x - baseVertices[0].x;
        int baseToPosVectorY = pos.y - baseVertices[0].y;
        int baseToPosVectorZ = pos.z - baseVertices[0].z;
        int baseDotPosVectors =
                baseNormalAxis.x * baseToPosVectorX +
                baseNormalAxis.y * baseToPosVectorY +
                baseNormalAxis.z * baseToPosVectorZ;
        
        boolean isAboveBaseHeight = baseDotPosVectors >= 0;
        if (!isAboveBaseHeight)
            return false;
        
        // Unoptimized:
        //   Vector3D topToPosVector = new Vector3D(topVertex, pos);
        //   int topDotPosVectors = Vector3D.dotProduct(baseNormalAxis, topToPosVector);
        // Optimized:
        int topToPosVectorX = pos.x - topVertex.x;
        int topToPosVectorY = pos.y - topVertex.y;
        int topToPosVectorZ = pos.z - topVertex.z;
        int topDotPosVectors =
                baseNormalAxis.x * topToPosVectorX +
                baseNormalAxis.y * topToPosVectorY +
                baseNormalAxis.z * topToPosVectorZ;
        
        boolean isBelowTopHeight = topDotPosVectors <= 0;
        if (!isBelowTopHeight)
            return false;
        
        Coord2D pos2D = CoordUtil3D.mapTo2DPlaneOrthagonalToVector(
                pos,
                baseNormalAxis);
        return base.isInside(pos2D);
    }
}
