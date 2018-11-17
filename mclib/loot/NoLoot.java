package xmilcode.mclib.loot;

import net.minecraft.world.World;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class NoLoot implements Loot
{
    @Override
    public boolean haveLoot()
    {
        return false;
    }

    @Override
    public void placeAt(World world, Coord3D pos)
    {
    }
}
