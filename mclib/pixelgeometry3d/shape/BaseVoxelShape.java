package xmilcode.mclib.pixelgeometry3d.shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;



// Collection of voxels without any specified shape.
public class BaseVoxelShape implements VoxelShape
{
    private Set<Coord3D> voxels;
    
    
    public BaseVoxelShape()
    {
        this(new HashSet<Coord3D>());
    }
        
    public BaseVoxelShape(Set<Coord3D> voxels)
    {
        this.voxels = voxels;
    }
    
    public BaseVoxelShape(BaseVoxelShape src)
    {
        this(new HashSet<Coord3D>(src.voxels()));
    }
    
    @Override
    public int countVoxels()
    {
        return voxels.size();
    }

    @Override
    public Set<Coord3D> voxels()
    {
        return voxels;
    }

    @Override
    public boolean isHit(Coord3D pos)
    {
        return voxels.contains(pos);
    }
    
    // Do not make 'public' to prevent the shape from changing to
    // something that does not match its derived class' type.
    protected void setVoxels(Set<Coord3D> voxels)
    {
        this.voxels = voxels;
    }
}
