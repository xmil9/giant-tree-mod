package xmilcode.gianttreemod.tree.growth.growthstrategies;

import java.util.NoSuchElementException;
import java.util.Random;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowingBlock;
import xmilcode.gianttreemod.tree.growth.GrowthStrategy;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.RandomUtil;
import net.minecraft.block.Block;
import net.minecraft.world.World;


// Randomly selects neighbor blocks.
public class RandomGrowthStrategy implements GrowthStrategy
{
    private Coord3D sourcePos;
    private Random rand;
    private float probabilityX;
    private float probabilityY;
    private float probabilityZ;
    
    
    public RandomGrowthStrategy(
            Coord3D sourcePos,
            Random rand,
            float probabilityX,
            float probabilityY,
            float probabilityZ)
    {
        this.sourcePos = sourcePos;
        this.rand = rand;
        this.probabilityX = probabilityX;
        this.probabilityY = probabilityY;
        this.probabilityZ = probabilityZ;
    }

    
    @Override
    public boolean growAt(
            World world,
            Coord3D pos,
            BlockCreator growingPart,
            GrowingBlock block)
    {
        boolean hasGrown = false;
        
        final int GROWTH_SPEED = 5;
        for (int i = 0; i < GROWTH_SPEED; ++i)
        {
            if (growingPart.createAnyBlockAt(world, nextPosition()))
                hasGrown = true;
        }

        return hasGrown;
    }


    private Coord3D nextPosition()
    {
        final int EXPANSION_DISTANCE = 1;
        return new Coord3D(
                sourcePos.x + randomOffset(probabilityX, rand, EXPANSION_DISTANCE),
                // Prevent downward expansion.
                sourcePos.y + randomOffset(probabilityY, rand, Math.max(0, EXPANSION_DISTANCE)),
                sourcePos.z + randomOffset(probabilityZ, rand, EXPANSION_DISTANCE));
    }

    
    private static int randomOffset(float probability, Random rand, int offset)
    {
        // Any offset at all?
        if (!RandomUtil.sampleRandEvent(probability, rand))
            return 0;
        // Fifty-fifty chance for each direction.
        return RandomUtil.sampleRandEvent(0.5F, rand) ? offset : -offset;
    }
}
