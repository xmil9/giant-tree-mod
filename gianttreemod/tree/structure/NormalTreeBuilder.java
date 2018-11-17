package xmilcode.gianttreemod.tree.structure;

import java.nio.channels.IllegalSelectorException;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase.Height;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.structure.branch.Branch;
import xmilcode.mclib.loot.Loot;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


//Collects all data required to create a tree object.
public class NormalTreeBuilder
{
    private Coord3D pos;
    private int fullHeight;
    private int trunkRadius;
    private int trunkHeight;
    private GrowthManager growthMgr;
    private List<Branch> branches;
    private Loot loot;
    
    
    public NormalTreeBuilder()
    {
        pos = null;
        fullHeight = TreeSpec.DEFAULT_FULL_HEIGHT;
        trunkRadius = TreeSpec.DEFAULT_TRUNK_RADIUS;
        trunkHeight = TreeSpec.DEFAULT_TRUNK_HEIGHT;
        growthMgr = null;
        branches = null;
        loot = null;
    }
    
    
    public NormalTreeBuilder setPosition(Coord3D pos)
    {
        this.pos = pos;
        return this;
    }
    
    
    public NormalTreeBuilder setTrunkRadius(int radius)
    {
        this.trunkRadius = radius;
        return this;
    }
    
    
    public NormalTreeBuilder setTrunkHeight(int height)
    {
        this.trunkHeight = height;
        return this;
    }
    
    
    public NormalTreeBuilder setFullHeight(int height)
    {
        this.fullHeight = height;
        return this;
    }
    
    
    public NormalTreeBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public NormalTreeBuilder setBranches(List<Branch> branches)
    {
        this.branches = branches;
        return this;
    }
    
    
    public NormalTreeBuilder setLoot(Loot loot)
    {
        this.loot = loot;
        return this;
    }
    
    
    public Tree build()
    {
        if (growthMgr == null ||
                branches == null ||
                pos == null ||
                trunkRadius == TreeSpec.DEFAULT_TRUNK_RADIUS ||
                trunkHeight == TreeSpec.DEFAULT_TRUNK_HEIGHT ||
                fullHeight == TreeSpec.DEFAULT_FULL_HEIGHT)
        {
            throw new IllegalSelectorException();
        }
        
        Tree tree = TreeFactory.createNormalTree(
                new TreeSpec(pos, fullHeight, trunkRadius, trunkHeight));
        for (Branch branch : branches)
            tree.addBranch(branch);
        
        // The growth manager needs to be set after all growing parts of the
        // tree have been created or added to make sure it gets passed to
        // all growing parts.
        tree.setGrowthManager(growthMgr);

        if (loot != null)
            tree.setLoot(loot);
        
        return tree;
        
    }
}
