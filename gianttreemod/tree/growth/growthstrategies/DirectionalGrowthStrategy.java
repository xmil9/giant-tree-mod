package xmilcode.gianttreemod.tree.growth.growthstrategies;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowingBlock;
import xmilcode.gianttreemod.tree.growth.GrowthStrategy;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


public class DirectionalGrowthStrategy implements GrowthStrategy
{
    private DirectionalExpansionStrategy expansionStrategy;

    
    public DirectionalGrowthStrategy(
            DirectionalExpansionStrategy expansionStrategy)
    {
        this.expansionStrategy = expansionStrategy; 
    }

    
    @Override
    public boolean growAt(
            World world,
            Coord3D pos,
            BlockCreator growingPart,
            GrowingBlock block)
    {
        boolean hasGrown = false;
        
        if (block.canGrow())
        {
            expansionStrategy.reset(pos);
            
            while (expansionStrategy.hasNext())
            {
                if (growingPart.createAnyBlockAt(world, expansionStrategy.next()))
                    hasGrown = true;
            }
            
            block.markAsDoneGrowing();
        }

        return hasGrown;
    }
}
