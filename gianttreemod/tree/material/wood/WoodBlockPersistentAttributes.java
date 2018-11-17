package xmilcode.gianttreemod.tree.material.wood;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Access to attributes of a wood block that get stored by a tile entity.
public interface WoodBlockPersistentAttributes
{
    Coord3D treeCorePosition();
    void setTreeCorePosition(Coord3D corePos);
}
