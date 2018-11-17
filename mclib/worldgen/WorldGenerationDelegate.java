package xmilcode.mclib.worldgen;

import java.util.Random;

import xmilcode.mclib.util.MCConstants;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;


// Delegate that gets called during world generation.
// Allows mod to generate its own blocks.
public class WorldGenerationDelegate implements IWorldGenerator
{
    public void register(int priorityWeight)
    {
        GameRegistry.registerWorldGenerator(this, priorityWeight);
    }
    
    
    @Override
    public void generate(
            Random rand,
            int chunkX,
            int chunkZ,
            World world,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
        int chunkXInBlockCoords = chunkX * MCConstants.CHUNK_SIZE_X;
        int chunkZInBlockCoords = chunkZ * MCConstants.CHUNK_SIZE_Z;
        
        switch (world.provider.dimensionId)
        {
            case MCConstants.DIM_NETHER:
                generateNether(
                        world,
                        rand,
                        chunkXInBlockCoords,
                        chunkZInBlockCoords,
                        chunkGenerator,
                        chunkProvider);
                break;
                
            case MCConstants.DIM_SURFACE:
                generateSurface(
                        world,
                        rand,
                        chunkXInBlockCoords,
                        chunkZInBlockCoords,
                        chunkGenerator,
                        chunkProvider);
                break;
            case MCConstants.DIM_END:
                generateTheEnd(
                        world,
                        rand,
                        chunkXInBlockCoords,
                        chunkZInBlockCoords,
                        chunkGenerator,
                        chunkProvider);
                break;
        }
    }


    protected void generateNether(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
    }


    protected void generateSurface(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
    }
    
    
    protected void generateTheEnd(
            World world,
            Random rand,
            int chunkXInBlockCoords,
            int chunkZInBlockCoords,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider)
    {
    }
}
