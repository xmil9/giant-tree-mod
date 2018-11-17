package xmilcode.mclib.pixelgeometry3d.triangularprism;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry2d.triangle.CcwTriangle;
import xmilcode.mclib.pixelgeometry2d.triangle.FilledTriangleDecorator;
import xmilcode.mclib.pixelgeometry2d.triangle.Triangle;


public class CcwTriangularPrism extends BaseTriangularPrism
{
    public CcwTriangularPrism(
            Vector3D baseNormalAxis,
            Coord3D baseVertex1,
            Coord3D baseVertex2,
            Coord3D baseVertex3,
            int height)
    {
        super(baseNormalAxis,
                baseVertex1,
                baseVertex2,
                baseVertex3,
                height,
                generateBaseTriangle(baseNormalAxis, baseVertex1, baseVertex2, baseVertex3));
    }
    
    
    private static Triangle generateBaseTriangle(
            Vector3D baseNormalAxis,
            Coord3D baseVertex1,
            Coord3D baseVertex2,
            Coord3D baseVertex3)
    {
        return new FilledTriangleDecorator(
                new CcwTriangle(
                        CoordUtil3D.mapTo2DPlaneOrthagonalToVector(baseVertex1, baseNormalAxis),
                        CoordUtil3D.mapTo2DPlaneOrthagonalToVector(baseVertex2, baseNormalAxis),
                        CoordUtil3D.mapTo2DPlaneOrthagonalToVector(baseVertex3, baseNormalAxis))); 
    }
}
