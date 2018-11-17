package xmilcode.gianttreemod.tree.material.wood;

import xmilcode.gianttreemod.tree.material.replacementstrategies.BlockReplacementStrategy;
import xmilcode.gianttreemod.tree.material.replacementstrategies.SoftMaterialsReplacementStrategy;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


public class WoodAttributes
{
    public static final Material MATERIAL = Material.wood;
    public static final CreativeTabs TAB = (CreativeTabs)null; // No tab.
    public static final float HARDNESS = 2.0F;
    public static final float RESISTANCE = 5.0F;
    public static final SoundType SOUND = Block.soundTypeWood;

    public static final BlockReplacementStrategy replacementStrategy =
            new SoftMaterialsReplacementStrategy();
}
