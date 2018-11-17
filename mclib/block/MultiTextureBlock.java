package xmilcode.mclib.block;

import java.util.List;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;


// Implements support for multiple textures on one block.
// See usage example below.
public abstract class MultiTextureBlock extends Block
{
    private int numTextures;
    // Texture images indexed by metadata.
    private IIcon[] icons;

    
    protected MultiTextureBlock(Material material, int numTextures)
    {
        super(material);
        this.numTextures = numTextures;
        this.icons = new IIcon[numTextures];
    }

    // Subclass has to provide the name id of the block.
    // E.g.: "wood".
    public abstract String nameId();
    // Subclass has to provide the id of its mod.
    public abstract String modId();
    // Subclass has to provide an array of texture names.
    // E.g.: { "wood_trunk", "wood_stairs", "wood_branches" }.
    protected abstract String[] textureNames();
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata)
    {
        return icons[metadata];
    }

    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item block, CreativeTabs tabs, List subBlocks)
    {
        for (int metadata = 0; metadata < numTextures; ++metadata)
            subBlocks.add(new ItemStack(block, 1, metadata));
    }

    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int metadata = 0; metadata < numTextures; ++metadata)
            registerIconWithIndex(metadata, iconRegister);
    }
    
    
    @SideOnly(Side.CLIENT)
    private void registerIconWithIndex(int idx, IIconRegister iconRegister)
    {
        icons[idx] = iconRegister.registerIcon(
                NamingUtil.generateTextureName(modId(), textureNames()[idx]));
    }
}


// Usage example:

//public class WoodBlock extends MultiTextureBlock
//{
//    private static final String NAME_ID = "wood";
//    private static final String INTERNAL_NAME = "wood";
//    private static final String[] TEXTURE_NAMES = new String[] {
//        "wood_trunk", "wood_stairs", "wood_branches" };
//
//
//    public WoodBlock()
//    {
//        super(Material.wood, TEXTURE_NAMES.length);
//        
//        setCreativeTab((CreativeTabs)null);
//        setHardness(2.0F);
//        setResistance(5.0F);
//        setStepSound(Block.soundTypeWood);
//    }
//    
//    public static WoodBlock createAndRegister()
//    {
//        WoodBlock block = new WookBlock();
//        GameRegistry.registerBlock(block, MultiTextureItemBlock.class, INTERNAL_NAME);
//        return block;
//    }
//
//    @Override
//    public String nameId()
//    {
//        return NAME_ID;
//    }
//
//    @Override
//    protected String[] textureNames()
//    {
//        return TEXTURE_NAMES;
//    }
//
//    @Override
//    public String modId()
//    {
//        return GiantTreeMod.MODID;
//    }
//}
