package xmilcode.gianttreemod.tree.structure.stairs;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class StairsSpec
{
    public static final Coord3D DEFAULT_BASE_CENTER = new Coord3D();
    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_HEIGHT = 0;

    private Coord3D baseCenter;
    private int trunkRadius;
    private int height;
    
    public StairsSpec()
    {
        this.baseCenter = DEFAULT_BASE_CENTER;
        this.trunkRadius = DEFAULT_RADIUS;
        this.height = DEFAULT_HEIGHT;
    }
    
    public StairsSpec(
            Coord3D baseCenter,
            int trunkRadius,
            int height)
    {
        this.baseCenter = baseCenter;
        this.trunkRadius = trunkRadius;
        this.height = height;
    }

    public Coord3D baseCenter()
    {
        return baseCenter;
    }

    public int trunkRadius()
    {
        return trunkRadius;
    }

    public int height()
    {
        return height;
    }
}
