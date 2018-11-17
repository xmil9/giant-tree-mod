package xmilcode.gianttreemod.tree.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.structure.branch.Branch;
import xmilcode.gianttreemod.tree.structure.branch.BranchSpec;
import xmilcode.gianttreemod.tree.structure.leaves.Leaves;
import xmilcode.gianttreemod.tree.structure.leaves.LeavesSpecGenerator;
import xmilcode.gianttreemod.tree.structure.leaves.SpecedLeavesBuilder;
import xmilcode.gianttreemod.tree.structure.stairs.NormalStairsBuilder;
import xmilcode.gianttreemod.tree.structure.stairs.Stairs;
import xmilcode.gianttreemod.tree.structure.stairs.StairsFactory;
import xmilcode.gianttreemod.tree.structure.tip.NormalTreeTipBuilder;
import xmilcode.gianttreemod.tree.structure.tip.TreeTip;
import xmilcode.gianttreemod.tree.structure.tip.TreeTipFactory;
import xmilcode.gianttreemod.tree.structure.tip.TreeTipSpecGenerator;
import xmilcode.gianttreemod.tree.structure.trunk.NormalTrunkBuilder;
import xmilcode.gianttreemod.tree.structure.trunk.Trunk;
import xmilcode.gianttreemod.tree.structure.trunk.TrunkFactory;
import xmilcode.mclib.loot.ChestLoot;
import xmilcode.mclib.loot.Loot;
import xmilcode.mclib.loot.NoLoot;
import xmilcode.mclib.loot.SingleUseLootDecorator;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


//Implements general stairs functionality.
public abstract class BaseTree implements Tree
{
    protected TreeSpec spec;
    protected Trunk trunk;
    protected Stairs stairs;
    protected TreeTip tip;
    protected List<Branch> branches;
    protected List<Leaves> allLeaves;
    protected GrowthManager growthMgr;
    protected SingleUseLootDecorator singleUseLoot;
    
    
    protected BaseTree(TreeSpec spec)
    {
        System.out.println("Giant tree created at " + spec.position().toString() + "." +
                " Radius:" + spec.trunkRadius() + ". Height:" + spec.fullHeight());

        this.growthMgr = new NullGrowthManager();
        this.spec = spec;
        this.trunk = createTrunk(spec, growthMgr);
        this.stairs = createStairs(spec, growthMgr);
        this.tip = createTip(spec, growthMgr);
        this.branches = new ArrayList<Branch>();
        this.allLeaves = new ArrayList<Leaves>();
        this.singleUseLoot = new SingleUseLootDecorator(new NoLoot());
    }

    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof BaseTree)
        {
            BaseTree cmp = (BaseTree)obj;
            return (corePosition().equals(cmp.corePosition()));
        }
        
        return false;
    }
    
    
    protected static Trunk createTrunk(TreeSpec treeSpec, GrowthManager growthMgr)
    {
        NormalTrunkBuilder trunkBuilder = new NormalTrunkBuilder();
        trunkBuilder.setPosition(treeSpec.position());
        trunkBuilder.setRadius(treeSpec.trunkRadius());
        trunkBuilder.setHeight(treeSpec.trunkHeight());
        trunkBuilder.setGrowthManager(growthMgr);
        return trunkBuilder.build();
    }
    
    
    protected static Stairs createStairs(TreeSpec treeSpec, GrowthManager growthMgr)
    {
        NormalStairsBuilder stairsBuilder = new NormalStairsBuilder();
        stairsBuilder.setBaseCenter(treeSpec.position());
        stairsBuilder.setTrunkRadius(treeSpec.trunkRadius());
        stairsBuilder.setHeight(treeSpec.trunkHeight());
        stairsBuilder.setGrowthManager(growthMgr);
        return stairsBuilder.build();
    }
    
    
    protected static TreeTip createTip(TreeSpec treeSpec, GrowthManager growthMgr)
    {
        NormalTreeTipBuilder tipBuilder = new NormalTreeTipBuilder();
        tipBuilder.setSpec(TreeTipSpecGenerator.generateFromTreeSpec(treeSpec));
        tipBuilder.setTreeCorePosition(treeSpec.position());
        tipBuilder.setGrowthManager(growthMgr);
        return tipBuilder.build();
    }
    
    
    protected static Leaves createLeaves(
            BranchSpec branchSpec,
            int atHeight,
            Coord3D treeCorePos,
            GrowthManager growthMgr)
    {
        LeavesSpecGenerator specGen = new LeavesSpecGenerator(branchSpec);
        
        SpecedLeavesBuilder builder = new SpecedLeavesBuilder();
        builder.setSpec(specGen.generate());
        builder.setAtHeight(atHeight);
        builder.setTreeCorePosition(treeCorePos);
        builder.setGrowthManager(growthMgr);
        return builder.build();
    }
    
    
    @Override
    public Coord3D corePosition()
    {
        return spec.position();
    }
    
    
    @Override
    public int trunkRadius()
    {
        return spec.trunkRadius();
    }
    
    
    @Override
    public int trunkHeight()
    {
        return spec.trunkHeight();
    }
    
    
    @Override
    public int fullHeight()
    {
        return spec.fullHeight();
    }

    
    @Override
    public void addBranch(Branch branch)
    {
        branches.add(branch);
        addLeaves(createLeaves(
                branch.spec(),
                branch.atTreeHeight(),
                spec.position(),
                growthMgr));
    }
    
    
    private void addLeaves(Leaves leaves)
    {
        allLeaves.add(leaves);
    }
    
    
    @Override
    public void setGrowthManager(GrowthManager mgr)
    {
        // We don't need to register the tree as growing part with the growth
        // manager because the tree object is not a growing part itself. The
        // tree object only holds the other growing parts.
        growthMgr = mgr;
        
        trunk.setGrowthManager(mgr);
        stairs.setGrowthManager(mgr);
        tip.setGrowthManager(mgr);
        for (Branch branch : branches)
            branch.setGrowthManager(mgr);
        for (Leaves leaves : allLeaves)
            leaves.setGrowthManager(mgr);
    }

    
    @Override
    public void setLoot(Loot loot)
    {
        this.singleUseLoot.setLoot(loot);
    }
    
    
    @Override
    public boolean growWoodBlockAt(TreeBlockAtPos sourceBlock)
    {
        if (trunk.growAt(sourceBlock))
        {
            checkForLootPlacement(sourceBlock);
            return true;
        }
        
        if (stairs.growAt(sourceBlock))
            return true;
        
        for (Branch branch : branches)
            if (branch.growAt(sourceBlock))
                return true;
        
        return false;
    }
    
    
    @Override
    public boolean growLeafBlockAt(TreeBlockAtPos sourceBlock)
    {
        if (tip.growAt(sourceBlock))
            return true;
        for (Leaves leaves : allLeaves)
            if (leaves.growAt(sourceBlock))
                return true;

        return false;
    }
    
    
    @Override
    public boolean growCoreBlockAt(TreeBlockAtPos sourceBlock)
    {
        if (trunk.growAt(sourceBlock))
            return true;
        return false;
    }
    
    
    private void checkForLootPlacement(TreeBlockAtPos block)
    {
        Coord3D pos = block.position();
        
        if (singleUseLoot.haveLoot() && trunk.isTop(pos))
        {
            Coord3D above = new Coord3D(pos.x, pos.y + 1, pos.z); 
            singleUseLoot.placeAt(block.world(), above);
        }
    }
}
