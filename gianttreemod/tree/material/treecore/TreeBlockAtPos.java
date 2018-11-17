package xmilcode.gianttreemod.tree.material.treecore;

import xmilcode.gianttreemod.tree.growth.GrowingBlock;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


// Interface that block classes have to implement to provide
// context information to growable parts.
public interface TreeBlockAtPos extends GrowingBlock
{
    Coord3D position();
    World world();
}
