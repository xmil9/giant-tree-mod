package xmilcode.gianttreemod.tree.structure.trunk;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.structure.tip.TreeTipSpec;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.nbt.NBTTagCompound;


// Functionality to serialize a trunk object's data.
public class PersistentTrunk extends BaseTrunk implements INbtPersistent
{
    public static final String TYPE_ID = "trunk";
    
    private static final String POSITION_X_TAG = "pos_x";
    private static final String POSITION_Y_TAG = "pos_y";
    private static final String POSITION_Z_TAG = "pos_z";
    private static final String RADIUS_TAG = "radius";
    private static final String HEIGHT_TAG = "height";

    // Package visibility only! Factory should be used to create instances.
    PersistentTrunk(TrunkSpec spec)
    {
        super(spec);
    }
    
    
    @Override
    public String typeId()
    {
        return TYPE_ID;
    }

    
    @Override
    public void read(NBTTagCompound storage)
    {
        Coord3D pos = new Coord3D(
                storage.getInteger(POSITION_X_TAG),
                storage.getInteger(POSITION_Y_TAG),
                storage.getInteger(POSITION_Z_TAG));
        int radius = storage.getInteger(RADIUS_TAG);
        int height = storage.getInteger(HEIGHT_TAG);
        
        reconstructTrunk(new TrunkSpec(pos, radius, height));
    }

    
    private void reconstructTrunk(TrunkSpec spec)
    {
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
    }
    

    @Override
    public void write(NBTTagCompound storage)
    {
        storage.setInteger(POSITION_X_TAG, position().x);
        storage.setInteger(POSITION_Y_TAG, position().y);
        storage.setInteger(POSITION_Z_TAG, position().z);
        storage.setInteger(RADIUS_TAG, radius());
        storage.setInteger(HEIGHT_TAG, height());
    }
}
