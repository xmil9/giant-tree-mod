package xmilcode.gianttreemod.tree.structure.leaves;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.structure.leaves.Leaves;
import xmilcode.gianttreemod.tree.structure.leaves.LeavesSerializationService;
import xmilcode.gianttreemod.tree.structure.leaves.SerializedLeavesBuilder;


// Collects all data required to create a serialized leaves object.
public class SerializedLeavesBuilder
{
    private NBTTagCompound storage;
    private String tagSuffix = "";
    private GrowthManager growthMgr;
    
    
    public SerializedLeavesBuilder setStorage(NBTTagCompound storage)
    {
        this.storage = storage;
        return this;
    }
    
    
    public SerializedLeavesBuilder setStorageTagSuffix(String suffix)
    {
        this.tagSuffix = suffix;
        return this;
    }
    
    
    public SerializedLeavesBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Leaves build()
    {
        if (growthMgr == null || storage == null)
            throw new IllegalStateException();
        
        Leaves leaves = LeavesSerializationService.deserialize(storage, tagSuffix);
        leaves.setGrowthManager(growthMgr);
        return leaves;
        
    }
}
