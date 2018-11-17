package xmilcode.mclib.plants;

import net.minecraft.block.Block;
import net.minecraft.world.World;


// Called by SeedsItemBase to check if the plant that a seed grows can
// grow at a given location.
public interface PlantLocationConditions
{
    // Called when planting seeds to check if they can grow on a given block.
    public boolean canPlantSeedGrowOnBlock(Block block);
    // Called when generating a plant to check if it can grow on a given block.
    public boolean canGeneratePlantOnBlock(Block block);
    // Called to check if a plant can occupy a given block.
    public boolean canPlantGrowIntoBlockAt(World world, int x, int y, int z);
}
