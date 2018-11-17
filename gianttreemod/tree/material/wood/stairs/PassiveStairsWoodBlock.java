package xmilcode.gianttreemod.tree.material.wood.stairs;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.common.registry.GameRegistry;


public class PassiveStairsWoodBlock extends BaseWoodBlock
{
    private static final String NAME_ID = "passive_wood_stairs";
    private static final String INTERNAL_NAME = "stairsWoodPassive";

    public PassiveStairsWoodBlock()
    {
        super();
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
    }

    public static PassiveStairsWoodBlock createAndRegister()
    {
        PassiveStairsWoodBlock block = new PassiveStairsWoodBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }
}
