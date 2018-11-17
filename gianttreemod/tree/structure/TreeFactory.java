package xmilcode.gianttreemod.tree.structure;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import xmilcode.gianttreemod.tree.structure.trunk.BaseTrunk;
import xmilcode.gianttreemod.tree.structure.trunk.PersistentTrunk;
import xmilcode.mclib.datastructures.ClosedRange;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.MathUtil;
import xmilcode.mclib.util.RandomUtil;


public class TreeFactory
{
    public static Tree createNullTree()
    {
        return new NullTree();
    }
    
    
    public static Tree createNormalTree(TreeSpec spec)
    {
        return new PersistentTree(spec);
    }
    
    
    // Used for deserialization.
    static Tree createTreeFromTypeId(String typeId)
    {
        if (typeId.equals(PersistentTree.TYPE_ID))
        {
            return createNormalTree(new TreeSpec());
        }
        else if (typeId.equals(NullTree.TYPE_ID))
        {
            return createNullTree();
        }
        else
        {
            throw new InvalidParameterException();
        }
    }
}
