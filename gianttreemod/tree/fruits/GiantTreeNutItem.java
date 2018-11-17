package xmilcode.gianttreemod.tree.fruits;

import cpw.mods.fml.common.registry.GameRegistry;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.mclib.food.FoodItemBase;


public class GiantTreeNutItem extends FoodItemBase
{
    private static final String NAME_ID = "giant_tree_nut";
    private static final String INTERNAL_NAME = "nutGiantTree";

    // Valid range [0, 20].
    private static final int FOOD_LEVEL = 2;
    // Valid range [0, 20/(FOOD_LEVEL*2)].
    private static final float SATURATION_MODIFIER = 1F;
    private static final boolean IS_WOLF_FOOD = false;
    
    
    public GiantTreeNutItem()
    {
        super(GiantTreeMod.MODID,
                NAME_ID,
                FOOD_LEVEL,
                SATURATION_MODIFIER,
                IS_WOLF_FOOD);
    }
    
    
    public static GiantTreeNutItem createAndRegister()
    {
        GiantTreeNutItem nut = new GiantTreeNutItem();
        GameRegistry.registerItem(nut, INTERNAL_NAME);
        return nut;
    }
}
