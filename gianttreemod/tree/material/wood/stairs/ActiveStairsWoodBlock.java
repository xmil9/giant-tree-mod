package xmilcode.gianttreemod.tree.material.wood.stairs;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.material.wood.ActiveWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.common.registry.GameRegistry;               


public class ActiveStairsWoodBlock extends ActiveWoodBlock
{
    private static final String NAME_ID = "active_wood_stairs";
    private static final String INTERNAL_NAME = "stairsWoodactive";

    public ActiveStairsWoodBlock()
    {
        super();
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
    }

    public static ActiveStairsWoodBlock createAndRegister()
    {
        ActiveStairsWoodBlock block = new ActiveStairsWoodBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }
    
    @Override
    protected BaseWoodBlock passiveWoodBlock()
    {
        return GiantTreeModule.passiveStairsWood;
    }
}
