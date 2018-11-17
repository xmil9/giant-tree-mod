package xmilcode.gianttreemod.tree.structure;

import java.util.Random;

import xmilcode.mclib.util.RandomUtil;



public class TreeParameters
{
    // World generation
    
    // Influences how many trees are attempted to be generated per world
    // chunk.
    // If >= 1, then it specifies the number of attempts to generate a tree
    // per chunk.
    // If >= 0 && < 1, then it specifies chance of having one attempt per
    // chunk, e.g. 0.3 means a 30% chance of having one attempt per chunk.
    // Note that attempting to generate a tree might not succeed because
    // the chosen location might not be suitable.
    public static final float CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK = 0.25f; 

    // Range of y positions in mc world where trees can grow.
    // Possible range [0, 255]. 
    public static final int MIN_TREE_Y_POS = 4; 
    public static final int MAX_TREE_Y_POS = 100; 

    
    // Trunk
    
    // Radius means the number of block on either side of the core block.
    // E.g. radius of 1 => trunk diameter of 3,
    //      radius of 2 => trunk diameter of 5,
    //      radius of r => trunk diameter of 2*r + 1
    public static final int MIN_TRUNK_RADIUS = 2;
    public static final int MAX_TRUNK_RADIUS = 5;
    
    // Depending on which y-coord the tree's core is located at the max
    // height might not be reached because the max y-coord is 255. 
    public static final int MIN_TRUNK_HEIGHT = 50;
    public static final int MAX_TRUNK_HEIGHT = 180;
    
    // Overlap (in voxel) of the tree height ranges that are associated
    // with the trunk radii.
    public static final int TRUNK_HEIGHT_RANGE_OVERLAP = 10;
    
    
    // Tip

    public static final int tipHeight(int fullHeight)
    {
        final double TIP_TO_FULL_HEIGHT_RATIO = 0.1;
        return (int)(fullHeight * TIP_TO_FULL_HEIGHT_RATIO);
    }

    
    // Branches
    
    public static final int MIN_BRANCH_LEVELS = 5; 
    public static final int MAX_BRANCH_LEVELS = 6;
    
    public static final int bottomBranchHeight(int trunkHeight)
    {
        final float BOTTOM_BRANCH_TO_TRUNK_HEIGHT_RATIO = 0.25f;
        return (int)(trunkHeight * BOTTOM_BRANCH_TO_TRUNK_HEIGHT_RATIO);
    }
    
    public static final int minBottomBranchLength(int trunkHeight)
    {
        final float MIN_BRANCH_LENGTH_TO_TRUNK_HEIGHT_RATIO = 0.3f;
        return (int)(trunkHeight * MIN_BRANCH_LENGTH_TO_TRUNK_HEIGHT_RATIO);
    }

    public static final int maxBottomBranchLength(int trunkHeight)
    {
        final float MAX_BRANCH_LENGTH_TO_TRUNK_HEIGHT_RATIO = 0.5f;
        return (int)(trunkHeight * MAX_BRANCH_LENGTH_TO_TRUNK_HEIGHT_RATIO);
    }
    
    public static final int topBranchLength(int bottomBranchLength)
    {
        final float TOP_TO_BOTTOM_BRANCH_LENGTH_RATIO = 0.3f;
        return (int)(bottomBranchLength * TOP_TO_BOTTOM_BRANCH_LENGTH_RATIO);
    }
    
    public static final int minBranchRadius(int trunkRadius)
    {
        final int MIN_BRANCH_TO_TRUNK_RADIUS_DIFF = 4;
        return Math.max(trunkRadius - MIN_BRANCH_TO_TRUNK_RADIUS_DIFF, 0);
    }
    
    public static final int maxBranchRadius(int trunkRadius)
    {
        final int MAX_BRANCH_TO_TRUNK_RADIUS_DIFF = 2;
        return trunkRadius - MAX_BRANCH_TO_TRUNK_RADIUS_DIFF;
    }
    
    
    // Leaves
    
    public static final int offsetOfLeavesFromTrunk(int branchLength)
    {
        final float LEAVES_TRUNK_OFFSET_TO_BRANCH_LENGTH_RATIO = 0.2f;
        final int MIN_LEAVES_TRUNK_OFFSET = 3;
        return Math.max(
                (int)(branchLength * LEAVES_TRUNK_OFFSET_TO_BRANCH_LENGTH_RATIO),
                MIN_LEAVES_TRUNK_OFFSET);
    }
    
    public static final int leavesWidth(int branchLength)
    {
        final float LEAVES_WIDTH_TO_BRANCH_LENGTH_RATIO = 0.75f;
        return (int)(branchLength * LEAVES_WIDTH_TO_BRANCH_LENGTH_RATIO);
    }

    
    public static final int leavesHeight(int branchRadius)
    {
        final int LEAVES_HEIGHT_TO_BRANCH_RADIUS_INCREASE = 2;
        return branchRadius + LEAVES_HEIGHT_TO_BRANCH_RADIUS_INCREASE;
    }
}
