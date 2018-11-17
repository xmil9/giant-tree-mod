package xmilcode.gianttreemod.tree.material.treecore;

import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.loot.TreeLootFactory;
import xmilcode.gianttreemod.tree.structure.SerializedTreeBuilder;
import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.gianttreemod.tree.structure.TreeFactory;
import xmilcode.gianttreemod.tree.structure.TreeSerializationService;
import xmilcode.mclib.loot.SingleUseLootDecorator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class TreeCoreBlockTileEntity extends TileEntity implements TreeCoreBlockPersistentAttributes
{
    private static final String INTERNAL_NAME = "giantTreeCoreTileEntity";

    private boolean canGrow = true;
    private Tree tree = TreeFactory.createNullTree();
    private boolean needToRegisterTree = false;

    
    public static void register()
    {
        GameRegistry.registerTileEntity(TreeCoreBlockTileEntity.class, INTERNAL_NAME);
    }
    
    
    @Override
    public Tree tree()
    {
        return tree;
    }
    
    
    @Override
    public void setTree(Tree tree)
    {
        this.tree = tree;
        markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    
    @Override
    public boolean canGrow()
    {
        return canGrow;
    }
    
    
    @Override
    public void markAsDoneGrowing()
    {
        canGrow = false;
        markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    
    @Override
    public void readFromNBT(NBTTagCompound storage)
    {
        super.readFromNBT(storage);
        canGrow = storage.getBoolean("canGrow");
        
        SerializedTreeBuilder treeBuilder = new SerializedTreeBuilder();
        treeBuilder.setStorage(storage);
        treeBuilder.setGrowthManager(new GrowthManager());
        treeBuilder.setLoot(TreeLootFactory.createTreeLoot());
        tree = treeBuilder.build();
        
        needToRegisterTree = true;
    }
    
    
    @Override
    public void writeToNBT(NBTTagCompound storage)
    {
        super.writeToNBT(storage);
        storage.setBoolean("canGrow", canGrow);
        
        TreeSerializationService.serialize(tree, storage);
    }
    
    
    @Override
    public void setWorldObj(World world)
    {
        super.setWorldObj(world);
        
        if (needToRegisterTree)
        {
            GiantTreeModule.treeRegistry.registerTree(world, tree);
            needToRegisterTree = false;
        }
    }
}
