package xmilcode.mclib.pixelgeometry3d.cuboid;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


public class CuboidGeometry implements Cuboid
{
    private final Coord3D baseVertex;
    private final Vector3D size;

    
    public CuboidGeometry(Coord3D corner, Vector3D size)
    {
        this.baseVertex = calcBaseVertex(corner, size);
        this.size = normalizeSize(size);
    }

    
    public CuboidGeometry(Coord3D corner, Coord3D oppositeCorner)
    {
        this(corner, new Vector3D(corner, oppositeCorner));
    }
    
    
    private static Coord3D calcBaseVertex(Coord3D corner, Vector3D size)
    {
        return new Coord3D(
                Math.min(corner.x, corner.x + size.x),
                Math.min(corner.y, corner.y + size.y),
                Math.min(corner.z, corner.z + size.z));
    }
    
    
    private static Vector3D normalizeSize(Vector3D unnormedSize)
    {
        return new Vector3D(
                Math.abs(unnormedSize.x),
                Math.abs(unnormedSize.y),
                Math.abs(unnormedSize.z));
    }
    
    
    @Override
    public Coord3D baseVertex()
    {
        return baseVertex;
    }

    
    @Override
    public Coord3D oppsiteVertex()
    {
        return new Coord3D(
                baseVertex.x + size.x,
                baseVertex.y + size.y,
                baseVertex.z + size.z);
    }

    
    @Override
    public Coord3D center()
    {
        return new Coord3D(
                baseVertex.x + size.x/2,
                baseVertex.y + size.y/2,
                baseVertex.z + size.z/2);
    }

    
    @Override
    public Vector3D size()
    {
        return size;
    }

    
    @Override
    public boolean isHit(Coord3D pos)
    {
        return (baseVertex.x <= pos.x && pos.x < baseVertex.x + size.x &&
                baseVertex.y <= pos.y && pos.y < baseVertex.y + size.y &&
                baseVertex.z <= pos.z && pos.z < baseVertex.z + size.z);
    }
}
