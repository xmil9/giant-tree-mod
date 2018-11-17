package xmilcode.gianttreemod.tree.structure;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class TreeSpec
{
    static final Coord3D DEFAULT_POSITION = new Coord3D();
    static final int DEFAULT_FULL_HEIGHT = 0;
    static final int DEFAULT_TRUNK_RADIUS = 0;
    static final int DEFAULT_TRUNK_HEIGHT = 0;


    private Coord3D pos;
    private int fullHeight;
    private int trunkRadius;
    private int trunkHeight;
    
    
    public TreeSpec()
    {
        this.pos = DEFAULT_POSITION;
        this.fullHeight = DEFAULT_FULL_HEIGHT;
        this.trunkRadius = DEFAULT_TRUNK_RADIUS;
        this.trunkHeight = DEFAULT_TRUNK_HEIGHT;
    }
    
    
    public TreeSpec(
            Coord3D pos,
            int fullHeight,
            int trunkRadius,
            int trunkHeight)
    {
        this.pos = pos;
        this.fullHeight = fullHeight;
        this.trunkRadius = trunkRadius;
        this.trunkHeight = trunkHeight;
    }


    public Coord3D position()
    {
        return pos;
    }


    public int fullHeight()
    {
        return fullHeight;
    }


    public int trunkRadius()
    {
        return trunkRadius;
    }


    public int trunkHeight()
    {
        return trunkHeight;
    }
}
