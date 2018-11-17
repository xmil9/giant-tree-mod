package xmilcode.mclib;

import java.util.ArrayList;
import java.util.List;

import xmilcode.mclib.proxies.CommonProxy;
import xmilcode.mclib.proxies.NullProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;



// Base class a mods main class.
public abstract class ModBase
{
    protected List<ModModule> modules;
    // A default proxy that doesn't do anything.
    private static CommonProxy defaultProxy = new NullProxy();
    // Generator for MC-wide entity ids.
    // Note that entity ids only need to be unique within a mod but to create
    // a spawn egg for an entity a unique id needs to be found, so we might as
    // well be consistent and use the same id for both.
    public static final EntityIdGenerator entityIdGen = new EntityIdGenerator(); 
    

    protected ModBase()
    {
        this.modules = new ArrayList<ModModule>();
    }
    
    
    public abstract String modId();
    
    
    protected void handlePreInit(FMLPreInitializationEvent event)
    {
        populateModules();
        initModules(event);
        
        registerRenderers();
        registerEvents();
        registerItems();
        registerBlocks();
        registerTileEntities();
        registerEntities();
    }
    
    
    protected void handleInit(FMLInitializationEvent event)
    {
        registerRecipes();
    }
    
    
    protected void handlePostInit(FMLPostInitializationEvent event)
    {
    }
    
    
    protected void handleServerStarting(FMLServerStartingEvent event)
    {
    }
    
    
    protected void handleServerStarted(FMLServerStartedEvent event)
    {
    }
    
    
    protected void handleServerStopping(FMLServerStoppingEvent event)
    {
    }
    
    
    protected void handleServerStopped(FMLServerStoppedEvent event)
    {
    }
    
    
    private void initModules(FMLPreInitializationEvent event)
    {
        for (ModModule module : modules)
            module.init(event);
    }

    
    private void registerRenderers()
    {
        // Let the active proxy decide whether to call each module's
        // registerRenderers() method. The client proxy will call them,
        // the server proxy won't.
        for (ModModule module : modules)
            getSidedProxy().registerRenderers(module);
    }

    
    private void registerEvents()
    {
        for (ModModule module : modules)
            module.registerEvents();
    }
    
    
    private void registerItems()
    {
        for (ModModule module : modules)
            module.registerItems();
    }
    
    
    private void registerBlocks()
    {
        for (ModModule module : modules)
            module.registerBlocks();
    }
    
    
    private void registerTileEntities()
    {
        for (ModModule module : modules)
            module.registerTileEntities();
    }
    
    
    private void registerEntities()
    {
        for (ModModule module : modules)
            module.registerEntities(this);
    }
    
    
    private void registerRecipes()
    {
        for (ModModule module : modules)
            module.registerRecipes();
    }
    
    
    // Derived mod classes add their modules here.
    protected abstract void populateModules();

    
    // Derived mod classes provide their proxy (if they have one) by overriding
    // this call.
    protected CommonProxy getSidedProxy()
    {
        return defaultProxy;
    }
}
