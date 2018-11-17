package xmilcode.gianttreemod.tree.structure.stairs;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public interface Stairs extends GrowablePart
{
    public String typeId();
    public int height();
    public boolean isHit(Coord3D pos);
}
