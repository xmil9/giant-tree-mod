package xmilcode.mclib.pixelgeometry3d.cuboid;

import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;



public interface Cuboid
{
    // The base vertex is the vertex where all dimensions have
    // their lowest values.
    public Coord3D baseVertex();
    // The opposite vertex to the the base vertex, i.e where all
    // dimensions have their highest values.
    // Note: Re-calculated for each call! 
    public Coord3D oppsiteVertex();
    public Coord3D center();
    public Vector3D size();
    public boolean isHit(Coord3D pos);
}
