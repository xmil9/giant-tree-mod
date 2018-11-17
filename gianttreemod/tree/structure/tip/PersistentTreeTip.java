package xmilcode.gianttreemod.tree.structure.tip;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class PersistentTreeTip extends BaseTreeTip implements INbtPersistent
{
    public static final String TYPE_ID = "persistent_tip";
    
    private static final String BASE_CENTER_X_TAG = "base_center_x";
    private static final String BASE_CENTER_Y_TAG = "base_center_y";
    private static final String BASE_CENTER_Z_TAG = "base_center_z";
    private static final String BASE_RADIUS_TAG = "base_radius";
    private static final String HEIGHT_TAG = "height";
    private static final String TREE_CORE_POS_X_TAG = "tree_core_pos_x";
    private static final String TREE_CORE_POS_Y_TAG = "tree_core_pos_y";
    private static final String TREE_CORE_POS_Z_TAG = "tree_core_pos_z";
    
    
    // Package visibility only! Factory should be used to create instances.
    PersistentTreeTip(
            TreeTipSpec spec,
            Coord3D treeCorePos)
    {
        super(spec, treeCorePos);
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
        int baseRadius = storage.getInteger(BASE_RADIUS_TAG);
        int height = storage.getInteger(HEIGHT_TAG);
        Coord3D treeCorePos = new Coord3D(
                storage.getInteger(TREE_CORE_POS_X_TAG),
                storage.getInteger(TREE_CORE_POS_Y_TAG),
                storage.getInteger(TREE_CORE_POS_Z_TAG));
        
        reconstructTip(
                new TreeTipSpec(baseCenter, baseRadius, height),
                treeCorePos);
    }

    
    private void reconstructTip(TreeTipSpec spec, Coord3D treeCorePos)
    {
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
        this.treeCorePos = treeCorePos; 
    }
    
    
    @Override
    public void write(NBTTagCompound storage)
    {
        storage.setInteger(BASE_CENTER_X_TAG, baseCenter().x);
        storage.setInteger(BASE_CENTER_Y_TAG, baseCenter().y);
        storage.setInteger(BASE_CENTER_Z_TAG, baseCenter().z);
        storage.setInteger(BASE_RADIUS_TAG, baseRadius());
        storage.setInteger(HEIGHT_TAG, height());
        storage.setInteger(TREE_CORE_POS_X_TAG, treeCorePosition().x);
        storage.setInteger(TREE_CORE_POS_Y_TAG, treeCorePosition().y);
        storage.setInteger(TREE_CORE_POS_Z_TAG, treeCorePosition().z);
    }

}
