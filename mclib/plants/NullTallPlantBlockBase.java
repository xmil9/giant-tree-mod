package xmilcode.mclib.plants;

import net.minecraft.item.Item;
import net.minecraft.world.World;



//Null object pattern for TallPlantBlockBase objects.
public class NullTallPlantBlockBase extends TallPlantBlockBase
{
    public NullTallPlantBlockBase()
    {
        super("", "", null);
    }
    
    
    @Override // TallPlantBlockBase
    protected int getPlantHeight()
    {
        return 1;
    }

    
    @Override // TallPlantBlockBase
    protected int getSproutStage()
    {
        return 100000;
    }

    
    @Override // PlantBlockBase
    public int countGrowthStages()
    {
        return 1;
    }

    
    @Override // PlantBlockBase
    protected GrowthSpeed getGrowthSpeed()
    {
        return GrowthSpeed.VERY_SLOW;
    }

    
    @Override // PlantBlockBase
    protected Item getPlantFruit()
    {
        return null;
    }

    
    @Override // PlantBlockBase
    protected Item getPlantSeeds()
    {
        return null;
    }

    
    @Override // PlantBlockBase
    protected int getMinFruits()
    {
        return 0;
    }

    
    @Override // PlantBlockBase
    protected int getMaxFruits()
    {
        return 0;
    }

    
    @Override // PlantBlockBase
    public boolean isWild()
    {
        return false;
    }

    
    @Override // PlantBlockBase
    public boolean canPlantGrowIntoBlockAt(World world, int x, int y, int z)
    {
        return false;
    }
}
