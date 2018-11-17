package xmilcode.gianttreemod.tree.structure.branch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.structure.TreeParameters;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.util.RandomUtil;


public class BranchGenerator
{
    private static class BranchLevelSpecs
    {
        private int numLevels;
        private int bottomLevelHeight;
        private int levelDistance;
        
        public BranchLevelSpecs(int trunkHeight, Random rand)
        {
            numLevels = RandomUtil.randInt(
                    TreeParameters.MIN_BRANCH_LEVELS,
                    TreeParameters.MAX_BRANCH_LEVELS,
                    rand);
            bottomLevelHeight = TreeParameters.bottomBranchHeight(trunkHeight);
            levelDistance = (trunkHeight - bottomLevelHeight) / numLevels;
            // Shift the branches up, otherwise the gap before the tip looks
            // too large.
            bottomLevelHeight += levelDistance / 2; 
        }
        
        public int countLevels()
        {
            return numLevels;
        }
        
        public int bottomLevelHeight()
        {
            return bottomLevelHeight;
        }
        
        public int levelDistance()
        {
            return levelDistance;
        }
    }

    
    private static class BranchLengthSpecs
    {
        private int lengthAtBottomLevel;
        private int lengthAtTopLevel;
        private double lengthDeltaPerLevel;
        
        public BranchLengthSpecs(int trunkHeight, int numBranchLevels, Random rand)
        {
            lengthAtBottomLevel = RandomUtil.randInt(
                    TreeParameters.minBottomBranchLength(trunkHeight),
                    TreeParameters.maxBottomBranchLength(trunkHeight),
                    rand);
            lengthAtTopLevel = TreeParameters.topBranchLength(lengthAtBottomLevel);
            lengthDeltaPerLevel =
                    (double)(lengthAtBottomLevel - lengthAtTopLevel) / (double)(numBranchLevels - 1);
        }
        
        public int lengthAtBottomLevel()
        {
            return lengthAtBottomLevel;
        }
        
        public int lengthAtTopLevel()
        {
            return lengthAtTopLevel;
        }
        
        public double lengthDeltaPerLevel()
        {
            return lengthDeltaPerLevel;
        }
    }
    
    
    private static class BranchRadiusSpecs
    {
        private int maxRadius;
        private int minRadius;
        private int numLevelsPerRadius;
        
        public BranchRadiusSpecs(int trunkRadius, int numBranchLevels)
        {
            maxRadius = TreeParameters.maxBranchRadius(trunkRadius);
            minRadius = TreeParameters.minBranchRadius(trunkRadius);
            numLevelsPerRadius = numBranchLevels / Math.max(maxRadius - minRadius, 1);
        }

        public int maxRadius()
        {
            return maxRadius;
        }

        public int minRadius()
        {
            return minRadius;
        }

        public int countLevelsPerRadius()
        {
            return numLevelsPerRadius;
        }
        
        public int calcRadiusAtLevel(int levelIdx)
        {
            return Math.max(
                    maxRadius - ((levelIdx + 1) / numLevelsPerRadius),
                    minRadius);
        }
    }
    
    
    private Coord3D treeCorePos;
    private int trunkRadius;
    private int trunkHeight;
    private Random rand;
    private BranchLevelSpecs levelSpecs;
    private BranchLengthSpecs lengthSpecs;
    private BranchRadiusSpecs radiusSpecs;
    
    
    public BranchGenerator(
            Coord3D treeCorePos,
            int trunkRadius,
            int trunkHeight,
            Random rand)
    {
        this.treeCorePos = treeCorePos;
        this.trunkRadius = trunkRadius;
        this.trunkHeight = trunkHeight;
        this.levelSpecs = new BranchLevelSpecs(trunkHeight, rand);
        this.lengthSpecs =
                new BranchLengthSpecs(trunkHeight, levelSpecs.countLevels(), rand);
        this.radiusSpecs =
                new BranchRadiusSpecs(trunkRadius, levelSpecs.countLevels());
    }

    
    public List<Branch> generateBranches()
    {
        List<Branch> branches = new ArrayList<Branch>();
        
        int currentHeight = levelSpecs.bottomLevelHeight;
        double currentLength = lengthSpecs.lengthAtBottomLevel;
        
        for (int i = 0; i < levelSpecs.countLevels(); ++i)
        {
            branches.addAll(generateBranchesAtLevel(i, currentHeight, (int)currentLength));
            
            currentHeight += levelSpecs.levelDistance();
            currentLength -= lengthSpecs.lengthDeltaPerLevel();
        }
        
        return branches;
    }
    
    
    private List<Branch> generateBranchesAtLevel(
            int levelIdx,
            int levelHeight,
            int levelBranchLength)
    {
        List<Branch> levelBranches = new ArrayList<Branch>();
        
        BranchOrientationIterator orientationIter = new BranchOrientationIterator();
        while (orientationIter.hasNext())
        {
            Branch branch = generateBranch(
                    levelIdx,
                    levelHeight,
                    levelBranchLength,
                    orientationIter.next());
            levelBranches.add(branch);
        }
        
        return levelBranches;
    }
    
    
    private Branch generateBranch(
            int levelIdx,
            int levelHeight,
            int levelBranchLength,
            Vector3D orientation)
    {
        NormalBranchBuilder branchBuilder = new NormalBranchBuilder();
        branchBuilder.setAxis(orientation);
        branchBuilder.setPosition(calcBranchPosition(levelHeight, orientation));
        branchBuilder.setRadius(radiusSpecs.calcRadiusAtLevel(levelIdx));
        branchBuilder.setLength(levelBranchLength);
        branchBuilder.setAtHeight(levelHeight);
        branchBuilder.setTreeCorePosition(treeCorePos);
        // Later the null growth manager will be replaced by the growth
        // manager for the tree. 
        branchBuilder.setGrowthManager(new NullGrowthManager());
        
        return branchBuilder.build();
    }
    
    
    private Coord3D calcBranchPosition(
            int levelHeight,
            Vector3D orientation)
    {
        // Start the branch slightly inside the trunk to prevent
        // discontinuities.
        final int INSIDE_TRUNK_OFFSET = 1;
        
        Vector3D offsetFromTrunkCenter = new Vector3D(
                orientation.x * (trunkRadius - INSIDE_TRUNK_OFFSET),
                levelHeight,
                orientation.z * (trunkRadius - INSIDE_TRUNK_OFFSET));
        
        return Vector3D.add(treeCorePos, offsetFromTrunkCenter);
    }
}
