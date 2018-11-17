package xmilcode.mclib.pixelgeometry2d;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


// Iterates the neighboring pixels of a given position in a given order.
public class NeighborPixelIterator implements Iterator<Coord2D>
{
    private Coord2D sourcePos;
    private List<Neighbor2D> order;
    private int currentIdx;
    
    public NeighborPixelIterator(Coord2D pos, List<Neighbor2D> order)
    {
        this.sourcePos = pos;
        this.order = order;
        this.currentIdx = 0;
    }
    
    @Override // Iterator<>
    public boolean hasNext()
    {
        return currentIdx < order.size();
    }

    @Override // Iterator<>
    public Coord2D next()
    {
        if (!hasNext())
            throw new NoSuchElementException();
        return Neighbor2D.getPositionOfNeighbor(sourcePos, order.get(currentIdx++));
    }

    @Override // Iterator<>
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
    
    public void reset()
    {
        currentIdx = 0;
    }
    
    public void resetForPixel(Coord2D pos)
    {
        sourcePos = pos;
        reset();
    }
}
