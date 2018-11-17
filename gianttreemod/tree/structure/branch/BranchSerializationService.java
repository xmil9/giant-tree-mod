package xmilcode.gianttreemod.tree.structure.branch;

import xmilcode.mclib.INbtPersistent;
import net.minecraft.nbt.NBTTagCompound;


// Handles workflow of serializing branch objects.
public class BranchSerializationService
{
    public static void serialize(Branch branch, NBTTagCompound storage)
    {
        serialize(branch, storage, "");
    }
    
    
    public static void serialize(Branch branch, NBTTagCompound storage, String tagSuffix)
    {
        storage.setString("branchType" + tagSuffix, branch.typeId());
        
        if (branch instanceof INbtPersistent)
        {
            INbtPersistent persistentBranch = (INbtPersistent)branch;

            NBTTagCompound branchStorage = new NBTTagCompound();
            persistentBranch.write(branchStorage);
            storage.setTag("branch" + tagSuffix, branchStorage);
        }
    }
    
    
    public static Branch deserialize(NBTTagCompound storage)
    {
        return deserialize(storage, "");
    }
    
    
    public static Branch deserialize(NBTTagCompound storage, String tagSuffix)
    {
        Branch branch = BranchFactory.createBranchFromTypeId(
                storage.getString("branchType" + tagSuffix));

        if (branch instanceof INbtPersistent)
        {
            INbtPersistent persistentBranch = (INbtPersistent)branch;
            persistentBranch.read(storage.getCompoundTag("branch" + tagSuffix));
        }
        
        return branch;
    }
}
