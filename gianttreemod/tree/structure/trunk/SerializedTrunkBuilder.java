package xmilcode.gianttreemod.tree.structure.trunk;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.nbt.NBTTagCompound;


// Collects all data required to create a serialized trunk object.
public class SerializedTrunkBuilder
{
    private NBTTagCompound storage;
    private String tagSuffix = "";
    private GrowthManager growthMgr;
    
    
    public SerializedTrunkBuilder setStorage(NBTTagCompound storage)
    {
        this.storage = storage;
        return this;
    }
    
    
    public SerializedTrunkBuilder setStorageTagSuffix(String tagSuffix)
    {
        this.tagSuffix = tagSuffix;
        return this;
    }
    
    
    public SerializedTrunkBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Trunk build()
    {
        if (growthMgr == null || storage == null)
            throw new IllegalStateException();
        
        Trunk trunk = TrunkSerializationService.deserialize(storage, tagSuffix);
        trunk.setGrowthManager(growthMgr);
        return trunk;
        
    }
}
