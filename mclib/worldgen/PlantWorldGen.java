package xmilcode.mclib.worldgen;

import java.util.Random;

import xmilcode.mclib.datastructures.RightOpenRange;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.plants.PlantLocationConditions;
import xmilcode.mclib.plants.WasPlantedObserver;
import xmilcode.mclib.util.MCConstants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


// Tries to generate a given plant a given number of times.
// Most tries will not succeed because the location is not
// suitable.
public class PlantWorldGen extends WorldGenerator
{
    private Block plant;
    private int numTries;
    private int maxStage;
    private RightOpenRange<Integer> xRange;
    private RightOpenRange<Integer> yRange;
    private RightOpenRange<Integer> zRange;
    
    
    public PlantWorldGen(
            Block plant,
            int maxStage,
            int numTries,
            RightOpenRange<Integer> xRange,
            RightOpenRange<Integer> yRange,
            RightOpenRange<Integer> zRange)
    {
        this.plant = plant;
        this.numTries = numTries;
        this.maxStage = maxStage;
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
    }
    
    
    public void generate(World world, Random rand)
    {
        for (int i = 0; i < numTries; ++i)
        {
            int x = xRange.min + rand.nextInt(xRange.max - xRange.min);
            int y = yRange.min + rand.nextInt(yRange.max - yRange.min);
            int z = zRange.min + rand.nextInt(zRange.max - zRange.min);
            
            generate(world, rand, x, y, z);
        }
    }
    
    
    @Override
    public boolean generate(
            World world,
            Random rand,
            int x,
            int y,
            int z)
    {
        if (plant.canPlaceBlockAt(world, x, y, z) &&
            canGeneratePlantOnBlock(world, x, y - 1, z))
        {
            world.setBlock(
                    x,
                    y,
                    z,
                    plant,
                    maxStage > 0 ? rand.nextInt(maxStage) : 0,
                    MCConstants.NOTIFY_CLIENT_FLAG);
            notifyTreeThatItWasPlanted(world, new Coord3D(x, y, z));
            return true;
        }
        
        return false;
    }

    
    private boolean canGeneratePlantOnBlock(World world, int x, int y, int z)
    {
        Block target = world.getBlock(x, y, z);
        
        PlantLocationConditions locationConditions = getPlantLocationConditions();
        if (locationConditions != null)
            return locationConditions.canGeneratePlantOnBlock(target);

        return (target == Blocks.grass); 
    }

    
    private PlantLocationConditions getPlantLocationConditions()
    {
        if (plant instanceof PlantLocationConditions)
            return (PlantLocationConditions)plant;
        return null;
    }
    
    
    private void notifyTreeThatItWasPlanted(World world, Coord3D at)
    {
        if (plant instanceof WasPlantedObserver)
        {
            WasPlantedObserver observer = (WasPlantedObserver)plant;
            observer.onWasPlanted(world, at);
        }
    }
}
