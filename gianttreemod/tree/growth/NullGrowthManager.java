package xmilcode.gianttreemod.tree.growth;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


public class NullGrowthManager extends GrowthManager
{
    @Override
    public void registerGrowingPart(BlockCreator growingPart)
    {
    }
    
    @Override
    public void unregisterGrowingPart(BlockCreator growingPart)
    {
    }
    
    @Override
    public boolean createBlockAt(World world, Coord3D at, BlockCreator caller)
    {
        return false;
    }
}
