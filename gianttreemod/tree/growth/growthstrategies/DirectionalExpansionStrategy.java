package xmilcode.gianttreemod.tree.growth.growthstrategies;

import java.util.Iterator;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public interface DirectionalExpansionStrategy extends Iterator<Coord3D>
{
    // Reset the expansion sequence to its initial state.
    public void reset(Coord3D sourcePos);
}
