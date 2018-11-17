package xmilcode.mclib.util;

import net.minecraft.world.World;


public class WorldUtil
{
    public static boolean isServerSide(World world)
    {
        return !world.isRemote;
    }


    public static boolean isClientSide(World world)
    {
        return !isServerSide(world);
    }
}
