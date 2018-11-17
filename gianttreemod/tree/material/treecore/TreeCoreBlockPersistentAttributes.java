package xmilcode.gianttreemod.tree.material.treecore;

import xmilcode.gianttreemod.tree.structure.Tree;



//Access to attributes of a core block that get stored by a tile entity.
public interface TreeCoreBlockPersistentAttributes
{
    Tree tree();
    void setTree(Tree tree);
    boolean canGrow();
    void markAsDoneGrowing();
}
