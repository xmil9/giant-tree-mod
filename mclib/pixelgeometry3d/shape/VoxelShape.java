package xmilcode.mclib.pixelgeometry3d.shape;

import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Collection of voxels.
public interface VoxelShape
{
    public int countVoxels();
    public Set<Coord3D> voxels();
    // Is the given position one of the shape's voxels?
    public boolean isHit(Coord3D pos);
}
