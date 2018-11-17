package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class LeavesSpec
{
    public static final Vector3D DEFAULT_AXIS = new Vector3D();
    public static final Coord3D DEFAULT_POSITION = new Coord3D();
    public static final int DEFAULT_LENGTH = 0;
    public static final int DEFAULT_WIDTH = 0;
    public static final int DEFAULT_HEIGHT = 0;

    
    // Axis along the tree's branch that the leaves belong to.
    private Vector3D branchAxis;
    // The center of the base triangle leg that runs perpendicular to the
    // tree's branch closest to the trunk.
    private Coord3D pos;
    // 'Length' measures the leaves' triangle along the branch's axis.
    private int length;
    // 'Width' measures the leaves' triangle perpendicular to the branch's
    // axis.
    private int width;
    // 'Height' measures the thickness of the leaves' shape in y-direction
    // (upwards).
    private int height;
    
    
    public LeavesSpec()
    {
        this(DEFAULT_AXIS,
                DEFAULT_POSITION,
                DEFAULT_LENGTH,
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT);
    }
    
    public LeavesSpec(
            Vector3D branchAxis,
            Coord3D pos,
            int length,
            int width,
            int height)
    {
        this.branchAxis = branchAxis;
        this.pos = pos;
        this.length = length;
        this.width = width;
        this.height = height;
    }
    
    public Vector3D branchAxis()
    {
        return branchAxis;
    }

    public Coord3D position()
    {
        return pos;
    }

    public int length()
    {
        return length;
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }
}
