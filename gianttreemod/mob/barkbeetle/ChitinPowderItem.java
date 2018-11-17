package xmilcode.gianttreemod.mob.barkbeetle;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.mob.GtMobModule;
import xmilcode.mclib.util.NamingUtil;
import xmilcode.plentyofcropsmod.food.FoodModule;
import cpw.mods.fml.common.registry.GameRegistry;


public class ChitinPowderItem extends Item
{
    private static final String NAME_ID = "chitin_powder";
    private static final String INTERNAL_NAME = "powderChitin";
    
    
    public ChitinPowderItem()
    {
        setUnlocalizedName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
        
        setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    
    public static ChitinPowderItem createAndRegister()
    {
        ChitinPowderItem chitin = new ChitinPowderItem();
        GameRegistry.registerItem(chitin, INTERNAL_NAME);
        return chitin;
    }
}
