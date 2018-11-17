package xmilcode.gianttreemod.tree.structure.tip;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public interface TreeTip extends GrowablePart
{
    public String typeId();
    public Coord3D baseCenter();
    public int baseRadius();
    public int height();
    public Coord3D treeCorePosition();
    public boolean isHit(Coord3D pos);
}
