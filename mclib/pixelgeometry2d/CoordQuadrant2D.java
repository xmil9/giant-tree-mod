package xmilcode.mclib.pixelgeometry2d;


// The four quadrant of a coord system.
public enum CoordQuadrant2D
{
    XPOS_YPOS(1, 1),
    XPOS_YNEG(1, -1),
    XNEG_YPOS(1, -1),
    XNEG_YNEG(-1, -1);
    
    
    private int xSign;
    private int ySign;
    
    
    private CoordQuadrant2D(int xSign, int ySign)
    {
        this.xSign = xSign;
        this.ySign = ySign;
    }
    
    public int xSign()
    {
        return xSign;
    }
    
    public int ySign()
    {
        return ySign;
    }
}
