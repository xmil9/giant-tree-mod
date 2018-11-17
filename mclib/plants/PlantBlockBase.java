package xmilcode.mclib.plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xmilcode.mclib.util.LightUtil;
import xmilcode.mclib.util.MathUtil;
import xmilcode.mclib.util.NamingUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


// Base class for plant blocks.
public abstract class PlantBlockBase extends BlockBush implements IGrowable, PlantLocationConditions
{
    // Factory to create plant blocks for given coordinates.
    protected IPlantBlockAtPosFactory blockAtPosFactory;
    
    @SideOnly(Side.CLIENT)
    private IIcon[] stageIcons;
    
    
    public PlantBlockBase(
	    String modId,
	    String nameId,
	    IPlantBlockAtPosFactory blockAtPosFactory)
    {
	super(Material.plants);
	
	this.blockAtPosFactory = blockAtPosFactory;
	
	setBlockName(NamingUtil.generateUnlocalizedName(modId, nameId));
	setBlockTextureName(NamingUtil.generateTextureName(modId, nameId));
	
	// By default plants don't appear in the creative menu.
	setCreativeTab((CreativeTabs)null);
	// Breaks easily.
	setHardness(0.0F);
	setStepSound(soundTypeGrass);
    }
    
    
    public boolean shouldGrowNow(Random rand)
    {
	return getGrowthSpeed().sample(rand);
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegistry)
    {
	stageIcons = new IIcon[countGrowthStages()];
	
        for (int i = 0; i < countGrowthStages(); ++i)
        {
            stageIcons[i] = iconRegistry.registerIcon(
        	    NamingUtil.generateStagedTextureName(getTextureName(), i));
        }
    }
    

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
	if (stageIcons == null)
	    throw new IllegalStateException();
	
        if (metadata < 0 || metadata > lastStage())
            metadata = lastStage();
        return stageIcons[metadata];
    }
    
    
    @Override
    public int getRenderType()
    {
	// For rendering bushes.
	// See net.minecraft.renderer.RenderBlocks.renderBlockByRenderType(...).
	return 1;
	// For rendering crops.
	//return 6;
    }


    // Can bonemeal be used to speed up growth of a given
    // plant instance?
    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean isRemote)
    {
	PlantBlockAtPos blockAtPos = blockAtPosFactory.create(world, x, y, z, this);
	return blockAtPos.canUseBonemealFertilizer();
    }


    // Returns whether applying bonemeal to a given plant instance
    // succeeds (possibly based on chance).
    // Called after func_149851_a(...) already succeeded.
    @Override
    public boolean func_149852_a(World world, Random rand, int x, int y, int z)
    {
	PlantBlockAtPos blockAtPos = blockAtPosFactory.create(world, x, y, z, this);
	return blockAtPos.willBonemealFertilizerSucceed(rand);
    }


    // Apply bonemeal to a given plant instance.
    // Called if both func_149851_a(...) and func_149852_a(...) succeed.
    @Override
    public void func_149853_b(World world, Random rand, int x, int y, int z)
    {
	PlantBlockAtPos blockAtPos = blockAtPosFactory.create(world, x, y, z, this);
	blockAtPos.applyBonemealFertilizer();
    }

    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
	super.updateTick(world, x, y, z, rand);
	
	PlantBlockAtPos blockAtPos = blockAtPosFactory.create(world, x, y, z, this);
	blockAtPos.updateTick(rand);
    }
    
    
    @Override
    public ArrayList<ItemStack> getDrops(
	    World world,
	    int x,
	    int y,
	    int z,
	    int metadata,
	    int fortune)
    {
	// At this point the plant's block has already been removed from
	// the world. Therefore, we have to pass the metadata to the
	// plant instance explicitly.
	
	PlantBlockAtPos blockAtPos = blockAtPosFactory.create(world, x, y, z, this, metadata);
	return blockAtPos.getDrops(
		getPlantFruit(),
		getMinFruits(),
		getMaxFruits(),
		getPlantSeeds(),
		fortune);
    }


    // MC function to check if the plant can grow on the given
    // block from a seed.
    @Override
    protected boolean canPlaceBlockOn(Block block)
    {
        return (block == Blocks.farmland ||
                block == Blocks.dirt ||
                block == Blocks.grass);
    }

    
    @Override
    public boolean canPlantSeedGrowOnBlock(Block block)
    {
        return (block == Blocks.farmland);
    }
    
    
    @Override
    public boolean canGeneratePlantOnBlock(Block block)
    {
        return canPlaceBlockOn(block);
    }
    
    
    @Override
    public boolean canPlantGrowIntoBlockAt(World world, int x, int y, int z)
    {
        return world.isAirBlock(x, y, z);
    }
    
    
    private int firstStage()
    {
        return 0;
    }
    
    
    private int lastStage()
    {
        return countGrowthStages() - 1;
    }

    
    // Returns the number of stages the plant grows through.
    public abstract int countGrowthStages();
    // Returns the speed that a plant grows with.
    protected abstract GrowthSpeed getGrowthSpeed();
    // Returns the item that the plant yields.
    protected abstract Item getPlantFruit();
    // Returns the seeds that grow the plant.
    protected abstract Item getPlantSeeds();
    // Return the min and max number of fruits dropped from a
    // fully grown plant.
    protected abstract int getMinFruits();
    protected abstract int getMaxFruits();
    // Does plant occur in the wild?
    // Influences if the plant can be generated in the world.
    public abstract boolean isWild();
}
