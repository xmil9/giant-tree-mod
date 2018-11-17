package xmilcode.gianttreemod.tree.material.wood;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;


// Manages additional data about tree blocks.
public class WoodBlockTileEntity extends TileEntity implements WoodBlockPersistentAttributes
{
    private static final String INTERNAL_NAME = "woodTileEntity";

    private Coord3D treeCorePos = new Coord3D();

    
    public static void register()
    {
        GameRegistry.registerTileEntity(WoodBlockTileEntity.class, INTERNAL_NAME);
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
    public void readFromNBT(NBTTagCompound storage)
    {
        super.readFromNBT(storage);
        treeCorePos = new Coord3D(
                storage.getInteger("treeCoreX"),
                storage.getInteger("treeCoreY"),
                storage.getInteger("treeCoreZ"));
    }
    
    
    @Override
    public void writeToNBT(NBTTagCompound storage)
    {
        super.writeToNBT(storage);
        storage.setInteger("treeCoreX", treeCorePos.x);
        storage.setInteger("treeCoreY", treeCorePos.y);
        storage.setInteger("treeCoreZ", treeCorePos.z);
    }
}
