package xmilcode.gianttreemod.tree.loot;

import net.minecraftforge.common.config.Configuration;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.config.ConfigUtil;
import xmilcode.mclib.config.IConfigSettings;
import xmilcode.mclib.util.NamingUtil;


// Configurable settings for loot.
public class TreeLootSettings implements IConfigSettings
{
    public enum LootQuantity { LITTLE, NORMAL, LOTS };
 
    private static final String LOOT_QUANTITY_NAME_ID = "tree_loot_quantity";
    private static final String LOOT_QUANTITY_INTERNAL_NAME = "treeLootQuantity";

    private LootQuantity lootQuantity = LootQuantity.NORMAL;


    public LootQuantity lootQuantity()
    {
        return lootQuantity;
    }
    
    
    @Override
    public void refreshSettings(Configuration config)
    {
        lootQuantity = LootQuantity.valueOf(
                ConfigUtil.enumStringFromConfig(
                        config.getString(
                                LOOT_QUANTITY_INTERNAL_NAME,
                                Configuration.CATEGORY_GENERAL,
                                ConfigUtil.configStringFromEnum(LootQuantity.NORMAL.toString()),
                                "Determines how much loot is placed on top of each tree.",
                                getLootQuantityValidValues(),
                                NamingUtil.generateConfigSettingName(GiantTreeMod.MODID, LOOT_QUANTITY_NAME_ID))));
    }

    
    private static String[] getLootQuantityValidValues()
    {
        return new String[] {
                ConfigUtil.configStringFromEnum(LootQuantity.LITTLE.toString()),
                ConfigUtil.configStringFromEnum(LootQuantity.NORMAL.toString()),
                ConfigUtil.configStringFromEnum(LootQuantity.LOTS.toString())
        };
    }
}
