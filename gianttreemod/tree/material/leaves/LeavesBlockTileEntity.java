package xmilcode.gianttreemod.tree.material.leaves;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;


public class LeavesBlockTileEntity extends TileEntity implements LeavesBlockPersistentAttributes
{
    private static final String INTERNAL_NAME = "leavesTileEntity";

    private Coord3D treeCorePos = new Coord3D();
    private boolean canGrow = true;

    
    public static void register()
    {
        GameRegistry.registerTileEntity(LeavesBlockTileEntity.class, INTERNAL_NAME);
    }
    
    
    @Override
    public Coord3D treeCorePosition()
    {
        return treeCorePos;
    }

    
    @Override
    public void setTreeCorePosition(Coord3D pos)
    {
        treeCorePos = pos;
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
        treeCorePos = new Coord3D(
                storage.getInteger("treeCoreX"),
                storage.getInteger("treeCoreY"),
                storage.getInteger("treeCoreZ"));
        canGrow = storage.getBoolean("canGrow");
    }
    
    
    @Override
    public void writeToNBT(NBTTagCompound storage)
    {
        super.writeToNBT(storage);
        storage.setInteger("treeCoreX", treeCorePos.x);
        storage.setInteger("treeCoreY", treeCorePos.y);
        storage.setInteger("treeCoreZ", treeCorePos.z);
        storage.setBoolean("canGrow", canGrow);
    }
}
