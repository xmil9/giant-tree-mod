package xmilcode.gianttreemod.tree.structure.branch;

import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public interface Branch extends GrowablePart
{
    public String typeId();
    public BranchSpec spec();
    public int atTreeHeight();
    public Coord3D treeCorePosition();
    public boolean isHit(Coord3D pos);
}
