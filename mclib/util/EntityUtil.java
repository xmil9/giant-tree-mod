package xmilcode.mclib.util;

import xmilcode.gianttreemod.mob.stickman.StickManEntity;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;


public class EntityUtil
{
    public static void addSpawnForAllBiomes(
            Class <? extends EntityLiving > entityClass,
            int spawnFrequency,
            int minSpawned,
            int maxSpawned,
            EnumCreatureType creatureType)
    {
        for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; ++i)
        {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[i]; 
            if (biome != null)
            {
                EntityRegistry.addSpawn(
                        entityClass,
                        spawnFrequency,
                        minSpawned,
                        maxSpawned,
                        creatureType,
                        biome);
            }
        }
    }
}
