package xmilcode.mclib.pixelgeometry3d.cuboid;

import java.util.HashSet;
import java.util.Set;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;


public class CuboidShape extends BaseVoxelShape implements Cuboid
{
    private final CuboidGeometry geometry;
    // Indicates that all voxels have been calculated and added to the
    // 3D shape.
    private boolean haveAllVoxels;
    
    
    public CuboidShape(Coord3D vertex, Vector3D size)
    {
        this.geometry = new CuboidGeometry(vertex, size);
        this.haveAllVoxels = false;
    }


    public CuboidShape(Coord3D vertex, Coord3D oppositeVertex)
    {
        this.geometry = new CuboidGeometry(vertex, oppositeVertex);
        this.haveAllVoxels = false;
    }


    @Override
    public Coord3D baseVertex()
    {
        return geometry.baseVertex();
    }


    @Override
    public Coord3D oppsiteVertex()
    {
        return geometry.oppsiteVertex();
    }


    @Override
    public Coord3D center()
    {
        return geometry.center();
    }


    @Override
    public Vector3D size()
    {
        return geometry.size();
    }

    
    @Override
    public int countVoxels()
    {
        return size().x * size().y * size().z;
    }

    
    @Override
    public Set<Coord3D> voxels()
    {
        // Lazy initialization of voxels because there could be many!
        if (!haveAllVoxels)
            addAllVoxelsToShape();
        return super.voxels();
    }

    
    private void addAllVoxelsToShape()
    {
        Set<Coord3D> allVoxels = new HashSet<Coord3D>();
        for (int x = baseVertex().x; x < baseVertex().x + size().x; ++x)
            for (int y = baseVertex().y; y < baseVertex().y + size().y; ++y)
                for (int z = baseVertex().z; z < baseVertex().z + size().z; ++z)
                    allVoxels.add(new Coord3D(x, y, z));
        setVoxels(allVoxels);
        haveAllVoxels = true;
    }
    

    @Override
    public boolean isHit(Coord3D pos)
    {
        return geometry.isHit(pos);
    }
}
