package xmilcode.mclib.food;


public class FoodUtil
{
    // The range for food level of food items is [0, 20].
    public static final int MIN_FOOD_LEVEL = 0;
    public static final int MAX_FOOD_LEVEL = 20;
    
    // The range for saturation level of food items is [0, 20].
    // However, in code the saturation level of food items is not set
    // directly. Instead a saturation modifier is specified.
    public static final float MIN_SATURATION_LEVEL = 0.0F;
    public static final float MAX_SATURATION_LEVEL = (float)MAX_FOOD_LEVEL;
    
    // The saturation modifier is an attribute of food items that is used
    // to calculate the item's food saturation level.
    // Formula: 'sat level' = 'food level' * 'sat modifier' * 2
    // Therefore, the range of the saturation modifier of a given food item
    // is:
    //   [0.0, MAX_SATURATION_LEVEL / ('food level' * 2)]
    // This means that the max modifier value depends on the food level!
    public static final float MIN_SATURATION_MODIFIER = 0.0F;
    private static final float MAX_SATURATION_MODIFIER_FOR_ANY_FOOD_LEVEL =
	    MAX_SATURATION_LEVEL / 2.0F;

    
    public static float MaxSaturationModifierForFoodLevel(int foodLevel)
    {
	return CalculateModifierForSaturationLevel(MAX_SATURATION_LEVEL, foodLevel);
    }

    
    public static float CalculateModifierForSaturationLevel(
	    float saturationLevel,
	    int foodLevel)
    {
	assert IsValidSaturationLevel(saturationLevel): "Invalid saturation level.";
	assert IsValidFoodLevel(foodLevel): "Invalid food level.";

	// Prevent div by zero when food level is zero.
	// For a food level of zero the saturation level will always be zero
	// as well and, therefore, the modifier does not matter. Return the
	// max modifier value for any food level.
	if (foodLevel == 0)
	    return MAX_SATURATION_MODIFIER_FOR_ANY_FOOD_LEVEL;

	// Equation for food saturation restoration:
	//   'saturation level' = 'food level' * 'saturation modifier' * 2
	// Solved for the modifier:
	//   'saturation modifier' = 'saturation level' / ('food level' * 2)
	return saturationLevel / (foodLevel * 2.0F);
    }
    
    
    public static boolean IsValidFoodLevel(int foodLevel)
    {
	return (MIN_FOOD_LEVEL <= foodLevel && foodLevel <= MAX_FOOD_LEVEL);
    }
    
    
    public static boolean IsValidSaturationLevel(float saturationLevel)
    {
	return (MIN_SATURATION_LEVEL <= saturationLevel &&
		saturationLevel <= MAX_SATURATION_LEVEL);
    }
    
    
    public static boolean IsValidSaturationModifierForFoodLevel(
	    float saturationModifier,
	    int foodLevel)
    {
	assert IsValidFoodLevel(foodLevel): "Invalid food level value.";
	return (MIN_SATURATION_MODIFIER <= saturationModifier &&
		saturationModifier <= MaxSaturationModifierForFoodLevel(foodLevel));
    }
}
