package xmilcode.gianttreemod.tree.structure;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.loot.Loot;


public class SerializedTreeBuilder
{
    private NBTTagCompound storage;
    private String tagSuffix = "";
    private GrowthManager growthMgr;
    private Loot loot;

    
    public SerializedTreeBuilder setStorage(NBTTagCompound storage)
    {
        this.storage = storage;
        return this;
    }
    
    
    public SerializedTreeBuilder setStorageTagSuffix(String tagSuffix)
    {
        this.tagSuffix = tagSuffix;
        return this;
    }
    
    
    public SerializedTreeBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public SerializedTreeBuilder setLoot(Loot loot)
    {
        this.loot = loot;
        return this;
    }
    
    
    public Tree build()
    {
        if (growthMgr == null || storage == null)
            throw new IllegalStateException();
        
        Tree tree = TreeSerializationService.deserialize(storage, tagSuffix);
        tree.setGrowthManager(growthMgr);
        tree.setLoot(loot);
        return tree;
        
    }
}
