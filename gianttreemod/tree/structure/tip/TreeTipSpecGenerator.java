package xmilcode.gianttreemod.tree.structure.tip;

import xmilcode.gianttreemod.tree.structure.TreeSpec;
import xmilcode.mclib.pixelgeometry3d.Coord3D;



public class TreeTipSpecGenerator
{
    public static TreeTipSpec generateFromTreeSpec(TreeSpec treeSpec)
    {
        return new TreeTipSpec(
                calcBaseCenterFromTreeSpec(treeSpec),
                2 * treeSpec.trunkRadius(),
                treeSpec.fullHeight() - treeSpec.trunkHeight());
    }
    
    
    private static Coord3D calcBaseCenterFromTreeSpec(TreeSpec treeSpec)
    {
        return new Coord3D(
                treeSpec.position().x,
                treeSpec.position().y + treeSpec.trunkHeight(),
                treeSpec.position().z);
    }
}
