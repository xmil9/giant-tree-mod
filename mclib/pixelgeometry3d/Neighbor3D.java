package xmilcode.mclib.pixelgeometry3d;




public enum Neighbor3D
{
    LEFT(-1, 0, 0),
    RIGHT(1, 0, 0),
    FRONT(0, 0, -1),
    BACK(0, 0, 1),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    UPPER_FRONT(0, 1, -1),
    UPPER_BACK(0, 1, 1),
    LOWER_FRONT(0, -1, -1),
    LOWER_BACK(0, -1, 1),
    UPPER_LEFT(-1, 1, 0),
    UPPER_RIGHT(1, 1, 0),
    LOWER_LEFT(-1, -1, 0),
    LOWER_RIGHT(1, -1, 0),
    LEFT_FRONT(-1, 0, -1),
    RIGHT_FRONT(1, 0, -1),
    LEFT_BACK(-1, 0, 1),
    RIGHT_BACK(1, 0, 1),
    UPPER_LEFT_FRONT(-1, 1, -1),
    UPPER_LEFT_BACK(-1, 1, 1),
    UPPER_RIGHT_FRONT(1, 1, -1),
    UPPER_RIGHT_BACK(1, 1, 1),
    LOWER_LEFT_FRONT(-1, -1, -1),
    LOWER_LEFT_BACK(-1, -1, 1),
    LOWER_RIGHT_FRONT(1, -1, -1),
    LOWER_RIGHT_BACK(1, -1, 1),
    NONE(0, 0, 0);
    
    
    private int dx;
    private int dy;
    private int dz;
    
    
    private Neighbor3D(int dx, int dy, int dz)
    {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
    
    
    public static Coord3D getPositionOfNeighbor(Coord3D pos, Neighbor3D neighbor)
    {
        return new Coord3D(
                pos.x + neighbor.dx,
                pos.y + neighbor.dy,
                pos.z + neighbor.dz);
    }
}
