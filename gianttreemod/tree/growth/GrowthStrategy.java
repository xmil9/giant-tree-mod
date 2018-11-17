package xmilcode.gianttreemod.tree.growth;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


// Main interface for growth strategies.
public interface GrowthStrategy
{
    public boolean growAt(
            World world,
            Coord3D pos,
            BlockCreator growingPart,
            GrowingBlock block);
}
