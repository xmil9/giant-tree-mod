package xmilcode.mclib.pixelgeometry2d.shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import xmilcode.mclib.pixelgeometry2d.AllNeighborPixelIterator;
import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.DirectNeighborPixelIterator;
import xmilcode.mclib.pixelgeometry2d.NeighborPixelIterator;



// Returns a collection of pixels that represent a given shape with its
// inside area filled.
// Needs to be given a point that defines where the inside of the shape is.
// This inner point cannot be part of the shape, it has to be truly inside.
// Assumes the shape's path is closed.
// If the border of the shape is not solid (i.e. inside and outside pixels
// of the shape touch each other diagonally), the fill algorithm has to use
// a conservative fill strategy and will not fill inside pixel that can only
// be reached diagonally from other inside pixels.
// Examples: (X=border pixel, I=inside pixel, O=outside pixel)
// Solid border:    IXXO            Not solid border:    IXO
//                   IXXO                                 IXO
//                    IXXO                                 IXO
public class PixelShapeFiller
{
    private PixelShape sourceShape;
    private Coord2D innerPos;
    private boolean isBorderOfShapeSolid;
    
    
    public PixelShapeFiller(
            PixelShape sourceShape,
            Coord2D innerPos)
    {
        this(sourceShape, innerPos, false);
    }
    
    
    public PixelShapeFiller(
            PixelShape sourceShape,
            Coord2D innerPos,
            boolean isBorderOfShapeSolid)
    {
        this.sourceShape = sourceShape;
        this.innerPos = innerPos;
        this.isBorderOfShapeSolid = isBorderOfShapeSolid;
    }
    
    
    public PixelShape fill()
    {
        if (sourceShape.countPixels() == 0)
            return new BasePixelShape();
        
        Set<Coord2D> filledPixels = new HashSet<Coord2D>(sourceShape.pixels());

        // If the passed inner pixel is part of the shape, we cannot use it
        // because we have to assume that we could reach the shape's outside
        // and fill everything.
        if (!sourceShape.isHit(innerPos))
        {
            Stack<Coord2D> pixelToProcess = new Stack<Coord2D>();
            pixelToProcess.push(innerPos);

            while (pixelToProcess.size() > 0)
            {
                Coord2D pixel = pixelToProcess.pop();

                if (!filledPixels.contains(pixel))
                {
                    filledPixels.add(pixel);

                    NeighborPixelIterator it = createFillIterator(pixel, isBorderOfShapeSolid);
                    while (it.hasNext())
                        pixelToProcess.push(it.next());
                }
            }
        }
        
        return new BasePixelShape(filledPixels);
    }
    
    
    private static NeighborPixelIterator createFillIterator(
            Coord2D sourcePixel,
            boolean isBorderOfShapeSolid)
    {
        // If the shape is solid, we can include diagonal neighbors in the fill
        // traversal without having to worry to jump to the outside of the shape
        // by accident.
        return isBorderOfShapeSolid ?
                new AllNeighborPixelIterator(sourcePixel) : new DirectNeighborPixelIterator(sourcePixel);
    }
}
