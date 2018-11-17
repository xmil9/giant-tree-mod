package xmilcode.gianttreemod.tree.structure.branch;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.gianttreemod.tree.growth.GrowthManager;


// Collects all data required to create a serialized branch object.
public class SerializedBranchBuilder
{
    private NBTTagCompound storage;
    private String tagSuffix = "";
    private GrowthManager growthMgr;
    
    
    public SerializedBranchBuilder setStorage(NBTTagCompound storage)
    {
        this.storage = storage;
        return this;
    }
    
    
    public SerializedBranchBuilder setStorageTagSuffix(String suffix)
    {
        this.tagSuffix = suffix;
        return this;
    }
    
    
    public SerializedBranchBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Branch build()
    {
        if (growthMgr == null || storage == null)
            throw new IllegalStateException();
        
        Branch branch = BranchSerializationService.deserialize(storage, tagSuffix);
        branch.setGrowthManager(growthMgr);
        return branch;
        
    }
}
