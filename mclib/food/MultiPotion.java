package xmilcode.mclib.food;

import java.util.ArrayList;
import java.util.List;

import xmilcode.mclib.util.TimeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;


// Stores multiple potions and applies them to players
// according to the potions probabilities.
public class MultiPotion
{
    // Helper class to hold a potion's attributes.
    private class PotionSpec extends Object
    {
	// Minecraft id of potion.
	public int potionId;
	public int durationInSecs;
	// Potion strength. Usually 0 or 1.
	public int amplifier;
	// Chance of taking effect: [0, 1].
	public float probability;
	
	public PotionSpec(
		int potionId,
		int durationInSecs,
		int amplifier,
		float probability)
	{
	    this.potionId = potionId;
	    this.durationInSecs = durationInSecs;
	    this.amplifier = amplifier;
	    this.probability = probability;
	}
	
	@Override
	public boolean equals(Object obj)
	{
	    if (obj instanceof PotionSpec)
	    {
		PotionSpec cmp = (PotionSpec)obj;
		return (potionId == cmp.potionId &&
			durationInSecs == cmp.durationInSecs &&
			amplifier == cmp.amplifier &&
			probability == cmp.probability);
	    }
	    
	    return false;
	}
	
	@Override
	public int hashCode()
	{
	    int hash = 0;
	    hash = hash * 13 + potionId;
	    hash = hash * 17 + durationInSecs;
	    hash = hash * 23 + amplifier;
	    hash = hash * 37 + Float.floatToIntBits(probability);
	    return hash;
	}
    }
    
    
    // The potions.
    private List<PotionSpec> potions = new ArrayList<PotionSpec>();


    public MultiPotion()
    {
    }
    
    
    public void addPotion(
	    int potionId,
	    int durationInSecs,
	    int amplifier,
	    float probability)
    {
	potions.add(new PotionSpec(potionId, durationInSecs, amplifier, probability));
    }
    
    
    public void removePotion(
	    int potionId,
	    int duration,
	    int amplifier,
	    float probability)
    {
	PotionSpec targetPotion =
		new PotionSpec(potionId, duration, amplifier, probability);
	int targetIdx = -1;
	
	for (int i = 0; i < potions.size(); ++i)
	{
	    if (potions.get(i).equals(targetPotion))
	    {
		targetIdx = i;
		break;
	    }
	}
	
	if (targetIdx != -1)
	    potions.remove(targetIdx);
    }
    
    
    // Remove all potions with a given potion id.
    public void removePotion(int potionId)
    {
	while (removeNextPotionWithId(potionId))
	    ;
    }

    
    private boolean removeNextPotionWithId(int potionId)
    {
	int targetIdx = -1;
	
	for (int i = 0; i < potions.size(); ++i)
	{
	    if (potions.get(i).potionId == potionId)
	    {
		
		targetIdx = i;
		break;
	    }
	}
	
	if (targetIdx != -1)
	    potions.remove(targetIdx);
	return (targetIdx != -1);
    }
    
    
    public void clearPotions()
    {
	potions.clear();
    }

    
    public void applyToPlayer(
	    ItemStack foodStack,
	    World world,
	    EntityPlayer player)
    {
	if (world.isRemote)
	    return;
	
	for (PotionSpec spec : potions)
	    applyPotionToPlayer(spec, player, world.rand.nextFloat());
    }
    
    
    private void applyPotionToPlayer(
	    PotionSpec potionSpec,
	    EntityPlayer player,
	    float chance)
    {
        if (potionSpec.potionId > 0 && chance < potionSpec.probability)
        {
            player.addPotionEffect(
        	    new PotionEffect(
        		    potionSpec.potionId,
        		    TimeUtil.TicksFromSeconds(potionSpec.durationInSecs),
        		    potionSpec.amplifier));
        }
    }
}
