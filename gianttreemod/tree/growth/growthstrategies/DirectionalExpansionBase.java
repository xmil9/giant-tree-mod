package xmilcode.gianttreemod.tree.growth.growthstrategies;

import java.util.NoSuchElementException;
import java.util.Random;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Neighbor3D;
import net.minecraft.world.World;


// Calculate a position to grow into by iterating through a set of
// directions.
public abstract class DirectionalExpansionBase implements DirectionalExpansionStrategy
{
    private Coord3D sourcePos = new Coord3D();
    private Neighbor3D currentDirection = Neighbor3D.FRONT;
    
    
    @Override
    public void reset(Coord3D sourcePos)
    {
        this.sourcePos = sourcePos;
        this.currentDirection = Neighbor3D.FRONT;
    }

    
    @Override
    public boolean hasNext()
    {
        return currentDirection != Neighbor3D.NONE;
    }

    
    @Override
    public Coord3D next()
    {
        if (!hasNext())
            throw new NoSuchElementException();
        
        Neighbor3D thisDirection = currentDirection;
        currentDirection = transit(currentDirection);
        
        return Neighbor3D.getPositionOfNeighbor(sourcePos, thisDirection);
    }

    
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }


    // Transitions the direction state.
    protected abstract Neighbor3D transit(Neighbor3D from);
}
