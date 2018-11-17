package xmilcode.gianttreemod.tree.structure.trunk;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class TrunkSpec
{
    public static final Coord3D DEFAULT_POSITION = new Coord3D();
    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_HEIGHT = 0;

    private Coord3D pos;
    private int radius;
    private int height;
    
    public TrunkSpec()
    {
        this.pos = DEFAULT_POSITION;
        this.radius = DEFAULT_RADIUS;
        this.height = DEFAULT_HEIGHT;
    }

    public TrunkSpec(
            Coord3D pos,
            int radius,
            int height)
    {
        this.pos = pos;
        this.radius = radius;
        this.height = height;
    }

    public Coord3D position()
    {
        return pos;
    }

    public int radius()
    {
        return radius;
    }

    public int height()
    {
        return height;
    }
}
