package xmilcode.gianttreemod.tree.material.wood;

import java.util.ArrayList;
import java.util.Random;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


// A wood block with a tile entity and that receives update ticks.
// This allows the block to exhibit growth behavior.
public abstract class ActiveWoodBlock extends BaseWoodBlock implements ITileEntityProvider
{
    protected ActiveWoodBlock()
    {
        super();
        setTickRandomly(true);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new WoodBlockTileEntity();
    }
    
    
    // Code from BlockContainer.
    @Override
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

    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);
        
        WoodBlockAtPos blockAtPos =
                new WoodBlockAtPos(world, new Coord3D(x, y, z), passiveWoodBlock());
        blockAtPos.updateTick(rand);
    }
    
    
    protected abstract BaseWoodBlock passiveWoodBlock(); 
}
