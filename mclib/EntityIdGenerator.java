package xmilcode.mclib;

import net.minecraft.entity.EntityList;
import xmilcode.mclib.util.MCConstants;


// Generates unused ids for entities.
public class EntityIdGenerator
{
    private int nextId;
    
    
    public EntityIdGenerator()
    {
        this(MCConstants.HIGHEST_MC_ENTITY_ID + 1);
    }

    public EntityIdGenerator(int startId)
    {
        this.nextId = startId;
    }
    
    public int nextId()
    {
        int id = findNextUnusedId(nextId);
        nextId = id + 1;
        return id;
    }
    
    private static int findNextUnusedId(int candidate)
    {
        while (EntityList.getStringFromID(candidate) != null)
            ++candidate;
        return candidate;
    }
}
