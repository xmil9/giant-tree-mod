package xmilcode.mclib.plants;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;


// Tile entity for tall plant blocks that stores the block's
// height information. 
public class TallPlantBlockTileEntity extends TileEntity
{
    private static final String INTERNAL_NAME = "tallPlantTileEntity";

    private int height;

    
    public TallPlantBlockTileEntity()
    {
	this.height = 0;
    }
    
    
    public static void register()
    {
	GameRegistry.registerTileEntity(TallPlantBlockTileEntity.class, INTERNAL_NAME);
    }
    
    
    public int getHeight()
    {
	return height;
    }
    
    
    public void setHeight(int height)
    {
	this.height = height;
	markDirty();
	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    
    @Override // TileEntity
    public void readFromNBT(NBTTagCompound storage)
    {
	super.readFromNBT(storage);
	height = storage.getInteger("height");
    }
    
    
    @Override // TileEntity
    public void writeToNBT(NBTTagCompound storage)
    {
	super.writeToNBT(storage);
	storage.setInteger("height", height);
    }
    
    
    @Override // TileEntity
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
	readFromNBT(packet.func_148857_g());
    }
    

    @Override // TileEntity
    public Packet getDescriptionPacket()
    {
	NBTTagCompound storage = new NBTTagCompound();
	writeToNBT(storage);
	return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, storage);
    }
}
