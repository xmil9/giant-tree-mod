package xmilcode.gianttreemod.tree.structure.trunk;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Collects all data required to create a trunk object.
public class NormalTrunkBuilder
{
    private static final int INVALID_RADIUS = -1;
    private static final int INVALID_HEIGHT = -1;
    
    private Coord3D pos;
    private int radius;
    private int height;
    private GrowthManager growthMgr;
    
    
    public NormalTrunkBuilder()
    {
        pos = null;
        radius = INVALID_RADIUS;
        height = INVALID_HEIGHT;
        growthMgr = null;
    }
    
    
    public NormalTrunkBuilder setPosition(Coord3D pos)
    {
        this.pos = pos;
        return this;
    }
    
    
    public NormalTrunkBuilder setRadius(int radius)
    {
        this.radius = radius;
        return this;
    }
    
    
    public NormalTrunkBuilder setHeight(int height)
    {
        this.height = height;
        return this;
    }
    
    
    public NormalTrunkBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Trunk build()
    {
        if (growthMgr == null ||
                pos == null ||
                radius == INVALID_RADIUS ||
                height == INVALID_HEIGHT)
        {
            throw new IllegalStateException();
        }
        
        Trunk trunk = TrunkFactory.createNormalTrunk(
                new TrunkSpec(pos, radius, height));
        trunk.setGrowthManager(growthMgr);
        return trunk;
        
    }
}
