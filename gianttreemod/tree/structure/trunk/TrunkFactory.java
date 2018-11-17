package xmilcode.gianttreemod.tree.structure.trunk;

import java.security.InvalidParameterException;

import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Factory for trunk objects.
// Note that if the parameter collection to completely create trunk objects
// is challenging, a builder object should be used instead of directly
// calling the factory. A builder will help to not forget a parameter.
public class TrunkFactory
{
    public static Trunk createNormalTrunk(TrunkSpec spec)
    {
        return new PersistentTrunk(spec);
    }
    
    
    // Used for deserialization.
    static Trunk createTrunkFromTypeId(String typeId)
    {
        if (typeId.equals(PersistentTrunk.TYPE_ID))
        {
            return createNormalTrunk(new TrunkSpec());
        }
        else
        {
            throw new InvalidParameterException();
        }
    }
    
}
