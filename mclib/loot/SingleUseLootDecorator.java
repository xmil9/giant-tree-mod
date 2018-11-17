package xmilcode.mclib.loot;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xmilcode.mclib.INbtPersistent;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Adds single-use functionality to any loot object.
public class SingleUseLootDecorator implements Loot, INbtPersistent
{
    private static final String HAS_BEEN_USED_TAG = "has_been_used";

    private Loot decorated;
    private boolean hasBeenUsed;
    
    
    public SingleUseLootDecorator(Loot decorated)
    {
        this.decorated = (decorated != null) ? decorated : new NoLoot();
        this.hasBeenUsed = false;
    }
    
    
    // Allows to set the decorated loot after the single-use decorator
    // has been created.
    public void setLoot(Loot decorated)
    {
        this.decorated = decorated;
    }
    
    
    @Override
    public boolean haveLoot()
    {
        return !hasBeenUsed && decorated.haveLoot();
    }

    
    @Override
    public void placeAt(World world, Coord3D pos)
    {
        if (!hasBeenUsed)
        {
            decorated.placeAt(world, pos);
            hasBeenUsed = true;
        }
    }

    
    @Override
    public void read(NBTTagCompound storage)
    {
        if (decorated instanceof INbtPersistent)
        {
            INbtPersistent persistentLoot = (INbtPersistent)decorated;
            persistentLoot.read(storage);
        }

        hasBeenUsed = storage.getBoolean(HAS_BEEN_USED_TAG);
    }

    
    @Override
    public void write(NBTTagCompound storage)
    {
        if (decorated instanceof INbtPersistent)
        {
            INbtPersistent persistentLoot = (INbtPersistent)decorated;
            persistentLoot.write(storage);
        }
        
        storage.setBoolean(HAS_BEEN_USED_TAG, hasBeenUsed);
    }
}
