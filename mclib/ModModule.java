package xmilcode.mclib;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;


// Common interface for modules.
// Modules are (ideally) independent parts of a mod's functionality. 
public interface ModModule
{
    void init(FMLPreInitializationEvent preInitEvent);
    void registerRenderers();
    void registerEvents();
    void registerItems();
    void registerBlocks();
    void registerTileEntities();
    void registerEntities(ModBase mod);
    void registerRecipes();
}
