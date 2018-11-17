package xmilcode.gianttreemod.tree.structure.stairs;

import java.security.InvalidParameterException;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Factory for stairs objects.
// Note that if the parameter collection to completely create stairs objects
// is challenging, a builder object should be used instead of directly
// calling the factory. A builder will help to not forget a parameter.
public class StairsFactory
{
    public static Stairs createNormalStairs(StairsSpec spec)
    {
        return new PersistentStairs(spec);
    }
    
    
    // Used for deserialization.
    static Stairs createStairsFromTypeId(String typeId)
    {
        if (typeId.equals(PersistentStairs.TYPE_ID))
        {
            return createNormalStairs(new StairsSpec());
        }
        else
        {
            throw new InvalidParameterException();
        }
    }
}
