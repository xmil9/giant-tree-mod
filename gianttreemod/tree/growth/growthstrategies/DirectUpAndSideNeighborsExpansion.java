package xmilcode.gianttreemod.tree.growth.growthstrategies;

import java.util.HashMap;
import java.util.Map;

import xmilcode.mclib.pixelgeometry3d.Neighbor3D;


// Expands to the blocks that are direct top, left, right, front and back
// neighbors of the source block.
// Downward expansion is skipped.
public class DirectUpAndSideNeighborsExpansion extends DirectionalExpansionBase
{
    private static Map<Neighbor3D, Neighbor3D> transitionTable =
            buildTransitionTable();
    
    
    private static Map<Neighbor3D, Neighbor3D> buildTransitionTable()
    {
        HashMap<Neighbor3D, Neighbor3D> table =
                new HashMap<Neighbor3D, Neighbor3D>();
        
        for (Neighbor3D value : Neighbor3D.values())
            table.put(value, Neighbor3D.NONE);
        
        table.put(Neighbor3D.FRONT, Neighbor3D.RIGHT);
        table.put(Neighbor3D.RIGHT, Neighbor3D.BACK);
        table.put(Neighbor3D.BACK, Neighbor3D.LEFT);
        table.put(Neighbor3D.LEFT, Neighbor3D.UP);

        return table;
    }
    
    
    @Override
    protected Neighbor3D transit(Neighbor3D from)
    {
        return transitionTable.get(from);
    }
}
