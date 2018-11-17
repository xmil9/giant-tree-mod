package xmilcode.mclib.loot;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public abstract class ChestLoot implements Loot
{
    @Override
    public void placeAt(World world, Coord3D pos)
    {
        createChestAt(world, pos);
        fillChestAt(world, pos);
    }
    

    private void createChestAt(World world, Coord3D pos)
    {
        world.setBlock(pos.x, pos.y, pos.z, Blocks.chest);
    }
    
    
    private void fillChestAt(World world, Coord3D pos)
    {
        TileEntityChest chestTe = (TileEntityChest)world.getTileEntity(pos.x, pos.y, pos.z);
        if (chestTe != null)
        {
            int numChestSlots = chestTe.getSizeInventory();
            List<ItemStack> itemsForSlots = getItemsForChestSlots(numChestSlots, world.rand);
            
            for (int slotIdx = 0;
                    slotIdx < Math.min(itemsForSlots.size(), numChestSlots);
                    ++slotIdx)
            {
                chestTe.setInventorySlotContents(slotIdx, itemsForSlots.get(slotIdx));
            }
        }
    }
    
    
    protected abstract List<ItemStack> getItemsForChestSlots(int maxSlots, Random rand);
}
