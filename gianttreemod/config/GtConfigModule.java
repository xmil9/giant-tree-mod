package xmilcode.gianttreemod.config;

import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.ModModule;
import xmilcode.mclib.ModBase;
import xmilcode.mclib.config.ConfigManager;



public class GtConfigModule implements ModModule
{
    private static ConfigManager configMgr;

    
    public static ConfigManager configManager()
    {
        return configMgr;
    }
    
    public static IConfigElement getConfigElementForCategory(String category)
    {
        return new ConfigElement(configMgr.getConfiguration().getCategory(category));
    }
    
    public static String getConfigFilePath()
    {
        return configMgr.getConfiguration().toString();
    }
    
    @Override
    public void init(FMLPreInitializationEvent preInitEvent)
    {
        configMgr = new ConfigManager(preInitEvent.getSuggestedConfigurationFile());
    }

    @Override
    public void registerRenderers()
    {
    }
    
    @Override
    public void registerEvents()
    {
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    public void registerItems()
    {
    }

    @Override
    public void registerBlocks()
    {
    }

    @Override
    public void registerTileEntities()
    {
    }

    @Override
    public void registerEntities(ModBase mod)
    {
    }

    @Override
    public void registerRecipes()
    {
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equals(GiantTreeMod.MODID))
        {
            configMgr.refresh();
            configMgr.save();
        }
    }
}
