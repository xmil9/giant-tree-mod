package xmilcode.gianttreemod.tree.structure.branch;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


//Collects all data required to create a branch object.
public class NormalBranchBuilder
{
    private static final int INVALID_RADIUS = -1;
    private static final int INVALID_LENGTH = -1;
    private static final int INVALID_AT_HEIGHT = -1;
    
    private Vector3D axis;
    private Coord3D branchPos;
    private int radius;
    private int length;
    private int atHeight;
    private Coord3D treeCorePos;
    private GrowthManager growthMgr;
    
    
    public NormalBranchBuilder()
    {
        axis = null;
        branchPos = null;
        radius = INVALID_RADIUS;
        length = INVALID_LENGTH;
        atHeight = INVALID_AT_HEIGHT;
        treeCorePos = null;
        growthMgr = null;
    }
    
    
    public NormalBranchBuilder setAxis(Vector3D axis)
    {
        this.axis = axis;
        return this;
    }

    
    public NormalBranchBuilder setPosition(Coord3D pos)
    {
        this.branchPos = pos;
        return this;
    }
    
    
    public NormalBranchBuilder setRadius(int radius)
    {
        this.radius = radius;
        return this;
    }
    
    
    public NormalBranchBuilder setLength(int length)
    {
        this.length = length;
        return this;
    }
    
    
    public NormalBranchBuilder setAtHeight(int atHeight)
    {
        this.atHeight = atHeight;
        return this;
    }
    
    
    public NormalBranchBuilder setTreeCorePosition(Coord3D corePos)
    {
        this.treeCorePos = corePos;
        return this;
    }
    
    
    public NormalBranchBuilder setGrowthManager(GrowthManager mgr)
    {
        this.growthMgr = mgr;
        return this;
    }
    
    
    public Branch build()
    {
        if (growthMgr == null ||
                branchPos == null ||
                axis == null ||
                radius == INVALID_RADIUS ||
                length == INVALID_LENGTH ||
                atHeight == INVALID_AT_HEIGHT ||
                treeCorePos == null)
        {
            throw new IllegalStateException();
        }
        
        Branch branch = BranchFactory.createNormalBranch(
                new BranchSpec(
                        axis,
                        branchPos,
                        radius,
                        length),
                atHeight,
                treeCorePos);
        branch.setGrowthManager(growthMgr);
        return branch;
        
    }
}
