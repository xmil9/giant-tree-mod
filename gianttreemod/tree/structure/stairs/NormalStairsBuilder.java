package xmilcode.gianttreemod.tree.structure.stairs;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;



// Collects all data required to create a stairs object.
public class NormalStairsBuilder
{
    private static final int INVALID_RADIUS = -1;
    private static final int INVALID_HEIGHT = -1;
    
    private Coord3D baseCenter;
    private int trunkRadius;
    private int height;
    private GrowthManager growthMgr;
    
    
    public NormalStairsBuilder()
    {
        baseCenter = null;
        trunkRadius = INVALID_RADIUS;
        height = INVALID_HEIGHT;
        growthMgr = null;
    }
    
    
    public NormalStairsBuilder setBaseCenter(Coord3D center)
    {
        this.baseCenter = center;
        return this;
    }
    
    
    public NormalStairsBuilder setTrunkRadius(int radius)
    {
        this.trunkRadius = radius;
        return this;
    }
    
    
    public NormalStairsBuilder setHeight(int height)
    {
        this.height = height;
        return this;
    }
    
    
    public NormalStairsBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Stairs build()
    {
        if (growthMgr == null ||
                baseCenter == null ||
                trunkRadius == INVALID_RADIUS ||
                height == INVALID_HEIGHT)
        {
            throw new IllegalStateException();
        }
        
        Stairs stairs = StairsFactory.createNormalStairs(
                new StairsSpec(baseCenter, trunkRadius, height));
        stairs.setGrowthManager(growthMgr);
        return stairs;
        
    }
}
