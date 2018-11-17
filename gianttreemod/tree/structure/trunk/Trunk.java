package xmilcode.gianttreemod.tree.structure.trunk;

import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public interface Trunk extends GrowablePart
{
    public String typeId();
    public Coord3D position();
    public int radius();
    public int height();
    public boolean isHit(Coord3D pos);
    public boolean isTop(Coord3D pos);
}
