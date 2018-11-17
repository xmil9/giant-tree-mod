package xmilcode.gianttreemod.tree.material.leaves.standard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.util.NamingUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;



// Item block class that is needed to support multiple textures with
// one block.
public class StdLeavesItemBlock extends ItemBlock
{
    public StdLeavesItemBlock(Block block)
    {
        super(block);
        setHasSubtypes(true);
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
                GiantTreeMod.MODID,
                associatedLeavesBlock().nameId());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int metadata)
    {
        int side = 0;
        return associatedLeavesBlock().getIcon(side, metadata);
    }
    
    private BaseStdLeavesBlock associatedLeavesBlock()
    {
        if (field_150939_a instanceof BaseStdLeavesBlock)
            return (BaseStdLeavesBlock)field_150939_a;
        
        throw new IllegalStateException();
    }
}
