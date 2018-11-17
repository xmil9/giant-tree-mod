package xmilcode.gianttreemod.tree.material.wood.branch;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.common.registry.GameRegistry;


public class PassiveBranchWoodBlock extends BaseWoodBlock
{
    private static final String NAME_ID = "passive_wood_branches";
    private static final String INTERNAL_NAME = "branchesWoodPassive";

    public PassiveBranchWoodBlock()
    {
        super();
        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
    }

    public static PassiveBranchWoodBlock createAndRegister()
    {
        PassiveBranchWoodBlock block = new PassiveBranchWoodBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }
}
