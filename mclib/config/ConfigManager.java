package xmilcode.mclib.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraftforge.common.config.Configuration;


// Manages persistence and modification of the configurable mod settings. 
public class ConfigManager
{
    private Configuration config;
    private List<IConfigSettings> settingsHolders = new ArrayList<IConfigSettings>();
    
    
    public ConfigManager(File configFile)
    {
        config = new Configuration(configFile);
    }
    
    public Configuration getConfiguration()
    {
        return config;
    }
    
    public void registerSettingsHolder(IConfigSettings holder)
    {
        settingsHolders.add(holder);
        holder.refreshSettings(config);
    }
    
    // Calls registered settings holders to refresh their settings.
    public void refresh()
    {
        for (IConfigSettings holder : settingsHolders)
            holder.refreshSettings(config);
    }
    
    // Serializes the Configuration object to disk.
    public void save()
    {
        if (config.hasChanged())
            config.save();
    }
}
