package xmilcode.gianttreemod.tree.growth;

import java.util.HashSet;
import java.util.Set;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


// Notifies all registered growing parts about growth events.
public class GrowthManager
{
    private Set<BlockCreator> parts = new HashSet<BlockCreator>();
    
    
    public void registerGrowingPart(BlockCreator part)
    {
        parts.add(part);
    }
    
    
    public void unregisterGrowingPart(BlockCreator part)
    {
        parts.remove(part);
    }
    
    
    public boolean createBlockAt(World world, Coord3D at, BlockCreator caller)
    {
        for (BlockCreator part : parts)
        {
            // Allow parts to only create their own blocks, otherwise we
            // will run into endless loops when those parts also call the
            // growth manager.
            // Optimization: Skip the caller itself.
            if (part != caller && part.createOwnBlockAt(world, at))
            {
                return true;
            }
        }
        
        return false;
    }
}
