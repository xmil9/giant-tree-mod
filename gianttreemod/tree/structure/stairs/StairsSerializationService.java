package xmilcode.gianttreemod.tree.structure.stairs;

import xmilcode.mclib.INbtPersistent;
import net.minecraft.nbt.NBTTagCompound;


// Handles workflow of serializing stairs objects.
public class StairsSerializationService
{
    public static void serialize(Stairs stairs, NBTTagCompound storage)
    {
        serialize(stairs, storage, "");
    }
    
    
    public static void serialize(Stairs stairs, NBTTagCompound storage, String tagSuffix)
    {
        storage.setString("stairsType" + tagSuffix, stairs.typeId());
        
        if (stairs instanceof INbtPersistent)
        {
            INbtPersistent persistentStairs = (INbtPersistent)stairs;

            NBTTagCompound stairsStorage = new NBTTagCompound();
            persistentStairs.write(stairsStorage);
            storage.setTag("stairs" + tagSuffix, stairsStorage);
        }
    }
    
    
    public static Stairs deserialize(NBTTagCompound storage)
    {
        return deserialize(storage, "");
    }
    
    
    public static Stairs deserialize(NBTTagCompound storage, String tagSuffix)
    {
        Stairs stairs = StairsFactory.createStairsFromTypeId(
                storage.getString("stairsType" + tagSuffix));

        if (stairs instanceof INbtPersistent)
        {
            INbtPersistent persistentStairs = (INbtPersistent)stairs;
            persistentStairs.read(storage.getCompoundTag("stairs" + tagSuffix));
        }
        
        return stairs;
    }
}
