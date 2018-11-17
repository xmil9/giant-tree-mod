package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.mclib.INbtPersistent;
import net.minecraft.nbt.NBTTagCompound;


// Handles workflow of serializing leaves objects.
public class LeavesSerializationService
{
    public static void serialize(Leaves leaves, NBTTagCompound storage)
    {
        serialize(leaves, storage, "");
    }
    
    
    public static void serialize(Leaves leaves, NBTTagCompound storage, String tagSuffix)
    {
        storage.setString("leavesType" + tagSuffix, leaves.typeId());
        
        if (leaves instanceof INbtPersistent)
        {
            INbtPersistent persistentLeaves = (INbtPersistent)leaves;

            NBTTagCompound leavesStorage = new NBTTagCompound();
            persistentLeaves.write(leavesStorage);
            storage.setTag("leaves" + tagSuffix, leavesStorage);
        }
    }
    
    
    public static Leaves deserialize(NBTTagCompound storage)
    {
        return deserialize(storage, "");
    }
    
    
    public static Leaves deserialize(NBTTagCompound storage, String tagSuffix)
    {
        Leaves leaves = LeavesFactory.createLeavesFromTypeId(
                storage.getString("leavesType" + tagSuffix));

        if (leaves instanceof INbtPersistent)
        {
            INbtPersistent persistentLeaves = (INbtPersistent)leaves;
            persistentLeaves.read(storage.getCompoundTag("leaves" + tagSuffix));
        }
        
        return leaves;
    }
}
