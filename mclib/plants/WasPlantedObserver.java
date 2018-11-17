package xmilcode.mclib.plants;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


// Can be implemented by plants that need a notification from their seeds
// that they were planted.
public interface WasPlantedObserver
{
    public void onWasPlanted(World world, Coord3D at);
}
