package xmilcode.gianttreemod.config;

import xmilcode.gianttreemod.GiantTreeMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;


public class GtModConfigGuiScreen extends GuiConfig
{
    @SuppressWarnings("unchecked")
    public GtModConfigGuiScreen(GuiScreen parentScreen)
    {
        super(parentScreen,
              GtConfigModule.getConfigElementForCategory(Configuration.CATEGORY_GENERAL).getChildElements(),
              GiantTreeMod.MODID,
              false,
              false,
              GuiConfig.getAbridgedConfigPath(GtConfigModule.getConfigFilePath()));
    }
}
