package xmilcode.mclib.plants;

import net.minecraft.world.World;


// Factory from which to instantiate plant blocks at given positions.
public interface IPlantBlockAtPosFactory
{
    // Instantiates block from given location in world.
    PlantBlockAtPos create(World world, int x, int y, int z, PlantBlockBase plant);
    // Instantiates block from given location in world. Passes the metadata
    // explicitly.
    PlantBlockAtPos create(
	    World world, int x, int y, int z, PlantBlockBase plant, int metadata);
}
