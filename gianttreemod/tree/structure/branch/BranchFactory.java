package xmilcode.gianttreemod.tree.structure.branch;

import java.security.InvalidParameterException;

import xmilcode.gianttreemod.tree.structure.stairs.BaseStairs;
import xmilcode.gianttreemod.tree.structure.stairs.PersistentStairs;
import xmilcode.gianttreemod.tree.structure.stairs.Stairs;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Factory for branch objects.
// Note that if the parameter collection to completely create branch objects
// is challenging, a builder object should be used instead of directly
// calling the factory. A builder will help to not forget a parameter.
public class BranchFactory
{
    static final int DEFAULT_AT_HEIGHT = 0;
    static final Coord3D DEFAULT_CORE_POSITION = new Coord3D();

    
    public static Branch createNormalBranch(
            BranchSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        return new PersistentBranch(
                spec,
                atHeight,
                treeCorePos);
    }
    
    
    // Used for deserialization.
    static Branch createBranchFromTypeId(String typeId)
    {
        if (typeId.equals(PersistentBranch.TYPE_ID))
        {
            return createNormalBranch(
                    new BranchSpec(),
                    DEFAULT_AT_HEIGHT,
                    DEFAULT_CORE_POSITION);
        }
        else
        {
            throw new InvalidParameterException();
        }
    }
}
