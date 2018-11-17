package xmilcode.gianttreemod.tree.structure.tip;

import xmilcode.mclib.INbtPersistent;
import net.minecraft.nbt.NBTTagCompound;


//Handles workflow of serializing tree tip objects.
public class TreeTipSerializationService
{
    public static void serialize(TreeTip tip, NBTTagCompound storage)
    {
        serialize(tip, storage, "");
    }
    
    
    public static void serialize(TreeTip tip, NBTTagCompound storage, String tagSuffix)
    {
        storage.setString("tipType" + tagSuffix, tip.typeId());
        
        if (tip instanceof INbtPersistent)
        {
            INbtPersistent persistentTip = (INbtPersistent)tip;

            NBTTagCompound leavesStorage = new NBTTagCompound();
            persistentTip.write(leavesStorage);
            storage.setTag("tip" + tagSuffix, leavesStorage);
        }
    }
    
    
    public static TreeTip deserialize(NBTTagCompound storage)
    {
        return deserialize(storage, "");
    }
    
    
    public static TreeTip deserialize(NBTTagCompound storage, String tagSuffix)
    {
        TreeTip tip = TreeTipFactory.createTipFromTypeId(
                storage.getString("tipType" + tagSuffix));

        if (tip instanceof INbtPersistent)
        {
            INbtPersistent persistentTip = (INbtPersistent)tip;
            persistentTip.read(storage.getCompoundTag("tip" + tagSuffix));
        }
        
        return tip;
    }
}
