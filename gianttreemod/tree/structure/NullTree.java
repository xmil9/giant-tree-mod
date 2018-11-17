package xmilcode.gianttreemod.tree.structure;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.structure.branch.Branch;
import xmilcode.mclib.loot.Loot;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


//Null pattern for tree objects.
public class NullTree implements Tree
{
    public static final String TYPE_ID = "null_tree";
    
    
    // Package visibility only! Factory should be used to create instances.
    NullTree()
    {
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof NullTree);
    }
    
    @Override
    public String typeId()
    {
        return TYPE_ID;
    }
    
    @Override
    public Coord3D corePosition()
    {
        return new Coord3D();
    }
    
    @Override
    public int trunkRadius()
    {
        return 0;
    }
    
    @Override
    public int trunkHeight()
    {
        return 0;
    }
    
    @Override
    public int fullHeight()
    {
        return 0;
    }

    @Override
    public void addBranch(Branch branch)
    {
    }
    
    @Override
    public void setGrowthManager(GrowthManager mgr)
    {
    }

    @Override
    public void setLoot(Loot loot)
    {
    }

    @Override
    public boolean growWoodBlockAt(TreeBlockAtPos sourceBlock)
    {
        return false;
    }

    @Override
    public boolean growLeafBlockAt(TreeBlockAtPos sourceBlock)
    {
        return false;
    }

    @Override
    public boolean growCoreBlockAt(TreeBlockAtPos sourceBlock)
    {
        return false;
    }
}
