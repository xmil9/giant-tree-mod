package xmilcode.gianttreemod.tree.growth;

import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import net.minecraft.world.World;


// Interface to be provided by components that want to support growth.
public interface GrowablePart
{
    public void setGrowthManager(GrowthManager mgr);
    public boolean growAt(TreeBlockAtPos sourceBlock);
}
