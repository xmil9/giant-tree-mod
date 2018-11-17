package xmilcode.gianttreemod.tree.structure.leaves;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class PersistentLeaves extends BaseLeaves implements INbtPersistent
{
    public static final String TYPE_ID = "persistent_leaves";

    private static final String AXIS_X_TAG = "axis_x";
    private static final String AXIS_Y_TAG = "axis_y";
    private static final String AXIS_Z_TAG = "axis_z";
    private static final String POSITION_X_TAG = "pos_x";
    private static final String POSITION_Y_TAG = "pos_y";
    private static final String POSITION_Z_TAG = "pos_z";
    private static final String LENGTH_TAG = "length";
    private static final String WIDTH_TAG = "width";
    private static final String HEIGHT_TAG = "height";
    private static final String AT_HEIGHT_TAG = "at_height";
    private static final String TREE_CORE_POSITION_X_TAG = "tree_core_pos_x";
    private static final String TREE_CORE_POSITION_Y_TAG = "tree_core_pos_y";
    private static final String TREE_CORE_POSITION_Z_TAG = "tree_core_pos_z";
    
    // Package visibility only! Factory should be used to create instances.
    PersistentLeaves(
            LeavesSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        super(spec, atHeight, treeCorePos);
    }

    
    @Override
    public String typeId()
    {
        return TYPE_ID;
    }
    
    
    @Override
    public void read(NBTTagCompound storage)
    {   
        Vector3D branchAxis = new Vector3D(
                storage.getInteger(AXIS_X_TAG),
                storage.getInteger(AXIS_Y_TAG),
                storage.getInteger(AXIS_Z_TAG));
        Coord3D pos = new Coord3D(
                storage.getInteger(POSITION_X_TAG),
                storage.getInteger(POSITION_Y_TAG),
                storage.getInteger(POSITION_Z_TAG));
        int length = storage.getInteger(LENGTH_TAG);
        int width = storage.getInteger(WIDTH_TAG);
        int height = storage.getInteger(HEIGHT_TAG); 
        int atHeight = storage.getInteger(AT_HEIGHT_TAG);
        Coord3D treeCorePos = new Coord3D(
                storage.getInteger(TREE_CORE_POSITION_X_TAG),
                storage.getInteger(TREE_CORE_POSITION_Y_TAG),
                storage.getInteger(TREE_CORE_POSITION_Z_TAG));
        
        reconstructLeaves(
                new LeavesSpec(branchAxis, pos, length, width, height),
                atHeight,
                treeCorePos);
    }

    
    private void reconstructLeaves(
            LeavesSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        this.spec = spec;
        this.atTreeHeight = atHeight;
        this.treeCorePos = treeCorePos;
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
    }
    

    @Override
    public void write(NBTTagCompound storage)
    {
        storage.setInteger(AXIS_X_TAG, branchAxis().x);
        storage.setInteger(AXIS_Y_TAG, branchAxis().y);
        storage.setInteger(AXIS_Z_TAG, branchAxis().z);
        storage.setInteger(POSITION_X_TAG, position().x);
        storage.setInteger(POSITION_Y_TAG, position().y);
        storage.setInteger(POSITION_Z_TAG, position().z);
        storage.setInteger(LENGTH_TAG, length());
        storage.setInteger(WIDTH_TAG, width());
        storage.setInteger(HEIGHT_TAG, height());
        storage.setInteger(AT_HEIGHT_TAG, atTreeHeight());
        storage.setInteger(TREE_CORE_POSITION_X_TAG, treeCorePosition().x);
        storage.setInteger(TREE_CORE_POSITION_Y_TAG, treeCorePosition().y);
        storage.setInteger(TREE_CORE_POSITION_Z_TAG, treeCorePosition().z);
    }
}
