package xmilcode.gianttreemod.tree.structure.stairs;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.nbt.NBTTagCompound;


// Functionality to serialize a stairs object's data.
public class PersistentStairs extends BaseStairs implements INbtPersistent
{
    public static final String TYPE_ID = "persistent_stairs";
    
    private static final String BASE_CENTER_X_TAG = "base_center_x";
    private static final String BASE_CENTER_Y_TAG = "base_center_y";
    private static final String BASE_CENTER_Z_TAG = "base_center_z";
    private static final String RADIUS_TAG = "radius";
    private static final String HEIGHT_TAG = "height";

    // Package visibility only! Factory should be used to create instances.
    PersistentStairs(StairsSpec spec)
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
        Coord3D baseCenter = new Coord3D(
                storage.getInteger(BASE_CENTER_X_TAG),
                storage.getInteger(BASE_CENTER_Y_TAG),
                storage.getInteger(BASE_CENTER_Z_TAG));
        int radius = storage.getInteger(RADIUS_TAG);
        int height = storage.getInteger(HEIGHT_TAG);
        
        reconstructStairs(new StairsSpec(baseCenter, radius, height));
    }

    
    private void reconstructStairs(StairsSpec spec)
    {
        this.baseCenter = spec.baseCenter();
        this.radius = spec.trunkRadius();
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
    }
    

    @Override
    public void write(NBTTagCompound storage)
    {
        storage.setInteger(BASE_CENTER_X_TAG, baseCenter.x);
        storage.setInteger(BASE_CENTER_Y_TAG, baseCenter.y);
        storage.setInteger(BASE_CENTER_Z_TAG, baseCenter.z);
        storage.setInteger(RADIUS_TAG, radius);
        storage.setInteger(HEIGHT_TAG, height());
    }
}
