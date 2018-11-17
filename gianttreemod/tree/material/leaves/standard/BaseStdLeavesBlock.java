package xmilcode.gianttreemod.tree.material.leaves.standard;

import java.util.ArrayList;
import java.util.List;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockAtPos;
import xmilcode.gianttreemod.tree.material.leaves.LeavesAttributes;
import xmilcode.mclib.GameSettingsObserver;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


// Base class for giant tree standard leaves blocks.
// - Behave the same as other leaves in MC.
// - Manages block icons.
public abstract class BaseStdLeavesBlock extends BlockLeavesBase implements GameSettingsObserver
{
    public enum DrawMode
    {
        TRANSPARENT(0),
        OPAQUE(1);
        
        private int indexValue;
        
        private DrawMode(int indexValue)
        {
            this.indexValue = indexValue;
        }
        
        public int asIndex()
        {
            return indexValue;
        }
    };
    
    private static final int NUM_DRAW_MODES = DrawMode.values().length; 
    
    // Texture images indexed by draw mode.
    private IIcon icons[] = new IIcon[NUM_DRAW_MODES];


    protected BaseStdLeavesBlock()
    {
        super(LeavesAttributes.MATERIAL, Minecraft.getMinecraft().gameSettings.fancyGraphics);
        setCreativeTab(LeavesAttributes.TAB);
        setHardness(LeavesAttributes.HARDNESS);
        setResistance(LeavesAttributes.RESISTANCE);
        setLightOpacity(LeavesAttributes.LIGHT_OPACITY);
        setStepSound(LeavesAttributes.SOUND);
    }

    
    public abstract String nameId();
    protected abstract String[] textureNames();

    
    private DrawMode drawMode()
    {
        // 'field_150121_P' is a field of the BlockLeavesBase class and holds
        // a flag whether for draw a leaves object fancy (transparent) or fast
        // (opaque).
        return field_150121_P ? DrawMode.TRANSPARENT : DrawMode.OPAQUE;
    }
    
    
    private void setDrawMode(DrawMode mode)
    {
        field_150121_P = (mode == DrawMode.TRANSPARENT);
    }
    
    
    @Override
    public boolean isOpaqueCube()
    {
        return (drawMode() == DrawMode.OPAQUE);
    }

    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        Block block = blockAccess.getBlock(x, y, z);
        return !this.field_150121_P && block == this ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata)
    {
        return icons[drawMode().asIndex()];
    }

    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item block, CreativeTabs tabs, List subBlocks)
    {
        final int METADATA = 0;
        subBlocks.add(new ItemStack(block, 1, METADATA));
    }

    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        registerIconWithIndex(DrawMode.TRANSPARENT.asIndex(), iconRegister);
        registerIconWithIndex(DrawMode.OPAQUE.asIndex(), iconRegister);
    }
    
    
    @SideOnly(Side.CLIENT)
    private void registerIconWithIndex(int idx, IIconRegister iconRegister)
    {
        icons[idx] = iconRegister.registerIcon(
                NamingUtil.generateTextureName(GiantTreeMod.MODID, textureNames()[idx]));
    }
    
    
    @Override
    public ArrayList<ItemStack> getDrops(
            World world,
            int x,
            int y,
            int z,
            int metadata,
            int fortune)
    {
        // At this point the tree's block has already been removed from
        // the world. Therefore, we have to pass the metadata to the
        // tree instance explicitly.
        
        LeavesBlockAtPos blockAtPos =
                new LeavesBlockAtPos(world, new Coord3D(x, y, z), metadata);
        return blockAtPos.getDrops(fortune);
    }

    
    // Creates color for leaves based on a biome's foilage color.
    // Copied from BlockLeaves class.
    // Only called once at initialization.
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
                int i2 = blockAccess.getBiomeGenForCoords(x + l1, z + k1).getBiomeFoliageColor(x + l1, y, z + k1);
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }
    
    
    @Override
    public void onFancyGraphicsChanged(boolean newValue)
    {
        setDrawMode(newValue ? DrawMode.TRANSPARENT : DrawMode.OPAQUE);
    }
}
