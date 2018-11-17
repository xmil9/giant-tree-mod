package xmilcode.gianttreemod.tree.structure;

import java.util.ArrayList;
import java.util.List;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.structure.branch.Branch;
import xmilcode.gianttreemod.tree.structure.branch.BranchSerializationService;
import xmilcode.gianttreemod.tree.structure.branch.SerializedBranchBuilder;
import xmilcode.gianttreemod.tree.structure.leaves.Leaves;
import xmilcode.gianttreemod.tree.structure.stairs.SerializedStairsBuilder;
import xmilcode.gianttreemod.tree.structure.stairs.Stairs;
import xmilcode.gianttreemod.tree.structure.stairs.StairsSerializationService;
import xmilcode.gianttreemod.tree.structure.tip.SerializedTreeTipBuilder;
import xmilcode.gianttreemod.tree.structure.tip.TreeTip;
import xmilcode.gianttreemod.tree.structure.tip.TreeTipSerializationService;
import xmilcode.gianttreemod.tree.structure.tip.TreeTipSpec;
import xmilcode.gianttreemod.tree.structure.trunk.SerializedTrunkBuilder;
import xmilcode.gianttreemod.tree.structure.trunk.Trunk;
import xmilcode.gianttreemod.tree.structure.trunk.TrunkSerializationService;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.loot.NoLoot;
import xmilcode.mclib.loot.SingleUseLootDecorator;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.nbt.NBTTagCompound;


//Functionality to serialize a stairs object's data.
public class PersistentTree extends BaseTree implements INbtPersistent 
{
    public static final String TYPE_ID = "persistent_giant_tree";
    
    private static final int INITIAL_VERSION = 1;
    private static final int CURRENT_VERSION = INITIAL_VERSION;
    
    private static final String TREE_DATA_VERSION_TAG = "tree_data_version";
    private static final String FULL_HEIGHT_TAG = "full_height";
    private static final String POSITION_X_TAG = "pos_x";
    private static final String POSITION_Y_TAG = "pos_y";
    private static final String POSITION_Z_TAG = "pos_z";
    private static final String TRUNK_RADIUS_TAG = "trunk_radius";
    private static final String TRUNK_HEIGHT_TAG = "trunk_height";
    private static final String BRANCH_COUNT_TAG = "branch_count";
    
    
    // Package visibility only! Factory should be used to create instances.
    PersistentTree(TreeSpec spec)
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
        int storedVersion = storage.getInteger(TREE_DATA_VERSION_TAG);
        if (storedVersion == INITIAL_VERSION)
            readInitialVersion(storage);
    }

    
    private void readInitialVersion(NBTTagCompound storage)
    {
        Coord3D pos = new Coord3D(
                storage.getInteger(POSITION_X_TAG),
                storage.getInteger(POSITION_Y_TAG),
                storage.getInteger(POSITION_Z_TAG));
        int fullHeight = storage.getInteger(FULL_HEIGHT_TAG);
        int trunkRadius = storage.getInteger(TRUNK_RADIUS_TAG);
        int trunkHeight = storage.getInteger(TRUNK_HEIGHT_TAG);
        
        List<Branch> branches = readBranchesInitialVersion(storage, growthMgr);
        SingleUseLootDecorator singleUseLoot = readLoot(storage);
        
        reconstructTree(
                new TreeSpec(pos, fullHeight, trunkRadius, trunkHeight),
                branches,
                singleUseLoot);
    }

    
    private static List<Branch> readBranchesInitialVersion(
            NBTTagCompound storage,
            GrowthManager growthMgr)
    {
        List<Branch> branches = new ArrayList<Branch>();
        
        int numBranches = storage.getInteger(BRANCH_COUNT_TAG);
        for (int i = 0; i < numBranches; ++i)
        {
            SerializedBranchBuilder branchBuilder = new SerializedBranchBuilder();
            branchBuilder.setStorage(storage).setStorageTagSuffix(String.valueOf(i)).setGrowthManager(growthMgr);
            Branch branch = branchBuilder.build();
            
            branches.add(branch);
        }
        
        return branches;
    }

    
    private static SingleUseLootDecorator readLoot(NBTTagCompound storage)
    {
        SingleUseLootDecorator singleUseLoot = new SingleUseLootDecorator(new NoLoot());
        singleUseLoot.read(storage);
        return singleUseLoot;
    }
    
    
    private void reconstructTree(
            TreeSpec treeSpec,
            List<Branch> branches,
            SingleUseLootDecorator singleUseLoot)
    {
        this.spec = treeSpec;
        this.trunk = createTrunk(treeSpec, growthMgr);
        this.stairs = createStairs(treeSpec, growthMgr);
        this.tip = createTip(treeSpec, growthMgr);
        this.branches = branches;
        this.allLeaves = reconstructLeaves(treeSpec, branches, growthMgr);
        this.singleUseLoot = singleUseLoot;
    }

    
    private static List<Leaves> reconstructLeaves(
            TreeSpec treeSpec,
            List<Branch> branches,
            GrowthManager growthMgr)
    {
        List<Leaves> allLeaves = new ArrayList<Leaves>();
        
        for (Branch branch : branches)
        {
            allLeaves.add(createLeaves(
                    branch.spec(),
                    branch.atTreeHeight(),
                    treeSpec.position(),
                    growthMgr));
        }
        
        return allLeaves;
    }
    
    @Override
    public void write(NBTTagCompound storage)
    {
        storage.setInteger(TREE_DATA_VERSION_TAG, CURRENT_VERSION);
        writeCurrentVersion(storage);
    }
    
    
    private void writeCurrentVersion(NBTTagCompound storage)
    {
        storage.setInteger(POSITION_X_TAG, spec.position().x);
        storage.setInteger(POSITION_Y_TAG, spec.position().y);
        storage.setInteger(POSITION_Z_TAG, spec.position().z);
        storage.setInteger(FULL_HEIGHT_TAG, spec.fullHeight());
        storage.setInteger(TRUNK_RADIUS_TAG, spec.trunkRadius());
        storage.setInteger(TRUNK_HEIGHT_TAG, spec.trunkHeight());

        // For now there is no need to write the trunk, stairs, and tip
        // data because it can be deterministically reconstructed from the
        // tree specs. If, however, any randomness is used in the future
        // to generate the trunk, stairs, or tip, then they need to be
        // written to storage here.

        writeBranches(branches, storage);
        
        singleUseLoot.write(storage);
    }
    

    private static void writeBranches(List<Branch> branches, NBTTagCompound storage)
    {
        storage.setInteger(BRANCH_COUNT_TAG, branches.size());
        for (int i = 0; i < branches.size(); ++i)
            BranchSerializationService.serialize(branches.get(i), storage, String.valueOf(i));
    }
}
