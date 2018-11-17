package xmilcode.gianttreemod.tree.material.leaves.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockAtPos;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockTileEntity;
import xmilcode.gianttreemod.tree.material.replacementstrategies.AirLikeReplacementStrategy;
import xmilcode.gianttreemod.tree.material.replacementstrategies.BlockReplacementStrategy;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.NamingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;



// A leaves block with a tile entity and that receives update ticks.
// This allows the block to exhibit growth behavior.
// Standard leaves behave the same as other leaves in MC.
public class ActiveStdLeavesBlock extends BaseStdLeavesBlock implements ITileEntityProvider
{
    private static final String NAME_ID = "active_std_leaves";
    private static final String INTERNAL_NAME = "leavesStdActive";
    private static final String[] TEXTURE_NAMES = new String[] {"active_std_leaves", "active_std_leaves_opaque"};
    
    
    public ActiveStdLeavesBlock()
    {
        super();
        setTickRandomly(true);
    }


    public static ActiveStdLeavesBlock createAndRegister()
    {
        ActiveStdLeavesBlock block = new ActiveStdLeavesBlock();
        GameRegistry.registerBlock(block, StdLeavesItemBlock.class, INTERNAL_NAME);
        return block;
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


    @Override
    public String nameId()
    {
        return NAME_ID;
    }


    @Override
    protected String[] textureNames()
    {
        return TEXTURE_NAMES;
    }
}
