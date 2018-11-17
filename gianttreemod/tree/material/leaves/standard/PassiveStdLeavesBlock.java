package xmilcode.gianttreemod.tree.material.leaves.standard;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.NamingUtil;


// A leaves block without tile entity and that does not receive any update ticks.
// Standard leaves behave the same as other leaves in MC.
public class PassiveStdLeavesBlock extends BaseStdLeavesBlock
{
    private static final String NAME_ID = "passive_std_leaves";
    private static final String INTERNAL_NAME = "leavesStdPassive";
    private static final String[] TEXTURE_NAMES = new String[] {"passive_std_leaves", "passive_std_leaves_opaque"};

    
    public static PassiveStdLeavesBlock createAndRegister()
    {
        PassiveStdLeavesBlock block = new PassiveStdLeavesBlock();
        GameRegistry.registerBlock(block, StdLeavesItemBlock.class, INTERNAL_NAME);
        return block;
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
