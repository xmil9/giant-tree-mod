package xmilcode.gianttreemod;

import xmilcode.gianttreemod.config.GtConfigModule;
import xmilcode.gianttreemod.mob.GtMobModule;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.mclib.ModBase;
import xmilcode.mclib.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;


@Mod(modid=GiantTreeMod.MODID,
    version=GiantTreeMod.VERSION,
    guiFactory="xmilcode.gianttreemod.GtModGuiFactory")
public class GiantTreeMod extends ModBase
{
    public static final String MODID = "xmilcode.gianttreemod";
    public static final String VERSION = "1.0";
    
    // In MC the sided proxy is a class with different types on the
    // client and server. The different types handle side-specific
    // tasks.
    // Forge creates the instance for each side based on the types
    // given by the annotation.
    @SidedProxy(clientSide = "xmilcode.gianttreemod.GiantTreeClientProxy",
            serverSide = "xmilcode.gianttreemod.GiantTreeServerProxy")
    public static CommonProxy sidedProxy;
    

    @Override
    public String modId()
    {
        return MODID;
    }
    
    
    // Read config, create and register items, blocks, etc.
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("preInit");
        handlePreInit(event);
    }
    
    
    // Register recipes, set up own data.
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("init");
        handleInit(event);
    }
    
    
    // Handle interaction with other mods.
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        System.out.println("postInit");
        handlePostInit(event);
    }
    
    
    // Register server commands.
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        System.out.println("serverStarting");
        handleServerStarting(event);
    }
    
    
    @EventHandler
    public void serverStarted(FMLServerStartedEvent event)
    {
        System.out.println("serverStarted");
        handleServerStarted(event);
    }
    
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event)
    {
        System.out.println("serverStopping");
        handleServerStopping(event);
    }
    
    
    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event)
    {
        System.out.println("serverStopped");
        handleServerStopped(event);
    }

    
    @Override 
    protected void populateModules()
    {
        modules.add(new GtConfigModule());
        modules.add(new GiantTreeModule());
        modules.add(new GtMobModule());
    }
    
    
    @Override
    protected CommonProxy getSidedProxy()
    {
        return sidedProxy;
    }
}
