package xmilcode.gianttreemod.tree.growth.growthstrategies;

import java.util.HashMap;
import java.util.Map;

import xmilcode.mclib.pixelgeometry3d.Neighbor3D;



// Expands to all neighbors of the source block but not below it.
public class AllUpAndSideNeighborsExpansion extends DirectionalExpansionBase
{
    private static Map<Neighbor3D, Neighbor3D> transitionTable =
            buildTransitionTable();
    
    
    private static Map<Neighbor3D, Neighbor3D> buildTransitionTable()
    {
        HashMap<Neighbor3D, Neighbor3D> table =
                new HashMap<Neighbor3D, Neighbor3D>();
        
        for (Neighbor3D value : Neighbor3D.values())
            table.put(value, Neighbor3D.NONE);
        
        table.put(Neighbor3D.FRONT, Neighbor3D.UPPER_FRONT);
        table.put(Neighbor3D.UPPER_FRONT, Neighbor3D.UPPER_RIGHT_FRONT);
        table.put(Neighbor3D.UPPER_RIGHT_FRONT, Neighbor3D.RIGHT_FRONT);
        table.put(Neighbor3D.RIGHT_FRONT, Neighbor3D.LEFT_FRONT);
        table.put(Neighbor3D.LEFT_FRONT, Neighbor3D.UPPER_LEFT_FRONT);
        table.put(Neighbor3D.UPPER_LEFT_FRONT, Neighbor3D.BACK);
        table.put(Neighbor3D.BACK, Neighbor3D.UPPER_BACK);
        table.put(Neighbor3D.UPPER_BACK, Neighbor3D.UPPER_RIGHT_BACK);
        table.put(Neighbor3D.UPPER_RIGHT_BACK, Neighbor3D.RIGHT_BACK);
        table.put(Neighbor3D.RIGHT_BACK, Neighbor3D.LEFT_BACK);
        table.put(Neighbor3D.LEFT_BACK, Neighbor3D.UPPER_LEFT_BACK);
        table.put(Neighbor3D.UPPER_LEFT_BACK, Neighbor3D.RIGHT);
        table.put(Neighbor3D.RIGHT, Neighbor3D.UPPER_RIGHT);
        table.put(Neighbor3D.UPPER_RIGHT, Neighbor3D.LEFT);
        table.put(Neighbor3D.LEFT, Neighbor3D.UPPER_LEFT);
        table.put(Neighbor3D.UPPER_LEFT, Neighbor3D.UP);

        return table;
    }
    
    
    @Override
    protected Neighbor3D transit(Neighbor3D from)
    {
        return transitionTable.get(from);
    }
}
