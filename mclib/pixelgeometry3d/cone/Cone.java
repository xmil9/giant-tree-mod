package xmilcode.mclib.pixelgeometry3d.cone;

import xmilcode.mclib.pixelgeometry2d.LinearDirection;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;


public interface Cone extends VoxelShape
{
    public Coord3D baseCenter();
    public int radius();
    public int height();
    public Vector3D centerAxis();
    public boolean hasCenterVoxel();
}
