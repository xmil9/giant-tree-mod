package xmilcode.gianttreemod.tree.material.leaves;

import net.minecraftforge.common.config.Configuration;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.config.ConfigUtil;
import xmilcode.mclib.config.IConfigSettings;
import xmilcode.mclib.util.NamingUtil;


// Configurable settings for leaves.
public class LeavesSettings implements IConfigSettings
{
    public enum LeavesType { STANDARD_LEAVES, SIMPLE_BLOCK_LEAVES };
    
    private static final String LEAVES_TYPE_NAME_ID = "leaves_type";
    private static final String LEAVES_TYPE_INTERNAL_NAME = "leavesType";

    private LeavesType leavesType = LeavesType.SIMPLE_BLOCK_LEAVES;


    public LeavesType leavesType()
    {
        return leavesType;
    }
    
    
    @Override
    public void refreshSettings(Configuration config)
    {
        leavesType = LeavesType.valueOf(
                ConfigUtil.enumStringFromConfig(
                        config.getString(
                                LEAVES_TYPE_INTERNAL_NAME,
                                Configuration.CATEGORY_GENERAL,
                                ConfigUtil.configStringFromEnum(LeavesType.SIMPLE_BLOCK_LEAVES.toString()),
                                "The way giant tree leaves are rendered.",
                                getLeavesTypeValidValues(),
                                NamingUtil.generateConfigSettingName(GiantTreeMod.MODID, LEAVES_TYPE_NAME_ID))));
    }

    
    private static String[] getLeavesTypeValidValues()
    {
        return new String[] {
                ConfigUtil.configStringFromEnum(LeavesType.STANDARD_LEAVES.toString()),
                ConfigUtil.configStringFromEnum(LeavesType.SIMPLE_BLOCK_LEAVES.toString()),
        };
    }
}
