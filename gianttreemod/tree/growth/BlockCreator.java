package xmilcode.gianttreemod.tree.growth;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


// Interface to be provided by growable parts. It allows clients to
// notify the growable part to create a block at a given position.
public interface BlockCreator
{
    // If possible creates a block of this part only at a given position. 
    public boolean createOwnBlockAt(World world, Coord3D at);
    // If possible creates a block of any part at a given position. 
    public boolean createAnyBlockAt(World world, Coord3D at);
}
