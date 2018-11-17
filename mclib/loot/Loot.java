package xmilcode.mclib.loot;

import net.minecraft.world.World;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public interface Loot
{
    // Checks if loot is available. Convenient to have to optimize
    // calling code.
    boolean haveLoot();
    void placeAt(World world, Coord3D pos);
}
