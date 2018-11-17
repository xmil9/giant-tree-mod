package xmilcode.mclib.plants;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.NamingUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;


public abstract class SeedsItemBase extends Item implements IPlantable
{
    public SeedsItemBase(String modId, String nameId)
    {
	super();
	
	setUnlocalizedName(NamingUtil.generateUnlocalizedName(modId, nameId));
	setTextureName(NamingUtil.generateTextureName(modId, nameId));
	setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
	return EnumPlantType.Crop;
    }
    
    
    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z)
    {
	return getSeedsPlant();
    }
    
    
    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
    {
	return 0;
    }
    
    
    @Override
    public boolean onItemUse(
	    ItemStack itemStack,
	    EntityPlayer player,
	    World world,
	    int x,
	    int y,
	    int z,
	    int side,
	    float xHit,
	    float yHit,
	    float zHit)
    {
	ForgeDirection direction = ForgeDirection.getOrientation(side);
	int plantY = y + 1;
	
	if (direction == ForgeDirection.UP &&
	    player.canPlayerEdit(x, y, z, side, itemStack) &&
	    canSeedGrowOnBlock(world, x, y, z, direction) &&
	    player.canPlayerEdit(x, plantY, z, side, itemStack) &&
	    canPlantGrowIntoBlock(world, x, plantY, z))
	{
	    // Grow plant on top of the clicked block.	
	    if (world.setBlock(x, plantY, z, getSeedsPlant()))
	    {
	        --itemStack.stackSize;
	        notifyPlantThatItWasPlanted(world, new Coord3D(x, plantY, z));
	        return true;
	    }
	}
		
	return false;
    }
    
    private boolean canSeedGrowOnBlock(
	    World world,
	    int x,
	    int y,
	    int z,
	    ForgeDirection direction)
    {
	Block ground = world.getBlock(x, y, z);
	
	if (getSeedsPlantLocationConditions() != null)
	    return getSeedsPlantLocationConditions().canPlantSeedGrowOnBlock(ground);
	else
	    return ground.canSustainPlant(world, x, y, z, direction, this);
    }
    
    
    private boolean canPlantGrowIntoBlock(World world, int x, int y, int z)
    {
        if (getSeedsPlantLocationConditions() != null)
            return getSeedsPlantLocationConditions().canPlantGrowIntoBlockAt(world, x, y, z);
        else
            return world.isAirBlock(x, y, z);
    }
    
    
    private PlantLocationConditions getSeedsPlantLocationConditions()
    {
        Block plant = getSeedsPlant();
        if (plant instanceof PlantLocationConditions)
            return (PlantLocationConditions)plant;
        return null;
    }
    
    
    private void notifyPlantThatItWasPlanted(World world, Coord3D at)
    {
        Block plantBlock = getSeedsPlant();
        if (plantBlock instanceof WasPlantedObserver)
        {
            WasPlantedObserver observer = (WasPlantedObserver)plantBlock;
            observer.onWasPlanted(world, at);
        }
    }
    
    
    // Returns the plant that grows from the seed.
    protected abstract Block getSeedsPlant();
}
