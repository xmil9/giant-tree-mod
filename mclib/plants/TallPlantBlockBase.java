package xmilcode.mclib.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


// Base class for plant blocks of plants that can be more than one block high.
// Uses a tile entity to store the height of each block of the plant.
public abstract class TallPlantBlockBase extends PlantBlockBase
                                         implements ITileEntityProvider
{
    public TallPlantBlockBase(
	    String modId,
	    String nameId,
	    IPlantBlockAtPosFactory blockAtPosFactory)
    {
	super(modId, nameId, blockAtPosFactory);
    }

    
    @Override // ITileEntityProvider
    public TileEntity createNewTileEntity(World world, int metadata)
    {
	return new TallPlantBlockTileEntity();
    }
    
    
    // Code from BlockContainer.
    @Override // Block
    public boolean onBlockEventReceived(
	    World world,
	    int x,
	    int y,
	    int z,
	    int p_149696_5_,
	    int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        
        // Forward client events to tile entity.
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity != null ?
        	tileEntity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }

    
    @Override // BlockBush
    protected boolean canPlaceBlockOn(Block block)
    {
	// Allow tall plants' blocks to be placed on top of each other.
        return (super.canPlaceBlockOn(block) ||
        	block == this);
    }
    
    
    // Returns how tall the plant is (in blocks).
    protected abstract int getPlantHeight();
    // Returns at which stage to sprout a new block.
    protected abstract int getSproutStage();
}
