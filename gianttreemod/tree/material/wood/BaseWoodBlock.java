package xmilcode.gianttreemod.tree.material.wood;

import java.util.ArrayList;
import java.util.List;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.block.MultiTextureBlock;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


// Base class for all wood blocks.
public class BaseWoodBlock extends Block
{
    protected BaseWoodBlock()
    {
        super(WoodAttributes.MATERIAL);
        
        setCreativeTab(WoodAttributes.TAB);
        setHardness(WoodAttributes.HARDNESS);
        setResistance(WoodAttributes.RESISTANCE);
        setStepSound(WoodAttributes.SOUND);
    }
}
