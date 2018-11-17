package xmilcode.mclib;

import net.minecraft.nbt.NBTTagCompound;


public interface INbtPersistent
{
    void read(NBTTagCompound storage);
    void write(NBTTagCompound storage);
}
