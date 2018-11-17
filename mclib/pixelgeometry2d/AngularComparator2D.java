package xmilcode.mclib.pixelgeometry2d;

import java.util.Comparator;


// Compares 2D coordinates by their angle from a common origin.
public class AngularComparator2D implements Comparator<Coord2D>
{
    private Coord2D origin;
    
    public AngularComparator2D(Coord2D origin)
    {
        this.origin = origin;
    }
    
    @Override // Comparator<>
    public int compare(Coord2D a, Coord2D b)
    {
        double angleA = CoordUtil2D.calcAngle(origin, a);
        double angleB = CoordUtil2D.calcAngle(origin, b);
        
        if (angleA < angleB)
            return -1;
        else if (angleA > angleB)
            return 1;
        else
            return 0; 
    }
}
