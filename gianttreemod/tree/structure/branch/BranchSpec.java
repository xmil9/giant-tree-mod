package xmilcode.gianttreemod.tree.structure.branch;

import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class BranchSpec
{
    public static final Vector3D DEFAULT_AXIS = Axis3D.X.axisVector();
    public static final Coord3D DEFAULT_POSITION = new Coord3D();
    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_LENGTH = 0;

    private Vector3D axis;
    private Coord3D pos;
    private int radius;
    private int length;
    
    public BranchSpec()
    {
        this(DEFAULT_AXIS,
                DEFAULT_POSITION,
                DEFAULT_RADIUS,
                DEFAULT_LENGTH);
    }
    
    public BranchSpec(
            Vector3D axis,
            Coord3D branchPos,
            int radius,
            int length)
    {
        if (!Axis3D.isAxisVector(axis))
            throw new IllegalArgumentException();
        
        this.axis = Vector3D.normalize(axis);
        this.pos = branchPos;
        this.radius = radius;
        this.length = length;
    }

    public Vector3D axis()
    {
        return axis;
    }

    public Coord3D position()
    {
        return pos;
    }

    public int radius()
    {
        return radius;
    }

    public int length()
    {
        return length;
    }
}
