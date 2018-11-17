package xmilcode.mclib.pixelgeometry2d;

import java.util.ArrayList;
import java.util.List;


public class AllNeighborPixelIterator extends NeighborPixelIterator 
{
    private static final List<Neighbor2D> ORDER = createOrder();
    
    private static List<Neighbor2D> createOrder()
    {
        ArrayList<Neighbor2D> order = new ArrayList<Neighbor2D>();
        order.add(Neighbor2D.TOP);
        order.add(Neighbor2D.TOP_RIGHT);
        order.add(Neighbor2D.RIGHT);
        order.add(Neighbor2D.BOTTOM_RIGHT);
        order.add(Neighbor2D.BOTTOM);
        order.add(Neighbor2D.BOTTOM_LEFT);
        order.add(Neighbor2D.LEFT);
        order.add(Neighbor2D.TOP_LEFT);
        return order;
    }
    
    public AllNeighborPixelIterator(Coord2D pos)
    {
        super(pos, ORDER);
    }
}
