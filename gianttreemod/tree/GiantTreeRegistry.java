package xmilcode.gianttreemod.tree;

import java.util.HashSet;
import java.util.Set;

import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.CoordUtil2D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import net.minecraft.world.World;


// Holds all trees and their associated worlds.
public class GiantTreeRegistry
{
    // Consistent world comparison. 
    private static boolean areWorldsEqual(World a, World b)
    {
        return (a.getSeed() == b.getSeed());
    }
    
    
    // Entry for tree collection.
    private class Entry
    {
        public final World world;
        public final Tree tree;
        
        public Entry(World world, Tree tree)
        {
            this.world = world;
            this.tree = tree;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Entry)
            {
                Entry cmp = (Entry)obj;
                return (GiantTreeRegistry.areWorldsEqual(world, cmp.world) &&
                        tree.equals(cmp.tree));
            }
            
            return false;
        }
        
        @Override
        public int hashCode()
        {
            return (int)(world.getSeed() +
                    tree.corePosition().x * 7 +
                    tree.corePosition().y * 11 +
                    tree.corePosition().z * 13);
        }
    }
    
    
    // Collection of trees and their associated worlds.
    private Set<Entry> entries = new HashSet<Entry>();
    
    
    public void registerTree(World world, Tree tree)
    {
        if (entries.add(new Entry(world, tree)))
        {
            System.out.println("Giant tree registered at " + tree.corePosition().toString() +
                    " for world <" + world.getSeed() + ">.");
        }
    }
    
    
    public void unregisterTree(World world, Tree tree)
    {
        if (entries.remove(new Entry(world, tree)))
        {
            System.out.println("Giant tree unregistered at " + tree.corePosition().toString() +
                    " for world <" + world.getSeed() + ">.");
        }
    }


    // Returns first found tree in given world that is within a cube
    // around a given position. The positions y-coord has to be within
    // the tree's base and top.
    public Tree findAnyTreeWithinBox(
            World world,
            Coord3D pos,
            int boxSideLengthHalf,
            int belowTreeMargin)
    {
        int minX = pos.x - boxSideLengthHalf;
        int maxX = pos.x + boxSideLengthHalf;
        int minZ = pos.z - boxSideLengthHalf;
        int maxZ = pos.z + boxSideLengthHalf;

        for (Entry entry : entries)
        {
            if (areWorldsEqual(world, entry.world))
            {
                Coord3D treePos = entry.tree.corePosition();
                
                if (treePos.x >= minX &&
                        treePos.x <= maxX &&
                        treePos.z >= minZ &&
                        treePos.z <= maxZ &&
                        pos.y >= Math.max(treePos.y - belowTreeMargin, 0) &&
                        pos.y <= treePos.y + entry.tree.fullHeight())
                {
                    return entry.tree;
                }
            }
        }
        
        return null;
    }
    
    
    // Returns tree closest to given position in given world or
    // null if no tree exists.
    public Tree findClosestTree(World world, Coord3D pos)
    {
        Tree closestTree = null;
        int closestDistSquared = Integer.MAX_VALUE;
        
        Coord2D posInXZPlane = CoordUtil3D.mapTo2DXZPlane(pos);

        for (Entry entry : entries)
        {
            if (world.equals(entry.world))
            {
                int distSquared = CoordUtil2D.distanceSquared2D(
                        posInXZPlane,
                        CoordUtil3D.mapTo2DXZPlane(entry.tree.corePosition()));
                
                if (distSquared < closestDistSquared)
                {
                    closestTree = entry.tree;
                    closestDistSquared = distSquared;
                }
            }
        }
        
        return closestTree;
    }
}
