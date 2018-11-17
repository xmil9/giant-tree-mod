package xmilcode.gianttreemod.tree.structure.branch;

import java.util.Iterator;
import java.util.NoSuchElementException;

import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;


// Iterates over all branch orientations.
public class BranchOrientationIterator implements Iterator<Vector3D>
{
    private Vector3D nextAxis = Axis3D.X.axisVector();
    private boolean isFinished = false;
    
    
    @Override
    public boolean hasNext()
    {
        return !isFinished;
    }

    
    @Override
    public Vector3D next()
    {
        if (!hasNext())
            throw new NoSuchElementException();
        
        Vector3D axis = nextAxis;
        transit();
        return axis;
    }

    
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    
    private void transit()
    {
        if (nextAxis.x > 0 || nextAxis.z > 0)
        {
            nextAxis = Vector3D.reverse(nextAxis);
        }
        else if (nextAxis.x < 0)
        {
            nextAxis = Axis3D.Z.axisVector();
        }
        else
        {
            isFinished = true;
        }
    }
}
