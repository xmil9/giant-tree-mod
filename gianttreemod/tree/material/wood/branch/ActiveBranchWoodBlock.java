package xmilcode.gianttreemod.tree.material.wood.branch;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.material.wood.ActiveWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.common.registry.GameRegistry;


public class ActiveBranchWoodBlock extends ActiveWoodBlock
{
    private static final String NAME_ID = "active_wood_branches";
    private static final String INTERNAL_NAME = "branchesWoodactive";

    public ActiveBranchWoodBlock()
    {
        super();
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
    }

    public static ActiveBranchWoodBlock createAndRegister()
    {
        ActiveBranchWoodBlock block = new ActiveBranchWoodBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }
    
    @Override
    protected BaseWoodBlock passiveWoodBlock()
    {
        return GiantTreeModule.passiveBranchWood;
    }
}
