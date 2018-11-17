package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.cuboid.Cuboid;
import xmilcode.mclib.pixelgeometry3d.cuboid.CuboidGeometry;
import xmilcode.mclib.pixelgeometry3d.triangularprism.CcwTriangularPrism;
import xmilcode.mclib.pixelgeometry3d.triangularprism.TriangularPrism;
import xmilcode.mclib.util.MathUtil;



// From a leaves specification generates the prism shape that forms the
// leaves of a branch.
public class LeavesShapeGenerator
{
    private LeavesSpec spec;
    
    
    public LeavesShapeGenerator(LeavesSpec spec)
    {
        this.spec = spec;
    }
    
    
    public TriangularPrism generateShape()
    {
        // The vertices of the triangle are created to be in counter-clockwise
        // order.
        return new CcwTriangularPrism(
                Axis3D.Y.axisVector(),
                generateRightVertex(),
                generateOuterVertex(),
                generateLeftVertex(),
                spec.height());
    }

    
    public Cuboid generateBounds()
    {
        final int OFFSET_TO_INCL_BORDER_PIXELS = 1;

        Coord3D rightVertex = generateRightVertex();
        Coord3D outerVertex = generateOuterVertex();
        Coord3D leftVertex = generateLeftVertex();
        
        Coord3D baseCorner = new Coord3D(
                MathUtil.min(rightVertex.x, outerVertex.x, leftVertex.x),
                spec.position().y,
                MathUtil.min(rightVertex.z, outerVertex.z, leftVertex.z));
        Coord3D oppositeCorner = new Coord3D(
                MathUtil.max(rightVertex.x, outerVertex.x, leftVertex.x) + OFFSET_TO_INCL_BORDER_PIXELS,
                spec.position().y + spec.height() + OFFSET_TO_INCL_BORDER_PIXELS,
                MathUtil.max(rightVertex.z, outerVertex.z, leftVertex.z) + OFFSET_TO_INCL_BORDER_PIXELS);
        
        return new CuboidGeometry(baseCorner, oppositeCorner);
    }
    
    private Coord3D generateRightVertex()
    {
        Vector3D trunkTangentAxis = getCcwPerpendicularAxisInXZPlane(spec.branchAxis());

        return Vector3D.add(
                spec.position(),
                Vector3D.muliply(trunkTangentAxis, spec.width() / 2.0));
    }

    
    private Coord3D generateOuterVertex()
    {
        return Vector3D.add(
                spec.position(),
                Vector3D.muliply(spec.branchAxis(), spec.length()));
    }

    
    private Coord3D generateLeftVertex()
    {
        Vector3D trunkTangentAxis = getCcwPerpendicularAxisInXZPlane(spec.branchAxis());

        return Vector3D.add(
                spec.position(),
                Vector3D.muliply(trunkTangentAxis, -spec.width() / 2.0));
    }
    
    
    private static Vector3D getCcwPerpendicularAxisInXZPlane(Vector3D axis)
    {
        if (axis.x > 0)
        {
            return Vector3D.reverse(Axis3D.Z.axisVector());
        }
        else if (axis.x < 0)
        {
            return Axis3D.Z.axisVector();
        }
        else if (axis.z > 0)
        {
            return Axis3D.X.axisVector();
        }
        else if (axis.z < 0)
        {
            return Vector3D.reverse(Axis3D.X.axisVector());
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
