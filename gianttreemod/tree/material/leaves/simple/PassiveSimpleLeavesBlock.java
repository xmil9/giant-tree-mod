package xmilcode.gianttreemod.tree.material.leaves.simple;

import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.leaves.LeavesAttributes;
import xmilcode.mclib.util.NamingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


// A leaves block without tile entity and that does not receive any update ticks.
// Simple leaves are plain blocks and do not behave like MC leaves. They render
// faster, though.
public class PassiveSimpleLeavesBlock extends Block
{
    private static final String NAME_ID = "passive_simple_leaves";
    private static final String INTERNAL_NAME = "leavesSimplePassive";

    
    public PassiveSimpleLeavesBlock()
    {
        super(LeavesAttributes.MATERIAL);
        
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
        
        setCreativeTab(LeavesAttributes.TAB);
        setHardness(LeavesAttributes.HARDNESS);
        setResistance(LeavesAttributes.RESISTANCE);
        setLightOpacity(LeavesAttributes.LIGHT_OPACITY);
        setStepSound(LeavesAttributes.SOUND);
    }
    
    
    public static PassiveSimpleLeavesBlock createAndRegister()
    {
        PassiveSimpleLeavesBlock leaves = new PassiveSimpleLeavesBlock();
        GameRegistry.registerBlock(leaves, INTERNAL_NAME);
        return leaves;
    }
}
