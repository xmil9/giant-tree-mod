package xmilcode.gianttreemod.tree.structure.trunk;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.mclib.INbtPersistent;


// Handles workflow of serializing trunk objects.
public class TrunkSerializationService
{
    public static void serialize(Trunk trunk, NBTTagCompound storage)
    {
        serialize(trunk, storage, "");
    }
    
    
    public static void serialize(Trunk trunk, NBTTagCompound storage, String tagSuffix)
    {
        storage.setString("trunkType" + tagSuffix, trunk.typeId());
        
        if (trunk instanceof INbtPersistent)
        {
            INbtPersistent persistentTrunk = (INbtPersistent)trunk;

            NBTTagCompound trunkStorage = new NBTTagCompound();
            persistentTrunk.write(trunkStorage);
            storage.setTag("trunk" + tagSuffix, trunkStorage);
        }
    }
    
    
    public static Trunk deserialize(NBTTagCompound storage)
    {
        return deserialize(storage, "");
    }
    
    
    public static Trunk deserialize(NBTTagCompound storage, String tagSuffix)
    {
        Trunk trunk = TrunkFactory.createTrunkFromTypeId(
                storage.getString("trunkType") + tagSuffix);

        if (trunk instanceof INbtPersistent)
        {
            INbtPersistent persistentTrunk = (INbtPersistent)trunk;
            persistentTrunk.read(storage.getCompoundTag("trunk" + tagSuffix));
        }
        
        return trunk;
    }
}
