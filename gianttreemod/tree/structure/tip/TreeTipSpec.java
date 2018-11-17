package xmilcode.gianttreemod.tree.structure.tip;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class TreeTipSpec
{
    public static final Coord3D DEFAULT_BASE_CENTER = new Coord3D();
    public static final int DEFAULT_RADIUS = 0;
    // The height cannot be zero because the cone shape will try to divide by it.
    public static final int DEFAULT_HEIGHT = 1;

    private Coord3D baseCenter;
    private int baseRadius;
    private int height;
    
    public TreeTipSpec()
    {
        this(DEFAULT_BASE_CENTER, DEFAULT_RADIUS, DEFAULT_HEIGHT);
    }
    
    public TreeTipSpec(
            Coord3D baseCenter,
            int baseRadius,
            int height)
    {
        this.baseCenter = baseCenter;
        this.baseRadius = baseRadius;
        this.height = (height > 1) ? height : DEFAULT_HEIGHT;
    }

    public Coord3D baseCenter()
    {
        return baseCenter;
    }

    public int baseRadius()
    {
        return baseRadius;
    }

    public int height()
    {
        return height;
    }
}
