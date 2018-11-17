package xmilcode.mclib.block;

import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;


// Item block class that is needed to support multiple textures with
// one block.
public class MultiTextureItemBlock extends ItemBlock
{
    private MultiTextureBlock associatedBlock;

    
    public MultiTextureItemBlock(Block associatedBlock)
    {
        super(associatedBlock);
        this.associatedBlock = castToMultiTextureBlock(associatedBlock);
        setHasSubtypes(true);
    }

    private static MultiTextureBlock castToMultiTextureBlock(Block block)
    {
        if (block instanceof MultiTextureBlock)
            return (MultiTextureBlock)block;
        throw new IllegalStateException();
    }
    
    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return NamingUtil.generateUnlocalizedName(
                associatedBlock.modId(),
                associatedBlock.nameId());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int metadata)
    {
        final int side = 0;
        return associatedBlock.getIcon(side, metadata);
    }
}
