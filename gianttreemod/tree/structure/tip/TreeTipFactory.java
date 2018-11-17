package xmilcode.gianttreemod.tree.structure.tip;

import java.security.InvalidParameterException;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class TreeTipFactory
{
    public static final Coord3D DEFAULT_CORE_POSITION = new Coord3D();

    
    public static TreeTip createNormalTip(TreeTipSpec spec, Coord3D treeCorePos)
    {
        return new PersistentTreeTip(spec, treeCorePos);
    }
    
    
    // Used for deserialization.
    static TreeTip createTipFromTypeId(String typeId)
    {
        if (typeId.equals(PersistentTreeTip.TYPE_ID))
        {
            return createNormalTip(new TreeTipSpec(), DEFAULT_CORE_POSITION);
        }
        else
        {
            throw new InvalidParameterException();
        }
    }
}
