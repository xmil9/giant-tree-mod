package xmilcode.mclib.pixelgeometry2d;

import java.util.ArrayList;
import java.util.List;


public class DirectNeighborPixelIterator extends NeighborPixelIterator
{
    private static final List<Neighbor2D> ORDER = createOrder();
    
    private static List<Neighbor2D> createOrder()
    {
        ArrayList<Neighbor2D> order = new ArrayList<Neighbor2D>();
        order.add(Neighbor2D.TOP);
        order.add(Neighbor2D.RIGHT);
        order.add(Neighbor2D.BOTTOM);
        order.add(Neighbor2D.LEFT);
        return order;
    }
    
    public DirectNeighborPixelIterator(Coord2D pos)
    {
        super(pos, ORDER);
    }
}
