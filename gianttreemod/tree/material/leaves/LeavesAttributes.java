package xmilcode.gianttreemod.tree.material.leaves;

import xmilcode.gianttreemod.tree.material.replacementstrategies.AirLikeReplacementStrategy;
import xmilcode.gianttreemod.tree.material.replacementstrategies.BlockReplacementStrategy;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


// Settings for properties shared by all leave types.
public class LeavesAttributes
{
    public static final Material MATERIAL = Material.leaves;
    public static final CreativeTabs TAB = (CreativeTabs)null; // No tab.
    public static final float HARDNESS = 0.2F;
    public static final float RESISTANCE = 1.0F;
    public static final int LIGHT_OPACITY = 1;
    public static final SoundType SOUND = Block.soundTypeGrass;

    public static final BlockReplacementStrategy REPLACEMENT_STRATEGY =
            new AirLikeReplacementStrategy();
}
