package xmilcode.mclib.pixelgeometry3d.cylinder;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.circle.Circle;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithoutCenterPixel;
import xmilcode.mclib.pixelgeometry2d.circle.FilledCircleDecorator;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class CylinderWithoutCenterVoxel extends BaseCylinder
{
    public CylinderWithoutCenterVoxel(
            Vector3D centerAxis,
            Coord3D baseCenter,
            int radius,
            int height)
    {
        super(centerAxis,
                baseCenter,
                height,
                generateBaseCircle(centerAxis, baseCenter, radius));
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
                new CircleWithoutCenterPixel(center2D, radius));
    }


    @Override
    public boolean hasCenterVoxel()
    {
        return false;
    }
}
