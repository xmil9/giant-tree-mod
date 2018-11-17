package xmilcode.gianttreemod.tree.structure.branch;

import net.minecraft.nbt.NBTTagCompound;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class PersistentBranch extends BaseBranch implements INbtPersistent
{
    public static final String TYPE_ID = "persistent_branch";

    private static final String AXIS_X_TAG = "axis_x";
    private static final String AXIS_Y_TAG = "axis_y";
    private static final String AXIS_Z_TAG = "axis_z";
    private static final String POSITION_X_TAG = "pos_x";
    private static final String POSITION_Y_TAG = "pos_y";
    private static final String POSITION_Z_TAG = "pos_z";
    private static final String RADIUS_TAG = "radius";
    private static final String LENGTH_TAG = "length";
    private static final String AT_HEIGHT_TAG = "at_height";
    private static final String TREE_CORE_POSITION_X_TAG = "tree_core_pos_x";
    private static final String TREE_CORE_POSITION_Y_TAG = "tree_core_pos_y";
    private static final String TREE_CORE_POSITION_Z_TAG = "tree_core_pos_z";
    
    // Package visibility only! Factory should be used to create instances.
    PersistentBranch(
            BranchSpec spec,
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
        Vector3D axis = new Vector3D(
                storage.getInteger(AXIS_X_TAG),
                storage.getInteger(AXIS_Y_TAG),
                storage.getInteger(AXIS_Z_TAG));
        Coord3D pos = new Coord3D(
                storage.getInteger(POSITION_X_TAG),
                storage.getInteger(POSITION_Y_TAG),
                storage.getInteger(POSITION_Z_TAG));
        int radius = storage.getInteger(RADIUS_TAG);
        int length = storage.getInteger(LENGTH_TAG); 
        int atHeight = storage.getInteger(AT_HEIGHT_TAG);
        Coord3D treeCorePos = new Coord3D(
                storage.getInteger(TREE_CORE_POSITION_X_TAG),
                storage.getInteger(TREE_CORE_POSITION_Y_TAG),
                storage.getInteger(TREE_CORE_POSITION_Z_TAG));
        
        reconstructBranch(
                new BranchSpec(axis, pos, radius, length),
                atHeight,
                treeCorePos);
    }

    
    private void reconstructBranch(
            BranchSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        this.spec = spec;
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
        this.atTreeHeight = atHeight;
        this.treeCorePos = treeCorePos;
    }
    

    @Override
    public void write(NBTTagCompound storage)
    {
        storage.setInteger(AXIS_X_TAG, spec().axis().x);
        storage.setInteger(AXIS_Y_TAG, spec().axis().y);
        storage.setInteger(AXIS_Z_TAG, spec().axis().z);
        storage.setInteger(POSITION_X_TAG, spec().position().x);
        storage.setInteger(POSITION_Y_TAG, spec().position().y);
        storage.setInteger(POSITION_Z_TAG, spec().position().z);
        storage.setInteger(RADIUS_TAG, spec().radius());
        storage.setInteger(LENGTH_TAG, spec().length());
        storage.setInteger(AT_HEIGHT_TAG, atTreeHeight());
        storage.setInteger(TREE_CORE_POSITION_X_TAG, treeCorePosition().x);
        storage.setInteger(TREE_CORE_POSITION_Y_TAG, treeCorePosition().y);
        storage.setInteger(TREE_CORE_POSITION_Z_TAG, treeCorePosition().z);
        
        // Note that the leaves don't need to be serialized because they can
        // deterministically be recreated from the branch's specs.
    }
}
