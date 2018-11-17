package xmilcode.mclib.pixelgeometry2d;

public enum Neighbor2D
{
    LEFT(-1, 0),
    RIGHT(1, 0),
    TOP(0, 1),
    BOTTOM(0, -1),
    TOP_LEFT(1, 1),
    TOP_RIGHT(1, -1),
    BOTTOM_LEFT(-1, 1),
    BOTTOM_RIGHT(-1, -1),
    NONE(0, 0);
    
    private int dx;
    private int dy;
    
    private Neighbor2D(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
    
    public static Coord2D getPositionOfNeighbor(Coord2D pos, Neighbor2D neighbor)
    {
        return new Coord2D(
                pos.x + neighbor.dx,
                pos.y + neighbor.dy);
    }
}
