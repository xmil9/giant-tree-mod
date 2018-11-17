package xmilcode.gianttreemod.tree.material.replacementstrategies;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import xmilcode.mclib.pixelgeometry3d.Coord3D;



public class AirLikeReplacementStrategy implements BlockReplacementStrategy
{
    @Override
    public boolean canReplace(World world, Coord3D targetPos)
    {
        Block targetBlock = world.getBlock(targetPos.x, targetPos.y, targetPos.z);
        Material material = targetBlock.getMaterial();
        return (material == Material.air ||
                material == Material.snow ||
                material == Material.water ||
                material == Material.fire ||
                material == Material.ice ||
                material == Material.web);
    }
}
