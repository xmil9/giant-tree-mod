package xmilcode.gianttreemod.tree.structure;

import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.loot.Loot;
import net.minecraft.nbt.NBTTagCompound;


//Handles workflow of serializing tree objects.
public class TreeSerializationService
{
    private static final String TREE_TYPE_TAG = "tree_type";
    private static final String TREE_TAG = "tree";
    
    
    public static void serialize(Tree tree, NBTTagCompound storage)
    {
        serialize(tree, storage, "");
    }
    
    
    public static void serialize(Tree tree, NBTTagCompound storage, String tagSuffix)
    {
        storage.setString(TREE_TYPE_TAG + tagSuffix, tree.typeId());
        
        if (tree instanceof INbtPersistent)
        {
            INbtPersistent persistentTree = (INbtPersistent)tree;

            NBTTagCompound treeStorage = new NBTTagCompound();
            persistentTree.write(treeStorage);
            storage.setTag(TREE_TAG + tagSuffix, treeStorage);
        }
    }
    
    
    public static Tree deserialize(NBTTagCompound storage)
    {
        return deserialize(storage, "");
    }

    
    public static Tree deserialize(NBTTagCompound storage, String tagSuffix)
    {
        Tree tree = TreeFactory.createTreeFromTypeId(
                storage.getString(TREE_TYPE_TAG + tagSuffix));

        if (tree instanceof INbtPersistent)
        {
            INbtPersistent persistentTree = (INbtPersistent)tree;
            persistentTree.read(storage.getCompoundTag(TREE_TAG + tagSuffix));
        }
        
        return tree;
    }
}
