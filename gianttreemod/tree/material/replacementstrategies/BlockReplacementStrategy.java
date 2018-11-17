package xmilcode.gianttreemod.tree.material.replacementstrategies;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.world.World;


// Interface for block replacement policies.
public interface BlockReplacementStrategy
{
    boolean canReplace(World world, Coord3D targetPos);
}
