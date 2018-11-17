package xmilcode.mclib.plants;

import java.util.ArrayList;
import java.util.Random;

import xmilcode.mclib.util.LightUtil;
import xmilcode.mclib.util.MCConstants;
import xmilcode.mclib.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


// Represents an existing plant block at a given position in the
// world.
public class PlantBlockAtPos
{
    // Factory for plant block instances.
    public static class Factory implements IPlantBlockAtPosFactory
    {
	public PlantBlockAtPos create(World world, int x, int y, int z, PlantBlockBase plant)
	{
	    return new PlantBlockAtPos(world, x, y, z, plant);
	}
	
	
	public PlantBlockAtPos create(
		World world, int x, int y, int z, PlantBlockBase plant, int metadata)
	{
	    return new PlantBlockAtPos(world, x, y, z, plant, metadata);
	}
    }
    
    
    protected final World world;
    protected final int x;
    protected final int y;
    protected final int z;
    protected final PlantBlockBase plant;
    // Occasionally a block is not part of the world and its metadata
    // cannot be accessed through the world object. In this case the
    // metadata can be passed explicitly into the ctor and is cached in
    // a field.
    private final int cachedMetadata;
    private final boolean useCachedMetadata;
    
    
    public PlantBlockAtPos(World world, int x, int y, int z, PlantBlockBase plant, int metadata)
    {
	this.world = world;
	this.x = x;
	this.y = y;
	this.z = z;
	this.plant = plant;
	this.cachedMetadata = metadata;
	this.useCachedMetadata = true;
    }
    
    
    public PlantBlockAtPos(World world, int x, int y, int z, PlantBlockBase plant)
    {
	this.world = world;
	this.x = x;
	this.y = y;
	this.z = z;
	this.plant = plant;
	this.cachedMetadata = 0;
	this.useCachedMetadata = false;
    }
    
    
    public boolean canUseBonemealFertilizer()
    {
        return !isPlantFullyGrown();
    }
    
    
    public boolean willBonemealFertilizerSucceed(Random rand)
    {
        return true;
    }

    
    public void applyBonemealFertilizer()
    {
        growPlant();
    }
    
    
    public void updateTick(Random rand)
    {
        if (!hasBlockReachedLastStage() &&
            isExposedToDaylight() &&
            plant.shouldGrowNow(rand))
        {
            growBlock();
        }
    }
    
    
    protected boolean isPlantFullyGrown()
    {
        return hasBlockReachedLastStage();
    }
    
    
    protected void growPlant()
    {
        growBlock();
    }
    
    
    protected void growBlock()
    {
        setStageOfBlock(getNextStage());
    }
    
    
    protected int getMetadata()
    {
	if (useCachedMetadata)
	    return cachedMetadata;
	return world.getBlockMetadata(x, y, z);
    }
    
    
    protected int firstStage()
    {
	return 0;
    }
    
    
    protected int lastStage()
    {
	return plant.countGrowthStages() - 1;
    }
    

    protected int getStageOfBlock()
    {
	return MathUtil.clamp(getMetadata(), firstStage(), lastStage());
    }
    
    
    protected void setStageOfBlock(int stage)
    {
	world.setBlockMetadataWithNotify(x, y, z, stage, MCConstants.NOTIFY_CLIENT_FLAG);
    }


    protected int getNextStage()
    {
	return getStageAdvancedBy(1);
    }


    protected int getStageAdvancedBy(int advanceBy)
    {
	return MathUtil.clamp(
		getStageOfBlock() + advanceBy,
		firstStage(),
		lastStage());
    }
    
    
    protected boolean hasBlockReachedLastStage()
    {
	return (getStageOfBlock() == lastStage());
    }
    
    
    protected boolean isExposedToDaylight()
    {
	// Check block above for daylight.
	return LightUtil.isDaylight(world.getBlockLightValue(x, y + 1, z));
    }
    
    
    public ArrayList<ItemStack> getDrops(
	    Item fruit,
	    int minFruits,
	    int maxFruits,
	    Item seeds,
	    int fortune)
    {
	ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
	int stage = getStageOfBlock();
	
	if (stage == firstStage())
	{
	    // Player might get one seed. More with fortune.
	    drops.add(new ItemStack(
		    seeds,
		    world.rand.nextInt(2 + fortune),
		    0));
	}
	else
	{
	    // The number of dropped fruits is a random value between the
	    // passed min and max with the fortune level increasing the max.
	    // This number is adjusted by a 'stage ratio' to make sure
	    // earlier stages drop less fruits.
	    
	    int deltaFruits = maxFruits - minFruits;
	    if (deltaFruits < 0)
		deltaFruits = 0;
	    
	    int numFruitsDropped =
		    minFruits + world.rand.nextInt(1 + deltaFruits + fortune);
	    float stageRatio = 1.0F / (1 + lastStage() - stage);
	    
	    drops.add(new ItemStack(
		    fruit,
		    (int)(stageRatio * numFruitsDropped),
		    0));
	    
	    // Occasionally also drop a seed.
	    if (world.rand.nextInt(10) < 4)
		drops.add(new ItemStack(seeds, 1, 0));
	}
	
	return drops;
    }
}
