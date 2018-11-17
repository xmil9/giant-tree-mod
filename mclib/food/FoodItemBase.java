package xmilcode.mclib.food;

import java.security.InvalidParameterException;

import xmilcode.mclib.util.NamingUtil;
import net.minecraft.item.ItemFood;


// Base class for food items that implements common
// functionality.
public class FoodItemBase extends ItemFood
{
    public FoodItemBase(
	    String modId,
	    String foodNameId,
	    int foodLevel,
	    float saturationModifier,
	    boolean isWolfFood)
    {
	super(foodLevel, saturationModifier, isWolfFood);

	if (!FoodUtil.IsValidFoodLevel(foodLevel) ||
	    !FoodUtil.IsValidSaturationModifierForFoodLevel(saturationModifier, foodLevel))
	{
	    throw new InvalidParameterException();
	}

	setUnlocalizedName(NamingUtil.generateUnlocalizedName(modId, foodNameId));
	setTextureName(NamingUtil.generateTextureName(modId, foodNameId));
    }
}
