package xmilcode.mclib.pixelgeometry2d.line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.Coord2D;


public class LinePixelGenerator
{
    private Coord2D pt1;
    private Coord2D pt2;
    private Set<Coord2D> linePixels;
    
    
    public LinePixelGenerator(Coord2D pt1, Coord2D pt2)
    {
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.linePixels = new HashSet<Coord2D>();
    }
    
    
    public Set<Coord2D> generatePixels()
    {
        linePixels.clear();
        calcLinePixels(pt1.x, pt1.y, pt2.x, pt2.y);
        return linePixels;
    }
    
    
    // Bresenham algorithm.
    // Source: http://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#C
    private void calcLinePixels(int x1, int y1, int x2, int y2)
    {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int incx = x1 < x2 ? 1 : -1;
        int incy = y1 < y2 ? 1 : -1; 
        int err = (dx > dy ? dx : -dy) / 2;
       
        for(;;)
        {
          setPixel(x1, y1);
          if (x1 == x2 && y1 == y2)
              break;
          
          int errSaved = err;
          if (errSaved > -dx)
          {
              err -= dy;
              x1 += incx;
          }
          
          if (errSaved < dy)
          {
              err += dx;
              y1 += incy;
          }
        }
    }
    
    
    private void setPixel(int x, int y)
    {
        linePixels.add(new Coord2D(x, y));
    }
}
