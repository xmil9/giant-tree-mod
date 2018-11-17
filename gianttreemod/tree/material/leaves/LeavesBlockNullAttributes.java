package xmilcode.gianttreemod.tree.material.leaves;

import xmilcode.mclib.pixelgeometry3d.Coord3D;


public class LeavesBlockNullAttributes implements LeavesBlockPersistentAttributes
{
    @Override
    public Coord3D treeCorePosition()
    {
        // Have to return null because any other coordinate would by chance
        // belong to a tree core block (unlikely as it would be!). 
        return null;
    }

    @Override
    public void setTreeCorePosition(Coord3D corePos)
    {
    }

    @Override
    public boolean canGrow()
    {
        return false;
    }
    
    @Override
    public void markAsDoneGrowing()
    {
    }
}
