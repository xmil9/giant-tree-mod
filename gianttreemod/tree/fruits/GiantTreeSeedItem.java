package xmilcode.gianttreemod.tree.fruits;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.mclib.plants.SeedsItemBase;



public class GiantTreeSeedItem extends SeedsItemBase
{
    private static final String NAME_ID = "giant_tree_seed";
    private static final String INTERNAL_NAME = "seedGiantTree";

    
    public GiantTreeSeedItem()
    {
        super(GiantTreeMod.MODID, NAME_ID);
    }
    
    
    public static GiantTreeSeedItem createAndRegister()
    {
        GiantTreeSeedItem seeds = new GiantTreeSeedItem();
        GameRegistry.registerItem(seeds, INTERNAL_NAME);
        return seeds;
    }
    
    
    @Override
    protected Block getSeedsPlant()
    {
        return GiantTreeModule.giantTreeCore;
    }
}
