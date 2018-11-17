package xmilcode.mclib.pixelgeometry3d.cylinder;

import java.util.List;

import xmilcode.mclib.pixelgeometry2d.LinearDirection;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;


public interface Cylinder extends VoxelShape
{
    public Coord3D baseCenter();
    public int radius();
    public int height();
    public Vector3D centerAxis();
    public boolean hasCenterVoxel();
}
