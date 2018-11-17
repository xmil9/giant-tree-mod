package xmilcode.mclib.pixelgeometry3d;


// X axis goes "sideways".
// Y axis goes "up".
// Z axis goes "deep"
public enum Axis3D
{
    X(1, 0, 0),
    Y(0, 1, 0),
    Z(0, 0, 1);
    
    private int x;
    private int y;
    private int z;
    
    
    private Axis3D(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    public Coord3D mask(Coord3D val)
    {
        return new Coord3D(val.x * x, val.y * y, val.z * z);
    }

    
    public Coord3D mask(int val)
    {
        return new Coord3D(val * x, val * y, val * z);
    }
    
    
    public int extractComponent(Coord3D pos3D)
    {
        // Only the component for the axis will yield a non-zero value.
        return x * pos3D.x + y * pos3D.y + z * pos3D.z;
    }
    
    
    public Vector3D axisVector()
    {
        return new Vector3D(x, y, z);
    }
    
    
    public static boolean isAxisVector(Vector3D v)
    {
        int nonZeroCount = v.x == 0 ? 0 : 1;
        nonZeroCount += v.y == 0 ? 0 : 1;
        nonZeroCount += v.z == 0 ? 0 : 1;
        return nonZeroCount == 1;
    }
}
