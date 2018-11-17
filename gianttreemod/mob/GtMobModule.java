package xmilcode.gianttreemod.mob;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import xmilcode.gianttreemod.config.GtConfigModule;
import xmilcode.gianttreemod.mob.barkbeetle.BarkBeetleEntity;
import xmilcode.gianttreemod.mob.barkbeetle.BarkBeetleRenderer;
import xmilcode.gianttreemod.mob.barkbeetle.ChitinPowderItem;
import xmilcode.gianttreemod.mob.barkbeetle.ProteinBarItem;
import xmilcode.gianttreemod.mob.stickman.StickManEntity;
import xmilcode.gianttreemod.mob.stickman.StickManRenderer;
import xmilcode.mclib.ModModule;
import xmilcode.mclib.ModBase;


public class GtMobModule implements ModModule
{
    public static ChitinPowderItem chitinPowder;
    public static ProteinBarItem proteinBar;
    
    
    // Configurable settings.
    public static GtMobSettings mobSettings = new GtMobSettings();

    
    @Override
    public void init(FMLPreInitializationEvent preInitEvent)
    {
    }

    @Override
    public void registerRenderers()
    {
        StickManRenderer.register();
        BarkBeetleRenderer.register();
    }

    @Override
    public void registerEvents()
    {
        // Register config settings.
        GtConfigModule.configManager().registerSettingsHolder(mobSettings);
    }

    @Override
    public void registerItems()
    {
        chitinPowder = ChitinPowderItem.createAndRegister();
        proteinBar = ProteinBarItem.createAndRegister();
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
        StickManEntity.register(mod);
        BarkBeetleEntity.register(mod);
    }

    @Override
    public void registerRecipes()
    {
        ProteinBarItem.registerRecipes();
    }
}
