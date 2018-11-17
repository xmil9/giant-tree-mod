package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.gianttreemod.tree.structure.TreeParameters;
import xmilcode.gianttreemod.tree.structure.branch.BranchSpec;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


// Given the specification for a branch generates the specification for
// its leaves.
public class LeavesSpecGenerator
{
    private BranchSpec branchSpec;
    

    public LeavesSpecGenerator(BranchSpec branchSpec)
    {
        this.branchSpec = branchSpec;
    }
    
    public LeavesSpec generate()
    {
        return new LeavesSpec(
                branchSpec.axis(),
                calcPosition(),
                calcLength(),
                calcWidth(),
                calcHeight()); 
    }
    
    private Coord3D calcPosition()
    {
        int offsetFromTrunk = TreeParameters.offsetOfLeavesFromTrunk(branchSpec.length());
        Vector3D offsetVector = Vector3D.muliply(branchSpec.axis(), offsetFromTrunk);
        return Vector3D.add(branchSpec.position(), offsetVector);
    }
    
    private int calcLength()
    {
        return branchSpec.length();
    }
    
    private int calcWidth()
    {
        return TreeParameters.leavesWidth(branchSpec.length());
    }
    
    private int calcHeight()
    {
        return TreeParameters.leavesHeight(branchSpec.radius());
    }
}
