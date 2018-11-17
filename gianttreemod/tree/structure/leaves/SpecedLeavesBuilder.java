package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class SpecedLeavesBuilder
{
    private static final int INVALID_AT_HEIGHT = -1;
    
    private LeavesSpec spec;
    private int atHeight;
    private Coord3D treeCorePos;
    private GrowthManager growthMgr;

    
    public SpecedLeavesBuilder()
    {
        spec = null;
        atHeight = INVALID_AT_HEIGHT;
        treeCorePos = null;
        growthMgr = null;
    }
    
    
    public SpecedLeavesBuilder setSpec(LeavesSpec spec)
    {
        this.spec = spec;
        return this;
    }
    
    
    public SpecedLeavesBuilder setAtHeight(int atHeight)
    {
        this.atHeight = atHeight;
        return this;
    }
    
    
    public SpecedLeavesBuilder setTreeCorePosition(Coord3D corePos)
    {
        this.treeCorePos = corePos;
        return this;
    }
    
    
    public SpecedLeavesBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Leaves build()
    {
        if (growthMgr == null ||
                spec == null ||
                atHeight == INVALID_AT_HEIGHT ||
                treeCorePos == null)
        {
            throw new IllegalStateException();
        }
        
        Leaves leaves = LeavesFactory.createNormalLeaves(
                spec,
                atHeight,
                treeCorePos);
        leaves.setGrowthManager(growthMgr);
        return leaves;
        
    }
}
