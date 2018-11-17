package xmilcode.gianttreemod.tree.structure.leaves;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public interface Leaves extends GrowablePart
{
    public String typeId();
    public Vector3D branchAxis();
    public Coord3D position();
    public int length();
    public int width();
    public int height();
    public int atTreeHeight();
    public Coord3D treeCorePosition();
    public boolean isHit(Coord3D pos);
}
