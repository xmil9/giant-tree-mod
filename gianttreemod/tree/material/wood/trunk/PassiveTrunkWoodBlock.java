package xmilcode.gianttreemod.tree.material.wood.trunk;

import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.mclib.util.NamingUtil;



public class PassiveTrunkWoodBlock extends BaseWoodBlock
{
    private static final String NAME_ID = "passive_wood_trunk";
    private static final String INTERNAL_NAME = "trunkWoodPassive";

    public PassiveTrunkWoodBlock()
    {
        super();
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
    }

    public static PassiveTrunkWoodBlock createAndRegister()
    {
        PassiveTrunkWoodBlock block = new PassiveTrunkWoodBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }
}
