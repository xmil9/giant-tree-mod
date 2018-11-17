package xmilcode.mclib.pixelgeometry2d.triangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.line.Line2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShapeFiller;


public class TrianglePixelGenerator
{
    private Coord2D vertices[];
    
    
    public TrianglePixelGenerator(
            Coord2D v1,
            Coord2D v2,
            Coord2D v3)
    {
        vertices = new Coord2D[3];
        vertices[0] = v1;
        vertices[1] = v2;
        vertices[2] = v3;
    }
    
    
    public Set<Coord2D> generateOutlinePixels()
    {
        HashSet<Coord2D> trianglePixels = new HashSet<Coord2D>();
        
        Line2D edges[] = new Line2D[3];
        edges[0] = new Line2D(vertices[0], vertices[1]);
        edges[1] = new Line2D(vertices[1], vertices[2]);
        edges[2] = new Line2D(vertices[2], vertices[0]);
        
        for (int i = 0; i < 3; ++i)
        {
            for (Coord2D pixel : edges[i].pixels())
                trianglePixels.add(pixel);
        }
        
        return trianglePixels;
    }


    public Set<Coord2D> generateFillPixels()
    {
        BasePixelShape triangleOutline = new BasePixelShape(generateOutlinePixels());
        Coord2D centroid = TriangleUtil.calcCentroid(vertices[0], vertices[1], vertices[2]);
        
        PixelShapeFiller filler = new PixelShapeFiller(triangleOutline, centroid, false);
        return filler.fill().pixels();
        
    }
}
