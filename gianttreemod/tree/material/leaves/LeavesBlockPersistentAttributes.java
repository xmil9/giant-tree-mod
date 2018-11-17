package xmilcode.gianttreemod.tree.material.leaves;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


//Access to attributes of a wood block that get stored by a tile entity.
public interface LeavesBlockPersistentAttributes
{
    Coord3D treeCorePosition();
    void setTreeCorePosition(Coord3D corePos);
    boolean canGrow();
    void markAsDoneGrowing();
}
