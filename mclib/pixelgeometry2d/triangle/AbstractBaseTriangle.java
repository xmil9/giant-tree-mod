package xmilcode.mclib.pixelgeometry2d.triangle;

import java.security.InvalidParameterException;
import java.util.List;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.Vector2D;
import xmilcode.mclib.pixelgeometry2d.VectorUtil2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;


public abstract class AbstractBaseTriangle extends BasePixelShape implements Triangle
{
    protected static final int NUM_VERTICES = 3;
    
    protected Coord2D[] vertices;
    protected Vector2D[] normals;
    
    
    public AbstractBaseTriangle(Coord2D v1, Coord2D v2, Coord2D v3)
    {
        super(new TrianglePixelGenerator(v1, v2, v3).generateOutlinePixels());
        vertices = new Coord2D[3];
        vertices[0] = v1;
        vertices[1] = v2;
        vertices[2] = v3;
        normals = calcNormals(vertices);
    }

    
    private static Vector2D[] calcNormals(Coord2D vertices[])
    {
        Vector2D normals[] = new Vector2D[3];
        normals[0] = VectorUtil2D.calcNormal(vertices[0], vertices[1]);
        normals[1] = VectorUtil2D.calcNormal(vertices[1], vertices[2]);
        normals[2] = VectorUtil2D.calcNormal(vertices[2], vertices[0]);
        return normals;
    }
    

    public AbstractBaseTriangle(List<Coord2D> vertices)
    {
        // Size of passed list is unchecked.
        this(vertices.get(0), vertices.get(1), vertices.get(2));
    }
    
    
    @Override
    public Coord2D vertex(int idx)
    {
        // Passed index is unchecked.
        return vertices[idx];
    }
    
    
    @Override
    public Coord2D centroid()
    {
        return TriangleUtil.calcCentroid(vertices[0], vertices[1], vertices[2]);
    }

    
    // Checks if a point is inside the triangle.
    // Source: 'Geometric Tools for Computer Graphics' pg.695 
    @Override
    public boolean isInside(Coord2D pos)
    {
        for (int i = 0; i < NUM_VERTICES; ++i)
        {
            if (!isPointOnInsideOfEdge(pos, i))
                return false;
        }
        
        return true;
    }
    
    
    // Checks if a point is on the inside side of triangle's edge. 
    protected abstract boolean isPointOnInsideOfEdge(Coord2D pos, int edgeIdx);
}
