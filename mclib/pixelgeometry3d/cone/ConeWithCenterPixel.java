package xmilcode.mclib.pixelgeometry3d.cone;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.circle.Circle;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithCenterPixel;
import xmilcode.mclib.pixelgeometry2d.circle.FilledCircleDecorator;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class ConeWithCenterPixel extends BaseCone
{
    public ConeWithCenterPixel(
            Vector3D centerAxis,
            Coord3D baseCenter,
            int baseRadius,
            int height)
    {
        super(centerAxis,
                baseCenter,
                height,
                generateBaseCircle(centerAxis, baseCenter, baseRadius));
    }


    private static Circle generateBaseCircle(
            Vector3D centerAxis,
            Coord3D baseCenter,
            int radius)
    {
        Coord2D center2D = CoordUtil3D.mapTo2DPlaneOrthagonalToVector(
                baseCenter,
                centerAxis);

        return new FilledCircleDecorator(
                new CircleWithCenterPixel(center2D, radius));
    }


    @Override
    public boolean hasCenterVoxel()
    {
        return true;
    }
}
