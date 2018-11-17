package xmilcode.gianttreemod.tree.structure.tip;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;



public class NormalTreeTipBuilder
{
    private TreeTipSpec spec;
    private Coord3D treeCorePos;
    private GrowthManager growthMgr;

    
    public NormalTreeTipBuilder()
    {
        spec = null;
        treeCorePos = null;
        growthMgr = null;
    }
    
    
    public NormalTreeTipBuilder setSpec(TreeTipSpec spec)
    {
        this.spec = spec;
        return this;
    }

    
    public NormalTreeTipBuilder setTreeCorePosition(Coord3D corePos)
    {
        this.treeCorePos = corePos;
        return this;
    }
    
    
    public NormalTreeTipBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public TreeTip build()
    {
        if (growthMgr == null ||
                spec == null ||
                treeCorePos == null)
        {
            throw new IllegalStateException();
        }
        
        TreeTip tip = TreeTipFactory.createNormalTip(spec, treeCorePos);
        tip.setGrowthManager(growthMgr);
        return tip;
        
    }
}
