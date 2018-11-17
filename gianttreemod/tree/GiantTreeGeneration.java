package xmilcode.gianttreemod.tree;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import xmilcode.gianttreemod.tree.structure.TreeParameters;
import xmilcode.mclib.datastructures.RightOpenRange;
import xmilcode.mclib.util.MCConstants;
import xmilcode.mclib.util.RandomUtil;
import xmilcode.mclib.worldgen.PlantWorldGen;
import xmilcode.mclib.worldgen.WorldGenerationDelegate;


// Handles generation of giant trees in world.
public class GiantTreeGeneration extends WorldGenerationDelegate
{
    @Override
    protected void generateNether(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
    }


    @Override
    protected void generateSurface(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
        generateTrees(world, rand, chunkXInBlockCoords, chunkZInBlockCoords);
    }
    
    
    @Override
    protected void generateTheEnd(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
    }
    
    
    private void generateTrees(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords)
    {
        int numTriesPerChunk = calcNumberOfTreeGenerationAttemptsPerChunk(rand);
        
        if (numTriesPerChunk > 0)
        {
            RightOpenRange<Integer> xRange = new RightOpenRange<Integer>(
                    chunkXInBlockCoords,
                    chunkXInBlockCoords + MCConstants.CHUNK_SIZE_X);
            RightOpenRange<Integer> yRange = new RightOpenRange<Integer>(
                    TreeParameters.MIN_TREE_Y_POS,
                    TreeParameters.MAX_TREE_Y_POS);
            RightOpenRange<Integer> zRange = new RightOpenRange<Integer>(
                    chunkZInBlockCoords,
                    chunkZInBlockCoords + MCConstants.CHUNK_SIZE_Z);
            final int TREE_CORE_METADATA = 0;

            PlantWorldGen plantGen = new PlantWorldGen(
                    GiantTreeModule.giantTreeCore,
                    TREE_CORE_METADATA,
                    numTriesPerChunk,
                    xRange,
                    yRange,
                    zRange);
            plantGen.generate(world, rand);
        }
    }
    
    
    @SuppressWarnings("unused")
    private static int calcNumberOfTreeGenerationAttemptsPerChunk(Random rand)
    {
        if (TreeParameters.CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK >= 1)
        {
            return (int)TreeParameters.CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK;
        }
        else
        {
            return RandomUtil.sampleRandEvent(
                    TreeParameters.CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK, rand) ? 1 : 0;
        }
    }
}
