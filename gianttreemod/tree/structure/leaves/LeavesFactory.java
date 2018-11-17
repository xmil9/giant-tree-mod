package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Factory for branch objects.
// Note that if the parameter collection to completely create leaves objects
// is challenging, a builder object should be used instead of directly
// calling the factory. A builder will help to not forget a parameter.
public class LeavesFactory
{
    public static final int DEFAULT_AT_HEIGHT = 0;
    public static final Coord3D DEFAULT_CORE_POSITION = new Coord3D();

    
    public static Leaves createNormalLeaves(
            LeavesSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        return new PersistentLeaves(
                spec,
                atHeight,
                treeCorePos);
    }
    
    
    // Used for deserialization.
    static Leaves createLeavesFromTypeId(String typeId)
    {
        if (typeId.equals(PersistentLeaves.TYPE_ID))
        {
            return createNormalLeaves(
                    new LeavesSpec(),
                    DEFAULT_AT_HEIGHT,
                    DEFAULT_CORE_POSITION);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
