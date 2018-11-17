package xmilcode.gianttreemod.mob;

import net.minecraftforge.common.config.Configuration;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.config.ConfigUtil;
import xmilcode.mclib.config.IConfigSettings;
import xmilcode.mclib.util.NamingUtil;


// Configurable settings for mobs.
public class GtMobSettings implements IConfigSettings
{
    public enum MobFrequency { FEW, NORMAL, MANY };
    
    private static final String MOB_FREQ_NAME_ID = "mob_frequency";
    private static final String MOB_FREQ_INTERNAL_NAME = "mobFrequency";

    private MobFrequency mobFrequency = MobFrequency.NORMAL;


    public MobFrequency mobFrequency()
    {
        return mobFrequency;
    }
    
    
    @Override
    public void refreshSettings(Configuration config)
    {
        mobFrequency = MobFrequency.valueOf(
                ConfigUtil.enumStringFromConfig(
                        config.getString(
                                MOB_FREQ_INTERNAL_NAME,
                                Configuration.CATEGORY_GENERAL,
                                ConfigUtil.configStringFromEnum(MobFrequency.NORMAL.toString()),
                                "Determines how many mobs are spawned.",
                                getMobFrequencyValidValues(),
                                NamingUtil.generateConfigSettingName(GiantTreeMod.MODID, MOB_FREQ_NAME_ID))));
    }

    
    private static String[] getMobFrequencyValidValues()
    {
        return new String[] {
                ConfigUtil.configStringFromEnum(MobFrequency.FEW.toString()),
                ConfigUtil.configStringFromEnum(MobFrequency.NORMAL.toString()),
                ConfigUtil.configStringFromEnum(MobFrequency.MANY.toString())
        };
    }
}
