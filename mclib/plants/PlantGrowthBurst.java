package xmilcode.mclib.plants;

import net.minecraft.block.IGrowable;
import net.minecraft.world.World;


// Allows to grow plants through their stages in bursts of a given
// number of stages.
public class PlantGrowthBurst
{
    private int stagesPerBurst;
    
    
    public PlantGrowthBurst(int numStagesPerBurst)
    {
        stagesPerBurst = numStagesPerBurst;
    }
    
    
    public void triggerBurst(World world, int x, int y, int z, IGrowable plant)
    {
        for (int i = 0; i < stagesPerBurst; ++i)
        {
            if (!applyBonemealToPlant(world, x, y, z, plant))
                break;
        }
    }


    private boolean applyBonemealToPlant(
            World world,
            int x,
            int y,
            int z,
            IGrowable plant)
    {
        // Check whether bonemeal can be applied and apply it if possible.
        // We are skipping a call to IGrowable.func_149852_a(...) that would
        // give the bonemeal a random chance to succeed because we want the
        // plant to grow as fast a possible.
        if (plant.func_149851_a(world, x, y, z, world.isRemote))
        {
            plant.func_149853_b(world, world.rand, x, y, z);
            return true;
        }
        
        return false;
    }
}