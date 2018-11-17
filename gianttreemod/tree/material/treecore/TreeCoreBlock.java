package xmilcode.gianttreemod.tree.material.treecore;

import java.util.ArrayList;
import java.util.Random;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.material.replacementstrategies.BlockReplacementStrategy;
import xmilcode.gianttreemod.tree.material.replacementstrategies.SoftMaterialsReplacementStrategy;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.plants.PlantLocationConditions;
import xmilcode.mclib.plants.WasPlantedObserver;
import xmilcode.mclib.util.NamingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;


public class TreeCoreBlock extends Block implements ITileEntityProvider, PlantLocationConditions, WasPlantedObserver
{
    private static final String NAME_ID = "giant_tree_core";
    private static final String INTERNAL_NAME = "coreGiantTree";
    
    public static final BlockReplacementStrategy replacementStrategy =
            new SoftMaterialsReplacementStrategy();

    
    public TreeCoreBlock()
    {
        super(Material.wood);

        setBlockName(NamingUtil.generateUnlocalizedName(GiantTreeMod.MODID, NAME_ID));
        setBlockTextureName(NamingUtil.generateTextureName(GiantTreeMod.MODID, NAME_ID));
        
        setCreativeTab((CreativeTabs)null);
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
        setTickRandomly(true);
    }


    public static TreeCoreBlock createAndRegister()
    {
        TreeCoreBlock block = new TreeCoreBlock();
        GameRegistry.registerBlock(block, INTERNAL_NAME);
        return block;
    }


    @Override // ITileEntityProvider
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TreeCoreBlockTileEntity();
    }
    
    
    // Code from BlockContainer.
    @Override // Block
    public boolean onBlockEventReceived(
            World world,
            int x,
            int y,
            int z,
            int p_149696_5_,
            int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        
        // Forward client events to tile entity.
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity != null ?
                tileEntity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }

    
    @Override // Block
    public void onPostBlockPlaced(
            World world,
            int x,
            int y,
            int z,
            int metadata)
    {
        super.onPostBlockPlaced(world, x, y, z, metadata);
        
        TreeCoreBlockAtPos blockAtPos =
                new TreeCoreBlockAtPos(world, new Coord3D(x, y, z), metadata);
        blockAtPos.onPostBlockPlaced();
    }
    
    
    @Override // Block
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);
        
        TreeCoreBlockAtPos blockAtPos =
                new TreeCoreBlockAtPos(world, new Coord3D(x, y, z));
        blockAtPos.updateTick(rand);
    }
    
    
    @Override // Block
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
        
        TreeCoreBlockAtPos blockAtPos =
                new TreeCoreBlockAtPos(world, new Coord3D(x, y, z), metadata);
        return blockAtPos.getDrops(fortune);
    }


    @Override
    public boolean canPlantSeedGrowOnBlock(Block block)
    {
        return canGeneratePlantOnBlock(block);
    }

    
    @Override
    public boolean canGeneratePlantOnBlock(Block block)
    {
        return (block == Blocks.farmland ||
                block == Blocks.dirt ||
                block == Blocks.grass ||
                block == Blocks.sand);
    }
    

    @Override
    public boolean canPlantGrowIntoBlockAt(World world, int x, int y, int z)
    {
        return replacementStrategy.canReplace(world, new Coord3D(x, y, z));
    }


    @Override
    public void onWasPlanted(World world, Coord3D at)
    {
        TreeCoreBlockAtPos blockAtPos = new TreeCoreBlockAtPos(world, at);
        blockAtPos.onWasPlanted();
    }
    
    
    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metaData)
    {
        super.onBlockDestroyedByPlayer(world, x, y, z, metaData);
        
        TreeCoreBlockAtPos blockAtPos =
                new TreeCoreBlockAtPos(world, new Coord3D(x, y, z));
        blockAtPos.onBlockDestroyedByPlayer();
    }
    
    
    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {
        super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
        
        TreeCoreBlockAtPos blockAtPos =
                new TreeCoreBlockAtPos(world, new Coord3D(x, y, z));
        blockAtPos.onBlockDestroyedByExplosion(explosion);
    }
}
