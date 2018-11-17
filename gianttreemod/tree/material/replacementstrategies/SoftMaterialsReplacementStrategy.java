package xmilcode.gianttreemod.tree.material.replacementstrategies;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;


public class SoftMaterialsReplacementStrategy implements BlockReplacementStrategy
{
    @Override
    public boolean canReplace(World world, Coord3D targetPos)
    {
        Block targetBlock = world.getBlock(targetPos.x, targetPos.y, targetPos.z);
        Material material = targetBlock.getMaterial();
        return (material == Material.air ||
                material == Material.snow ||
                material == Material.water ||
                material == Material.plants ||
                material == Material.leaves ||
                material == Material.cactus ||
                material == Material.coral ||
                material == Material.fire ||
                material == Material.grass ||
                material == Material.ice ||
                material == Material.sponge ||
                material == Material.vine ||
                material == Material.web);
    }
}
