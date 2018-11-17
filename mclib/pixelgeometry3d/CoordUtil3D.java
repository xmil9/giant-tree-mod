package xmilcode.mclib.pixelgeometry3d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;
import xmilcode.mclib.pixelgeometry3d.shape.BaseVoxelShape;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;


public class CoordUtil3D
{
    // Map 3D to 2D.
    
    public static Coord2D mapTo2DYZPlane(Coord3D pos3D)
    {
        return new Coord2D(pos3D.z, pos3D.y);
    }
    
    
    public static Coord2D mapTo2DXZPlane(Coord3D pos3D)
    {
        return new Coord2D(pos3D.x, pos3D.z);
    }
    
    
    public static Coord2D mapTo2DXYPlane(Coord3D pos3D)
    {
        return new Coord2D(pos3D.x, pos3D.y);
    }
    
    
    public static Coord2D mapTo2DPlaneOrthagonalToVector(
            Coord3D pos3D,
            Vector3D planeNormal)
    {
        if (!Axis3D.isAxisVector(planeNormal))
            throw new IllegalArgumentException();
        
        if (planeNormal.x != 0)
        {
            return mapTo2DYZPlane(pos3D);
        }
        else if (planeNormal.y != 0)
        {
            return mapTo2DXZPlane(pos3D);
        }
        else
        {
            return mapTo2DXYPlane(pos3D);
        }
    }
    
    
    public static Set<Coord2D> mapTo2DPlaneOrthagonalToVector(
            Set<Coord3D> voxels,
            Vector3D planeNormal)
    {
        Set<Coord2D> pixels = new HashSet<Coord2D>();
        for (Coord3D voxel : voxels)
            pixels.add(mapTo2DPlaneOrthagonalToVector(voxel, planeNormal));
        return pixels;
    }
    
    
    public static PixelShape mapTo2DPlaneOrthagonalToVector(
            VoxelShape voxelShape,
            Vector3D planeNormal)
    {
        return new BasePixelShape(
                mapTo2DPlaneOrthagonalToVector(voxelShape.voxels(), planeNormal));
    }
    
    
    // Map 2D to 3D.
    
    public static Coord3D mapTo3DYZPlane(Coord2D pos2D, int x)
    {
        return new Coord3D(x, pos2D.y, pos2D.x);
    }
    
    
    public static Coord3D mapTo3DXZPlane(Coord2D pos2D, int y)
    {
        return new Coord3D(pos2D.x, y, pos2D.y);
    }
    
    
    public static Coord3D mapTo3DXYPlane(Coord2D pos2D, int z)
    {
        return new Coord3D(pos2D.x, pos2D.y, z);
    }

    
    public static Coord3D mapTo3DPlaneOrthagonalToVector(
            Coord2D pos2D,
            int planeCoord,
            Vector3D planeNormal)
    {
        if (!Axis3D.isAxisVector(planeNormal))
            throw new IllegalArgumentException();
        
        if (planeNormal.x != 0)
        {
            return mapTo3DYZPlane(pos2D, planeCoord);
        }
        else if (planeNormal.y != 0)
        {
            return mapTo3DXZPlane(pos2D, planeCoord);
        }
        else
        {
            return mapTo3DXYPlane(pos2D, planeCoord);
        }
    }
    
    
    public static Set<Coord3D> mapTo3DPlaneOrthagonalToVector(
            Set<Coord2D> pixels,
            int planeCoord,
            Vector3D planeNormal)
    {
        Set<Coord3D> voxels = new HashSet<Coord3D>();
        for (Coord2D pixel : pixels)
            voxels.add(mapTo3DPlaneOrthagonalToVector(pixel, planeCoord, planeNormal));
        return voxels;
    }
    
    
    public static VoxelShape mapTo3DPlaneOrthagonalToVector(
            PixelShape pixelShape,
            int planeCoord,
            Vector3D planeNormal)
    {
        return new BaseVoxelShape(
                mapTo3DPlaneOrthagonalToVector(pixelShape.pixels(), planeCoord, planeNormal));
    }
}
