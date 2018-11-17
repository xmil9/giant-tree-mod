package xmilcode.mclib.pixelgeometry3d.cone;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.LinearDirection;
import xmilcode.mclib.pixelgeometry2d.VectorUtil2D;
import xmilcode.mclib.pixelgeometry2d.circle.Circle;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;
import xmilcode.mclib.util.MathUtil;


// Base class for cone classes.
// Only supports cones with a center axis that is parallel to one of
// the coordinate system axes.
public abstract class BaseCone extends BaseVoxelShape implements Cone
{
    private final Vector3D centerAxis;
    private final Coord3D baseCenter;
    private final int height;
    private final Circle base;
    // Cached point for the cone's tip.
    private final Coord3D tip;
    // Cached vector from tip to base of cone. 
    private final Vector3D normedTipToBaseVector;
    // Cached squared cosine value of the angle 'theta' between the
    // 'tip to base' vector and the outer cone edge at the tip.
    private final double cosThetaSquared;
    // Indicates that all voxels have been calculated and added to the
    // 3D shape.
    private boolean haveAllVoxels;

    
    protected BaseCone(
            Vector3D centerAxis,
            Coord3D baseCenter,
            int height,
            Circle base)
    {
        // Do not immediately calculate and pass all voxels of the cone to
        // the 3D shape super class because there could be many voxels and we
        // do not actually need all voxels internally. We can implement the
        // other 3D shape operations using geometry. Only if a caller specifically
        // asks for all voxels do we calculate them.  
        super();
        
        if (!Axis3D.isAxisVector(centerAxis))
            throw new IllegalArgumentException("Axis vector expected.");
        
        this.centerAxis = Vector3D.normalize(centerAxis);
        this.baseCenter = baseCenter;
        this.height = height;
        this.base = base;
        this.tip = calcTip(centerAxis, baseCenter, height);
        this.normedTipToBaseVector = Vector3D.normalize(new Vector3D(tip, baseCenter));
        this.cosThetaSquared = calcCosineThetaSquared(height, base.radius());
        this.haveAllVoxels = false;
    }

    
    private static Coord3D calcTip(
            Vector3D centerAxis,
            Coord3D baseCenter,
            int height)
    {
        Vector3D tipOffset = Vector3D.muliply(centerAxis, height);
        return Vector3D.add(baseCenter, tipOffset);
    }
    
    
    private static double calcCosineThetaSquared(int height, int radius)
    {
        double theta = calcTheta(height, radius);
        double cosTheta = Math.cos(theta);
        return cosTheta * cosTheta;
    }

    
    // Calculates the angle 'theta' at the tip of the cone between the
    // 'tip to base' vector and the outer edge of the cone.
    static double calcTheta(double height, double radius)
    {
        if (height == 0)
            throw new IllegalArgumentException();
        return Math.atan(radius / height);
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
        return centerAxis;
    }

    
    @Override
    public int countVoxels()
    {
        // Lazy initialization of the cone's voxels because there could be many!
        if (!haveAllVoxels)
            addAllVoxelsToShape();
        return super.countVoxels();
    }

    @Override
    public Set<Coord3D> voxels()
    {
        // Lazy initialization of the cone's voxels because there could be many!
        if (!haveAllVoxels)
            addAllVoxelsToShape();
        return super.voxels();
    }

    
    private void addAllVoxelsToShape()
    {
        ConeVoxelGenerator gen = new ConeVoxelGenerator(this);
        setVoxels(gen.generateVoxels());

        haveAllVoxels = true;
    }

    
    @Override
    public boolean isHit(Coord3D pos)
    {
        // Check if a point is inside the cone.
        // Source: 'Geometric Tools for Computer Graphics' pg.583f.
        
        // Unoptimized:
        //   Vector3D tipToPosVector = new Vector3D(tip, pos);
        //   int tipDotPosVectors =
        //          Vector3D.dotProduct(normedTipToBaseVector, tipToPosVector);
        //   int posDotPosVectors =
        //          Vector3D.dotProduct(tipToPosVector, tipToPosVector);
        // Avoiding the creation of Vector3D objects speeds up the running
        // time considerably (see BaseCylinder.java for measurements).
        // Since this method gets called very often this is worth the
        // decrease in readability.
        // Optimized:
        int tipToPosVectorX = pos.x - tip.x; 
        int tipToPosVectorY = pos.y - tip.y; 
        int tipToPosVectorZ = pos.z - tip.z;
        int tipDotPosVectors =
                normedTipToBaseVector.x * tipToPosVectorX +
                normedTipToBaseVector.y * tipToPosVectorY +
                normedTipToBaseVector.z * tipToPosVectorZ;
        int posDotPosVectors =
                tipToPosVectorX * tipToPosVectorX +
                tipToPosVectorY * tipToPosVectorY +
                tipToPosVectorZ * tipToPosVectorZ;
        
        if (tipDotPosVectors >= 0 &&
                tipDotPosVectors * tipDotPosVectors >= cosThetaSquared * posDotPosVectors)
        {
            // Check that the point is on the inside side of the cone's base plane.
            // The point is on the inside if the vector from the base's center to
            // the point is pointing in "the other direction" than the 'tip to base'
            // vector. "The other direction" meaning that its angle with the 'tip to
            // base' vector is >= 90 degrees, i.e. the dot product is <= 0.
            // This is not from the source above. Their cone's base is infinitely
            // far away.
            
            // Uoptimized:
            //   Vector3D baseToPosVector = new Vector3D(baseCenter, pos);
            //   int baseDotPosVectors =
            //          Vector3D.dotProduct(normedTipToBaseVector, baseToPosVector);
            // Optimized:
            int baseToPosVectorX = pos.x - baseCenter.x;
            int baseToPosVectorY = pos.y - baseCenter.y;
            int baseToPosVectorZ = pos.z - baseCenter.z;
            int baseDotPosVectors =
                    normedTipToBaseVector.x * baseToPosVectorX +
                    normedTipToBaseVector.y * baseToPosVectorY +
                    normedTipToBaseVector.z * baseToPosVectorZ;
            
            if (baseDotPosVectors <= 0)
                return true;
        }
        
        return false;
    }
}
