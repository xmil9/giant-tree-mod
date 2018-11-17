package xmilcode.mclib.pixelgeometry2d.shape;

import java.util.HashSet;
import java.util.Set;

import xmilcode.mclib.pixelgeometry2d.AllNeighborPixelIterator;
import xmilcode.mclib.pixelgeometry2d.AngularComparator2D;
import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.DirectNeighborPixelIterator;
import xmilcode.mclib.pixelgeometry2d.NeighborPixelIterator;



// Returns a collection of pixels that outline a given shape.
// Needs to be given a point that defines where the inside of the shape is.
// Assumes the shape's path is closed.
// If the border of the shape is not solid (i.e. inside and outside pixels
// of the shape touch each other diagonally), the fill algorithm has to use
// a conservative fill strategy and will not fill inside pixel that can only
// be reached diagonally from other inside pixels.
// Example: (X=border pixel, I=inside pixel, O=outside pixel)
// Solid border:  IXXO            Not solid border:    IXO
//                 IXXO                                 IXO
//                  IXXO                                 IXO
public class PixelShapeOutliner
{
    private enum OutlineForm { OUTSIDE, INSIDE }
    private enum OutlineThickness { SOLID, THIN }

    private PixelShape sourceShape;
    private Coord2D innerPos;
    private boolean isBorderOfShapeSolid;
    
    
    public PixelShapeOutliner(
            PixelShape sourceShape,
            Coord2D innerPos)
    {
        this(sourceShape, innerPos, false);
    }
    
    
    public PixelShapeOutliner(
            PixelShape sourceShape,
            Coord2D innerPos,
            boolean isBorderOfShapeSolid)
    {
        this.sourceShape = sourceShape;
        this.innerPos = innerPos;
        this.isBorderOfShapeSolid = isBorderOfShapeSolid;
    }


    // Variations:
    // - Form:
    //     Outside - The outline is formed around the outside of the shape's
    //               pixels. No pixels of the outline will be shape pixels.
    //     Inside  - The outline is formed by the outer-most pixels that are
    //               part of the shape. All pixels of the outline will be
    //               shape pixels.
    // - Thickness:
    //     Solid - No inside and outside pixels touch each other diagonally.
    //     Thin  - Inside and outside pixels of the shape might touch each other
    //             diagonally.
    public PixelShape outlineSolidOnOutside()
    {
        return outline(OutlineForm.OUTSIDE, OutlineThickness.SOLID);
    }

    public PixelShape outlineThinOnOutside()
    {
        return outline(OutlineForm.OUTSIDE, OutlineThickness.THIN);
    }
    
    public PixelShape outlineSolidOnInside(boolean createSolidOutline)
    {    
        return outline(OutlineForm.INSIDE, OutlineThickness.SOLID);
    }
    
    public PixelShape outlineThinOnInside(boolean createSolidOutline)
    {    
        return outline(OutlineForm.INSIDE, OutlineThickness.THIN);
    }
    
    
    private PixelShape outline(
            OutlineForm form,
            OutlineThickness thickness)
    {
        PixelShapeFiller filler = new PixelShapeFiller(sourceShape, innerPos, isBorderOfShapeSolid);
        PixelShape filledShape = filler.fill();
        
        NeighborPixelIterator neighborIter =
                (thickness == OutlineThickness.SOLID) ?
                        new AllNeighborPixelIterator(new Coord2D()) : new DirectNeighborPixelIterator(new Coord2D());
                
        Set<Coord2D> outline;
        if (form == OutlineForm.OUTSIDE)
            outline = outlineFilledShapeOnOutside(filledShape, neighborIter);
        else
            outline = outlineFilledShapeOnInside(filledShape, neighborIter);
        
        return new BasePixelShape(new HashSet<Coord2D>(outline));
    }
    
    
    private static Set<Coord2D> outlineFilledShapeOnOutside(
           PixelShape shape,
           NeighborPixelIterator neighborIter)
    {
        HashSet<Coord2D> outline = new HashSet<Coord2D>();
        if (shape.countPixels() == 0)
            return outline;
        
        for (Coord2D shapePixel : shape.pixels())
        {
            neighborIter.resetForPixel(shapePixel);
            
            while (neighborIter.hasNext())
            {
                Coord2D neighbor = neighborIter.next();
                if (!shape.pixels().contains(neighbor))
                    outline.add(neighbor);
            }
        }
        
        return outline;
    }
   
   
    private static Set<Coord2D> outlineFilledShapeOnInside(
            PixelShape shape,
            NeighborPixelIterator neighborIter)
    {
        HashSet<Coord2D> outline = new HashSet<Coord2D>();
        if (shape.countPixels() == 0)
            return outline;

        for (Coord2D shapePixel : shape.pixels())
        {
            boolean continueWithNextShapePixel = false;
            neighborIter.resetForPixel(shapePixel);

            while (neighborIter.hasNext() && !continueWithNextShapePixel)
            {
                Coord2D neighbor = neighborIter.next();
                if (!shape.pixels().contains(neighbor))
                {
                    outline.add(shapePixel);
                    // We don't need to look at any further neighbors of
                    // this shape pixel anymore.
                    continueWithNextShapePixel = true;
                }
            }
        }

        return outline;
    }
}
