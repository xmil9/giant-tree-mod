package xmilcode.gianttreemod.tree.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.loot.TreeLootFactory;
import xmilcode.gianttreemod.tree.structure.branch.Branch;
import xmilcode.gianttreemod.tree.structure.branch.BranchGenerator;
import xmilcode.mclib.datastructures.ClosedRange;
import xmilcode.mclib.loot.SingleUseLootDecorator;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.MathUtil;
import xmilcode.mclib.util.RandomUtil;


// Generates a tree with random properties.
public class TreeGenerator
{
    // Mapping of trunk radius to valid tree heights.  
    private static final Map<Integer, ClosedRange<Integer>> TRUNK_HEIGHT_LIMITS =
            initHeightLimits();
    
    
    private static Map<Integer, ClosedRange<Integer>> initHeightLimits()
    {
        final int trunkHeightDelta = TreeParameters.MAX_TRUNK_HEIGHT - TreeParameters.MIN_TRUNK_HEIGHT;
        final int numTrunkRadii = TreeParameters.MAX_TRUNK_RADIUS - TreeParameters.MIN_TRUNK_RADIUS + 1;
        final int rangeSize = trunkHeightDelta / numTrunkRadii;
        int rangeStart = TreeParameters.MIN_TRUNK_HEIGHT;
        
        HashMap<Integer, ClosedRange<Integer>> ranges =
                new HashMap<Integer, ClosedRange<Integer>>();
    
        for (int i = TreeParameters.MIN_TRUNK_RADIUS; i <= TreeParameters.MAX_TRUNK_RADIUS; ++i)
        {
            ClosedRange<Integer> heightRange = new ClosedRange<Integer>(
                    MathUtil.clamp(
                            rangeStart - TreeParameters.TRUNK_HEIGHT_RANGE_OVERLAP,
                            TreeParameters.MIN_TRUNK_HEIGHT,
                            TreeParameters.MAX_TRUNK_HEIGHT),
                    MathUtil.clamp(
                            rangeStart + rangeSize + TreeParameters.TRUNK_HEIGHT_RANGE_OVERLAP,
                            TreeParameters.MIN_TRUNK_HEIGHT,
                            TreeParameters.MAX_TRUNK_HEIGHT));
            ranges.put(i, heightRange);
            
            rangeStart += rangeSize;
        }
        
        return ranges;
    }
    
    
    // Creates a tree at a given position and initializes it with random
    // attributes.
    public static Tree generateTreeAt(Coord3D at, Random rand)
    {
        int trunkRadius = RandomUtil.randInt(
                TreeParameters.MIN_TRUNK_RADIUS,
                TreeParameters.MAX_TRUNK_RADIUS,
                rand);
        int fullHeight = RandomUtil.randInt(
                TRUNK_HEIGHT_LIMITS.get(trunkRadius).min,
                TRUNK_HEIGHT_LIMITS.get(trunkRadius).max,
                rand);
        int trunkHeight = fullHeight - TreeParameters.tipHeight(fullHeight);
        
        BranchGenerator branchGen =
                new BranchGenerator(at, trunkRadius, trunkHeight, rand);
        List<Branch> branches = branchGen.generateBranches();
        
        NormalTreeBuilder treeBuilder = new NormalTreeBuilder();
        treeBuilder.setPosition(at);
        treeBuilder.setFullHeight(fullHeight);
        treeBuilder.setTrunkRadius(trunkRadius);
        treeBuilder.setTrunkHeight(trunkHeight);
        treeBuilder.setGrowthManager(new GrowthManager());
        treeBuilder.setBranches(branches);
        treeBuilder.setLoot(TreeLootFactory.createTreeLoot());
        return treeBuilder.build();
    }

    
    @SuppressWarnings("unused")
    public static int calcNumberOfGenerationAttempts(Random rand)
    {
        if (TreeParameters.CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK >= 1)
        {
            return (int)TreeParameters.CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK;
        }
        else
        {
            return RandomUtil.sampleRandEvent(
                    TreeParameters.CHANCE_OF_TREE_GEN_ATTEMPT_IN_CHUNK, rand) ? 1 : 0;
        }
    }
}
