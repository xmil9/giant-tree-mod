package xmilcode.mclib.pixelgeometry3d.triangularprism;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;



public interface TriangularPrism extends VoxelShape
{
    public Coord3D baseVertex(int idx);
    public int height();
    public Vector3D baseNormalAxis();
}
