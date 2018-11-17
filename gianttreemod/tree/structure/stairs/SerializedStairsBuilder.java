package xmilcode.gianttreemod.tree.structure.stairs;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.gianttreemod.tree.growth.GrowthManager;



// Collects all data required to create a serialized stairs object.
public class SerializedStairsBuilder
{
    private NBTTagCompound storage;
    private String tagSuffix = "";
    private GrowthManager growthMgr;
    
    
    public SerializedStairsBuilder setStorage(NBTTagCompound storage)
    {
        this.storage = storage;
        return this;
    }
    
    
    public SerializedStairsBuilder setStorageTagSuffix(String suffix)
    {
        this.tagSuffix = suffix;
        return this;
    }
    
    
    public SerializedStairsBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Stairs build()
    {
        if (growthMgr == null || storage == null)
            throw new IllegalStateException();
        
        Stairs stairs = StairsSerializationService.deserialize(storage, tagSuffix);
        stairs.setGrowthManager(growthMgr);
        return stairs;
        
    }
}
