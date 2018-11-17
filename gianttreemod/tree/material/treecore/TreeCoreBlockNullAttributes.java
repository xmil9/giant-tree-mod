package xmilcode.gianttreemod.tree.material.treecore;

import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.gianttreemod.tree.structure.TreeFactory;
import xmilcode.mclib.pixelgeometry3d.Neighbor3D;
import net.minecraft.nbt.NBTTagCompound;


public class TreeCoreBlockNullAttributes implements TreeCoreBlockPersistentAttributes
{
    @Override
    public Tree tree()
    {
        return TreeFactory.createNullTree();
    }
    
    @Override
    public void setTree(Tree tree)
    {
    }

    @Override
    public  boolean canGrow()
    {
        return false;
    }

    @Override
    public void markAsDoneGrowing()
    {
    }
}
