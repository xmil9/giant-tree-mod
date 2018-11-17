package xmilcode.mclib.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


// Base class for food items that want to use multiple potion effects.
// A food item's default potion is not affected by this and can still
// be used as normal.
// If extending a food item with MultiPotionFood is not an option,
// then MultiPotion by itself can be composed into the food item directly.
public class MultiPotionFoodItem extends FoodItemBase
{
    private MultiPotion potions = new MultiPotion();
    
    
    public MultiPotionFoodItem(
	    String modId,
	    String foodNameId,
	    int foodLevel,
	    float saturationModifier,
	    boolean isWolfFood)
    {
	super(modId, foodNameId, foodLevel, saturationModifier, isWolfFood);
    }

    
    public void addPotion(
	    int potionId,
	    int durationInSecs,
	    int amplifier,
	    float probability)
    {
	potions.addPotion(potionId, durationInSecs, amplifier, probability);
    }
    
    
    public void removePotion(
	    int potionId,
	    int duration,
	    int amplifier,
	    float probability)
    {
	potions.removePotion(potionId, duration, amplifier, probability);
    }
    
    
    // Remove all potions with a given potion id.
    public void removePotion(int potionId)
    {
	potions.removePotion(potionId);
    }
    
    
    public void clearPotions()
    {
	potions.clearPotions();
    }
    
    
    @Override
    protected void onFoodEaten(ItemStack foodStack, World world, EntityPlayer player)
    {
	super.onFoodEaten(foodStack, world, player);
	potions.applyToPlayer(foodStack, world, player);
    }
}
