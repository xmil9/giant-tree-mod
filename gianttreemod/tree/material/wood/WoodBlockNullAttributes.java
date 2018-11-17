package xmilcode.gianttreemod.tree.material.wood;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Neighbor3D;
import net.minecraft.nbt.NBTTagCompound;




// Null object pattern for a wood block's persistent attributes.
public class WoodBlockNullAttributes implements WoodBlockPersistentAttributes 
{
    @Override
    public Coord3D treeCorePosition()
    {
        // Have to return null because any other coordinate would by chance
        // belong to a tree core block (unlikely as it would be!). 
        return null;
    }

    @Override
    public void setTreeCorePosition(Coord3D corePos)
    {
    }
}
