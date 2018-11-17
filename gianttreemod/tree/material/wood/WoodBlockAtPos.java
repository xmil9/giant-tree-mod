package xmilcode.gianttreemod.tree.material.wood;

import java.util.ArrayList;
import java.util.Random;

import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockNullAttributes;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockPersistentAttributes;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockTileEntity;
import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Neighbor3D;
import xmilcode.mclib.util.MCConstants;
import xmilcode.mclib.util.MathUtil;
import xmilcode.mclib.util.RandomUtil;
import xmilcode.mclib.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class WoodBlockAtPos implements TreeBlockAtPos
{
    private final World world;
    private final Coord3D pos;
    // Occasionally a block is not part of the world and its metadata
    // cannot be accessed through the world object. In this case the
    // metadata can be passed explicitly into the ctor and is cached in
    // a field.
    private final int cachedMetadata;
    private final boolean useCachedMetadata;
    private final BaseWoodBlock passiveWoodBlock;
    
    
    public WoodBlockAtPos(
            World world,
            Coord3D pos,
            int metadata,
            BaseWoodBlock passiveWoodBlock)
    {
        this(world, pos, metadata, true, passiveWoodBlock);
    }
    
    
    public WoodBlockAtPos(
            World world,
            Coord3D pos,
            BaseWoodBlock passiveWoodBlock)
    {
        this(world, pos, 0, false, passiveWoodBlock);
    }
    
   
    private WoodBlockAtPos(
            World world,
            Coord3D pos,
            int metadata,
            boolean useMetadata,
            BaseWoodBlock passiveWoodBlock)
    {
        this.world = world;
        this.pos = pos;
        this.cachedMetadata = metadata;
        this.useCachedMetadata = useMetadata;
        // Might be better to provide the passive block through inheritance
        // instead of passing it in.
        this.passiveWoodBlock = passiveWoodBlock;
    }
    
    
    private int metadata()
    {
        if (useCachedMetadata)
            return cachedMetadata;
        return world.getBlockMetadata(pos.x, pos.y, pos.z);
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
        world.setBlock(
                pos.x,
                pos.y,
                pos.z,
                passiveWoodBlock,
                metadata(),
                MCConstants.UPDATE_BLOCK_FLAG | MCConstants.NOTIFY_CLIENT_FLAG);
    }

    
    void updateTick(Random rand)
    {
        tree().growWoodBlockAt(this);
    }

    
    private Tree tree()
    {
        return getCorePersistentAttributes().tree();
    }
    
    
    public void setTreeCorePosition(Coord3D corePos)
    {
        getPersistentAttributes().setTreeCorePosition(corePos);
    }
    
    
    private WoodBlockPersistentAttributes getPersistentAttributes()
    {
        TileEntity blockTe = world.getTileEntity(pos.x, pos.y, pos.z);
        if (blockTe != null && blockTe instanceof WoodBlockTileEntity)
        {
            return (WoodBlockTileEntity)blockTe;
        }
        else
        {
            System.out.println(
                    "Warning: Wood block at " + pos.toString() + " has no tile entity!");
            return new WoodBlockNullAttributes();
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
}
