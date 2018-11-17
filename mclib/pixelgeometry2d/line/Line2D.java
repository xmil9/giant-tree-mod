package xmilcode.mclib.pixelgeometry2d.line;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;


public class Line2D extends BasePixelShape
{
    private Coord2D start;
    private Coord2D end;
    
    public Line2D(Coord2D from, Coord2D to)
    {
        super(new LinePixelGenerator(from, to).generatePixels());
        this.start = from;
        this.end = to;
    }
    
    public Coord2D startPoint()
    {
        return start;
    }

    public Coord2D endPoint()
    {
        return end;
    }
}
