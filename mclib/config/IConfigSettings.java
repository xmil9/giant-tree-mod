package xmilcode.mclib.config;

import net.minecraftforge.common.config.Configuration;


// Interface that objects that hold config settings have to implement.
public interface IConfigSettings
{
    // Called when settings need to be refreshed.
    void refreshSettings(Configuration config);
}
