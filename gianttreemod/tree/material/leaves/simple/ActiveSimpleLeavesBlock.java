package xmilcode.gianttreemod.tree.material.leaves.simple;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockAtPos;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockTileEntity;
import xmilcode.gianttreemod.tree.material.leaves.LeavesAttributes;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.NamingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;



// A leaves block with a tile entity and that receives update ticks.
// This allows the block to exhibit growth behavior.
// Simple leaves are plain blocks and do not behave like MC leaves. They render
// faster, though.
public class ActiveSimpleLeavesBlock extends Block implements ITileEntityProvider
{
    private static final String NAME_ID = "active_simple_leaves";
    private static final String INTERNAL_NAME = "leavesSimpleActive";

    
    public ActiveSimpleLeavesBlock()
    {
        super(LeavesAttributes.MATERIAL);
        
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
        
        setCreativeTab(LeavesAttributes.TAB);
        setHardness(LeavesAttributes.HARDNESS);
        setResistance(LeavesAttributes.RESISTANCE);
        setLightOpacity(LeavesAttributes.LIGHT_OPACITY);
        setStepSound(LeavesAttributes.SOUND);
        
        setTickRandomly(true);
    }
    
    
    public static ActiveSimpleLeavesBlock createAndRegister()
    {
        ActiveSimpleLeavesBlock leaves = new ActiveSimpleLeavesBlock();
        GameRegistry.registerBlock(leaves, INTERNAL_NAME);
        return leaves;
    }

    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new LeavesBlockTileEntity();
    }
    
    
    // Code from BlockContainer.
    @Override
    public boolean onBlockEventReceived(
            World world,
            int x,
            int y,
            int z,
            int p_149696_5_,
            int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        
        // Forward client events to tile entity.
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity != null ?
                tileEntity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }

    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);
        
        LeavesBlockAtPos blockAtPos =
                new LeavesBlockAtPos(world, new Coord3D(x, y, z));
        blockAtPos.updateTick(rand);
    }
}
