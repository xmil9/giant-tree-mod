package xmilcode.gianttreemod.tree.material.leaves;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockNullAttributes;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockPersistentAttributes;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockTileEntity;
import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class LeavesBlockAtPos implements TreeBlockAtPos
{
    private final World world;
    private final Coord3D pos;
    // Occasionally a block is not part of the world and its metadata
    // cannot be accessed through the world object. In this case the
    // metadata can be passed explicitly into the ctor and is cached in
    // a field.
    private final int cachedMetadata;
    private final boolean useCachedMetadata;
    
    
    public LeavesBlockAtPos(World world, Coord3D pos, int metadata)
    {
        this(world, pos, metadata, true);
    }
    
    
    public LeavesBlockAtPos(World world, Coord3D pos)
    {
        this(world, pos, 0, false);
    }
    
   
    private LeavesBlockAtPos(
            World world,
            Coord3D pos,
            int metadata,
            boolean useMetadata)
    {
        this.world = world;
        this.pos = pos;
        this.cachedMetadata = metadata;
        this.useCachedMetadata = useMetadata;
    }
    
    
    @Override
    public Coord3D position()
    {
        return pos;
    }
    
    
    @Override
    public World world()
    {
        return world;
    }


    @Override
    public boolean canGrow()
    {
        return true;
    }


    @Override
    public void markAsDoneGrowing()
    {
        // Replace the block with a passive block that won't received
        // update ticks and, therefore, cannot grow anymore.
        world.setBlock(pos.x, pos.y, pos.z, GiantTreeModule.passiveLeavesBlock());
    }

    
    public void updateTick(Random rand)
    {
        tree().growLeafBlockAt(this);
    }

    
    private Tree tree()
    {
        return getCorePersistentAttributes().tree();
    }
    
    
    public void setTreeCorePosition(Coord3D corePos)
    {
        getPersistentAttributes().setTreeCorePosition(corePos);
    }
    
    
    private LeavesBlockPersistentAttributes getPersistentAttributes()
    {
        TileEntity blockTe = world.getTileEntity(pos.x, pos.y, pos.z);
        if (blockTe != null && blockTe instanceof LeavesBlockTileEntity)
        {
            return (LeavesBlockTileEntity)blockTe;
        }
        else
        {
            System.out.println(
                    "Warning: Leaves block at " + pos.toString() + " has no tile entity!");
            return new LeavesBlockNullAttributes();
        }
    }
    
    
    private TreeCoreBlockPersistentAttributes getCorePersistentAttributes()
    {
        Coord3D corePos = getPersistentAttributes().treeCorePosition();
        
        TileEntity coreTe = null;
        if (corePos != null)
            coreTe = world.getTileEntity(corePos.x, corePos.y, corePos.z);
        
        if (coreTe != null && coreTe instanceof TreeCoreBlockTileEntity)
        {
            return (TreeCoreBlockTileEntity)coreTe;
        }
        else
        {
            System.out.println(
                    "Warning: Giant tree core block at (" +
                    pos.x + "," + pos.y + "," + pos.z + ") has no tile entity!");
            return new TreeCoreBlockNullAttributes();
        }
    }
    
    
    public ArrayList<ItemStack> getDrops(int fortune)
    {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        if (world.rand.nextInt(15) < 1 + 2*fortune)
            drops.add(new ItemStack(GiantTreeModule.giantTreeNut, 1, 0));

        if (world.rand.nextInt(40) < 1 + 2*fortune)
            drops.add(new ItemStack(Items.apple, 1, 0));

        if (world.rand.nextInt(1000) < 1 + 5*fortune)
            drops.add(new ItemStack(GiantTreeModule.giantTreeSeed, 1, 0));
        
        return drops;
    }
}
