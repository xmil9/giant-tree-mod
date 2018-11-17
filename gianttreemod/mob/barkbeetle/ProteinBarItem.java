package xmilcode.gianttreemod.mob.barkbeetle;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.mob.GtMobModule;
import xmilcode.mclib.food.FoodItemBase;


public class ProteinBarItem extends FoodItemBase
{
    private static final String NAME_ID = "protein_bar";
    private static final String INTERNAL_NAME = "barProtein";

    // Valid range [0, 20].
    private static final int FOOD_LEVEL = 6;
    // Valid range [0, 20/(FOOD_LEVEL*2)].
    private static final float SATURATION_MODIFIER = 0.8F;
    private static final boolean IS_WOLF_FOOD = false;
    
    
    public ProteinBarItem()
    {
        super(GiantTreeMod.MODID,
                NAME_ID,
                FOOD_LEVEL,
                SATURATION_MODIFIER,
                IS_WOLF_FOOD);

        setAlwaysEdible();
        setPotionEffect(Potion.damageBoost.id, 100, 1, 1.0F);
    }
    
    
    public static ProteinBarItem createAndRegister()
    {
        ProteinBarItem bar = new ProteinBarItem();
        GameRegistry.registerItem(bar, INTERNAL_NAME);
        return bar;
    }

    
    public static void registerRecipes()
    {
        GameRegistry.addRecipe(
                new ItemStack(GtMobModule.proteinBar),
                new Object[] {
                    "CCC", "WAW", "CCC",
                    'C', GtMobModule.chitinPowder,
                    'W', Items.wheat,
                    'A', Items.apple});
    }
}
