package xmilcode.mclib.pixelgeometry2d.triangle;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.Vector2D;
import xmilcode.mclib.pixelgeometry2d.VectorUtil2D;


public class ClockwiseTriangle extends AbstractBaseTriangle
{
    public ClockwiseTriangle(Coord2D v1, Coord2D v2, Coord2D v3)
    {
        super(v1, v2, v3);
    }


    @Override
    protected boolean isPointOnInsideOfEdge(Coord2D pos, int edgeIdx)
    {
        Vector2D vecToPoint = new Vector2D(vertices[edgeIdx], pos);
        return VectorUtil2D.dotProduct(normals[edgeIdx], vecToPoint) >= 0;
    }
}
