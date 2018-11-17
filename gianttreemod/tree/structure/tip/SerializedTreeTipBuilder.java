package xmilcode.gianttreemod.tree.structure.tip;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.gianttreemod.tree.growth.GrowthManager;


public class SerializedTreeTipBuilder
{
    private NBTTagCompound storage;
    private String tagSuffix = "";
    private GrowthManager growthMgr;
    
    
    public SerializedTreeTipBuilder setStorage(NBTTagCompound storage)
    {
        this.storage = storage;
        return this;
    }
    
    
    public SerializedTreeTipBuilder setStorageTagSuffix(String suffix)
    {
        this.tagSuffix = suffix;
        return this;
    }
    
    
    public SerializedTreeTipBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public TreeTip build()
    {
        if (growthMgr == null || storage == null)
            throw new IllegalStateException();
        
        TreeTip tip = TreeTipSerializationService.deserialize(storage, tagSuffix);
        tip.setGrowthManager(growthMgr);
        return tip;
        
    }
}
