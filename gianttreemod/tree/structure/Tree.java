package xmilcode.gianttreemod.tree.structure;

import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.structure.branch.Branch;
import xmilcode.mclib.loot.Loot;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public interface Tree
{
    public String typeId();
    public Coord3D corePosition();
    public int trunkRadius();
    public int trunkHeight();
    public int fullHeight();
    public void addBranch(Branch branch);
    public void setGrowthManager(GrowthManager mgr);
    public void setLoot(Loot loot);
    public boolean growWoodBlockAt(TreeBlockAtPos sourceBlock);
    public boolean growLeafBlockAt(TreeBlockAtPos sourceBlock);
    public boolean growCoreBlockAt(TreeBlockAtPos sourceBlock);
}
