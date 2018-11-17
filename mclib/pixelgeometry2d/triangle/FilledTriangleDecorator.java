package xmilcode.mclib.pixelgeometry2d.triangle;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShapeFiller;



public class FilledTriangleDecorator implements Triangle
{
    private Triangle decorated;
    private PixelShape filled;
    
    
    public FilledTriangleDecorator(Triangle decorated)
    {
        this.decorated = decorated;
        this.filled = fill(decorated);
    }
    
    @Override
    public int countPixels()
    {
        return filled.countPixels();
    }

    @Override
    public Set<Coord2D> pixels()
    {
        return filled.pixels();
    }

    @Override
    public boolean isHit(Coord2D pos)
    {
        return filled.isHit(pos);
    }

    @Override
    public Coord2D vertex(int idx)
    {
        return decorated.vertex(idx);
    }

    @Override
    public Coord2D centroid()
    {
        return decorated.centroid();
    }

    @Override
    public boolean isInside(Coord2D pos)
    {
        return decorated.isInside(pos);
    }
    
    private static PixelShape fill(Triangle triangle)
    {
        PixelShapeFiller filler = new PixelShapeFiller(triangle, triangle.centroid());
        return filler.fill();
    }
}
