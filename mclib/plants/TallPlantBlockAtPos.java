package xmilcode.mclib.plants;

import java.util.ArrayList;
import java.util.Random;

import scala.xml.persistent.SetStorage;
import xmilcode.mclib.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


// Represents an existing plant block of a tall plant at a given position
// in the world.
public class TallPlantBlockAtPos extends PlantBlockAtPos
{
    // Factory for tall plant block instances.
    public static class Factory implements IPlantBlockAtPosFactory
    {
	public PlantBlockAtPos create(
		World world, int x, int y, int z, PlantBlockBase plant)
	{
	    return new TallPlantBlockAtPos(world, x, y, z, plant);
	}
	
	
	public PlantBlockAtPos create(
		World world, int x, int y, int z, PlantBlockBase plant, int metadata)
	{
	    return new TallPlantBlockAtPos(world, x, y, z, plant, metadata);
	}
    }

    
    public TallPlantBlockAtPos(
	    World world, int x, int y, int z, PlantBlockBase plant, int metadata)
    {
	super(world, x, y, z, plant, metadata);
    }


    public TallPlantBlockAtPos(World world, int x, int y, int z, PlantBlockBase plant)
    {
	super(world, x, y, z, plant);
    }

    
    @Override // PlantBlockAtPos
    public void applyBonemealFertilizer()
    {
        growPlant();
    }
    
    
    @Override // PlantBlockAtPos
    public void updateTick(Random rand)
    {
        if ((!hasBlockReachedLastStage() || !isBlockAtMaxHeight()) &&
            isExposedToDaylight() &&
            plant.shouldGrowNow(rand))
        {
            growPlant();
        }
    }
    
    
    @Override // PlantBlockAtPos
    protected boolean isPlantFullyGrown()
    {
        TallPlantBlockAtPos topBlock = getTopBlock();
        return (topBlock.isBlockAtMaxHeight() &&
                topBlock.getStageOfBlock() == sproutStage());
    }
    
    
    @Override // PlantBlockAtPos
    protected void growPlant()
    {
        TallPlantBlockAtPos topBlock = getTopBlock();
        topBlock.growBlock();
    }

    
    @Override // PlantBlockAtPos
    protected void growBlock()
    {
        // Check if we need to sprout a new block.
        if (!isBlockAtMaxHeight() &&
            getStageOfBlock() >= sproutStage() &&
            getTallPant().canPlantGrowIntoBlockAt(world, x, y + 1, z))
        {
            world.setBlock(x, y + 1, z, plant);
            
            TallPlantBlockAtPos sproutedBlock = new TallPlantBlockAtPos(world, x, y + 1, z, plant);
            sproutedBlock.setHeightOfBlock(getNextHeight());
            sproutedBlock.setStageOfBlock(firstStage());
        }
        
        // Check if we need to advance this block's growth stage.
        // This needs to be done after doing the sprout check to
        // not advance this block's stage past the sprout stage
        // without sprouting.
        // Note that the condition also needs to work for the special
        // case where the sprout stage is the same as the last stage.
        if (!hasBlockReachedLastStage())
        {
            if (getStageOfBlock() < sproutStage() ||
                (getStageOfBlock() >= sproutStage() && !isBlockAtMaxHeight()))
            {
                int nextStage = getNextStage();
                setStageOfBlock(nextStage);
            }
        }
    }

    
    protected boolean isTopBlockOfPlant()
    {
        return (world.getBlock(x, y + 1, z) != plant);
    }
    
    
    private TallPlantBlockAtPos getTopBlock()
    {
        int topPos = y;
        while (world.getBlock(x, topPos + 1, z) == plant)
            ++topPos;
        return new TallPlantBlockAtPos(world, x, topPos, z, plant);
    }
    
    
    protected TallPlantBlockBase getTallPant()
    {
	if (plant instanceof TallPlantBlockBase)
	{
	    return (TallPlantBlockBase)plant;
	}
	else
	{
            System.out.println(
                    "Warning: Tall plant at (" + x + "," + y + "," + z + ") has no TallPlantBlockBase object!");
            return new NullTallPlantBlockBase();
	}
    }    
    
    protected TallPlantBlockTileEntity getTallPlantTileEntity()
    {
	TileEntity te = world.getTileEntity(x, y, z);
	if (te != null && te instanceof TallPlantBlockTileEntity)
	{
	    return (TallPlantBlockTileEntity)te;
	}
	else
	{
	    System.out.println(
	            "Warning: Tall plant at (" + x + "," + y + "," + z + ") has no tile entity!");
	    return new NullTallPlantBlockTileEntity();
	}
    }
    
    
    private int sproutStage()
    {
	return getTallPant().getSproutStage();
    }
    
    
    private int minHeight()
    {
	return 0;
    }
    
    
    private int maxHeight()
    {
	return getTallPant().getPlantHeight() - 1;
    }
    

    public int getHeightOfBlock()
    {
	TallPlantBlockTileEntity tallPlantEntity = getTallPlantTileEntity(); 
	return MathUtil.clamp(
		tallPlantEntity.getHeight(),
		minHeight(),
		maxHeight());
    }
    
    
    protected void setHeightOfBlock(int height)
    {
	TallPlantBlockTileEntity tallPlantEntity = getTallPlantTileEntity();
	tallPlantEntity.setHeight(height);
    }


    public int getNextHeight()
    {
	return getHeightAdvancedBy(1);
    }


    public int getHeightAdvancedBy(int advanceBy)
    {
	return MathUtil.clamp(
		getHeightOfBlock() + advanceBy,
		minHeight(),
		maxHeight());
    }
    
    
    protected boolean isBlockAtMaxHeight()
    {
	return (getHeightOfBlock() == maxHeight());
    }
}
