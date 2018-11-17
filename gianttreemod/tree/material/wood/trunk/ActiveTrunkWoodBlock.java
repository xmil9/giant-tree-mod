package xmilcode.gianttreemod.tree.material.wood.trunk;

import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.material.wood.ActiveWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.mclib.util.NamingUtil;



public class ActiveTrunkWoodBlock extends ActiveWoodBlock
{
    private static final String NAME_ID = "active_wood_trunk";
    private static final String INTERNAL_NAME = "trunkWoodactive";

    public ActiveTrunkWoodBlock()
    {
        super();
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
    }
    
    public static ActiveTrunkWoodBlock createAndRegister()
    {
        ActiveTrunkWoodBlock block = new ActiveTrunkWoodBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }
    
    @Override
    protected BaseWoodBlock passiveWoodBlock()
    {
        return GiantTreeModule.passiveTrunkWood;
    }
}
