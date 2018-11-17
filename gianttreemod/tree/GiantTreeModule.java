package xmilcode.gianttreemod.tree;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import xmilcode.gianttreemod.config.GtConfigModule;
import xmilcode.gianttreemod.tree.fruits.GiantTreeNutItem;
import xmilcode.gianttreemod.tree.fruits.GiantTreeSeedItem;
import xmilcode.gianttreemod.tree.loot.TreeLootSettings;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockTileEntity;
import xmilcode.gianttreemod.tree.material.leaves.LeavesSettings;
import xmilcode.gianttreemod.tree.material.leaves.simple.ActiveSimpleLeavesBlock;
import xmilcode.gianttreemod.tree.material.leaves.simple.PassiveSimpleLeavesBlock;
import xmilcode.gianttreemod.tree.material.leaves.standard.ActiveStdLeavesBlock;
import xmilcode.gianttreemod.tree.material.leaves.standard.PassiveStdLeavesBlock;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlock;
import xmilcode.gianttreemod.tree.material.treecore.TreeCoreBlockTileEntity;
import xmilcode.gianttreemod.tree.material.wood.WoodBlockTileEntity;
import xmilcode.gianttreemod.tree.material.wood.branch.ActiveBranchWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.branch.PassiveBranchWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.stairs.ActiveStairsWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.stairs.PassiveStairsWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.trunk.ActiveTrunkWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.trunk.PassiveTrunkWoodBlock;
import xmilcode.mclib.GameSettingsTracker;
import xmilcode.mclib.ModModule;
import xmilcode.mclib.ModBase;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;


public class GiantTreeModule implements ModModule
{
    public static TreeCoreBlock giantTreeCore;
    private static ActiveStdLeavesBlock activeStdLeaves;
    private static PassiveStdLeavesBlock passiveStdLeaves;
    private static ActiveSimpleLeavesBlock activeSimpleLeaves;
    private static PassiveSimpleLeavesBlock passiveSimpleLeaves;
    public static ActiveTrunkWoodBlock activeTrunkWood;
    public static PassiveTrunkWoodBlock passiveTrunkWood;
    public static ActiveBranchWoodBlock activeBranchWood;
    public static PassiveBranchWoodBlock passiveBranchWood;
    public static ActiveStairsWoodBlock activeStairsWood;
    public static PassiveStairsWoodBlock passiveStairsWood;
    public static GiantTreeSeedItem giantTreeSeed;
    public static GiantTreeNutItem giantTreeNut;

    public static GiantTreeRegistry treeRegistry = new GiantTreeRegistry();
    
    // Delegate for world generation events.
    private GiantTreeGeneration treeGeneration = new GiantTreeGeneration();
    // Detects changes to MC game settings that we are interested in.
    private static GameSettingsTracker gameSettingsTracker;

    // Configurable settings.
    public static TreeLootSettings lootSettings = new TreeLootSettings();
    private static LeavesSettings leavesSettings = new LeavesSettings();

    
    public static Block activeLeavesBlock()
    {
        switch (leavesSettings.leavesType())
        {
            case STANDARD_LEAVES:
                return activeStdLeaves;
            
            case SIMPLE_BLOCK_LEAVES:
            default:
                return activeSimpleLeaves;
        }
    }
    

    public static Block passiveLeavesBlock()
    {
        switch (leavesSettings.leavesType())
        {
            case STANDARD_LEAVES:
                return passiveStdLeaves;
            
            case SIMPLE_BLOCK_LEAVES:
            default:
                return passiveSimpleLeaves;
        }
    }

    
    @Override
    public void init(FMLPreInitializationEvent preInitEvent)
    {
        gameSettingsTracker = new GameSettingsTracker();
    }


    @Override
    public void registerRenderers()
    {
    }

    
    @Override
    public void registerEvents()
    {
        // Register config settings.
        GtConfigModule.configManager().registerSettingsHolder(lootSettings);
        GtConfigModule.configManager().registerSettingsHolder(leavesSettings);

        // Priority for generation order. The lower the earlier. 
        final int GEN_WEIGHT = 0;
        treeGeneration.register(GEN_WEIGHT);
        
        // Register as handler for events on FML bus.
        FMLCommonHandler.instance().bus().register(this);
    }

    
    @Override
    public void registerItems()
    {
        giantTreeSeed = GiantTreeSeedItem.createAndRegister();
        giantTreeNut = GiantTreeNutItem.createAndRegister();
    }

    
    @Override
    public void registerBlocks()
    {
        giantTreeCore = TreeCoreBlock.createAndRegister();
        activeStdLeaves = ActiveStdLeavesBlock.createAndRegister();
        passiveStdLeaves = PassiveStdLeavesBlock.createAndRegister();
        activeSimpleLeaves = ActiveSimpleLeavesBlock.createAndRegister();
        passiveSimpleLeaves = PassiveSimpleLeavesBlock.createAndRegister();
        activeTrunkWood = ActiveTrunkWoodBlock.createAndRegister();
        passiveTrunkWood = PassiveTrunkWoodBlock.createAndRegister();
        activeBranchWood = ActiveBranchWoodBlock.createAndRegister();
        passiveBranchWood = PassiveBranchWoodBlock.createAndRegister();
        activeStairsWood = ActiveStairsWoodBlock.createAndRegister();
        passiveStairsWood = PassiveStairsWoodBlock.createAndRegister();

        gameSettingsTracker.registerObserver(activeStdLeaves);
        gameSettingsTracker.registerObserver(passiveStdLeaves);
    }

    
    @Override
    public void registerTileEntities()
    {
        TreeCoreBlockTileEntity.register();
        LeavesBlockTileEntity.register();
        WoodBlockTileEntity.register();
    }

    
    @Override
    public void registerEntities(ModBase mod)
    {
    }

    
    @Override
    public void registerRecipes()
    {
    }


    // Event handlers.
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        gameSettingsTracker.track();
    }
}
