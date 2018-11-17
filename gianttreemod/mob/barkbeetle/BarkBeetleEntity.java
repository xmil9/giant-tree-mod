package xmilcode.gianttreemod.mob.barkbeetle;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.mob.GtMobModule;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.mclib.ModBase;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.EntityUtil;
import xmilcode.mclib.util.NamingUtil;
import xmilcode.mclib.util.RandomUtil;
import cpw.mods.fml.common.registry.EntityRegistry;


public class BarkBeetleEntity extends EntityMob
{
    public static final String NAME_ID = "barkbeetle_mob";
    
    // Bounding box
    private static final float BOUNDS_WIDTH = 0.4F; 
    private static final float BOUNDS_HEIGHT = 0.2F; 
    // Distance from player up to which entity is active. Beyond this
    // distance the entity is passive.
    private static final int TRACKING_RANGE = 80;
    // Frequency of entity updates.
    private static final int UPDATE_FREQ = 3;
    private static final boolean INCLUDE_VELOCITY_IN_UPDATES = true; 
    static final float SHADOW_SIZE = 0.2F;
    // Colors of spawn egg.
    private static final int EGG_BASE_COLOR = 0x6B3500; // brown
    private static final int EGG_SPOT_COLOR = 0x000000; // black
    // Spawn parameters.
    private static final int MIN_NUM_SPAWNED = 2;
    private static final int MAX_NUM_SPAWNED = 5;
    private static final int SPAWN_DISTANCE_TO_TREE = 40;
    // How far below the tree's y-coord spawning is allowed. Necessary in case
    // tree is on a hill/peak.
    private static final int SPAWN_BELOW_TREE_MARGIN = 20;
    // Base attributes.
    // MC default: 20.0, Enderman: 40.0
    private static final double MAX_HEALTH = 2.0;
    // MC default: 0.699999988079071, Zombie: 0.23000000417232513, Enderman: 0.30000001192092896
    private static final double MOVEMENT_SPEED = 1.5;
    // MC default: 2.0, Zombie: 3.0, Enderman: 7.0
    private static final double ATTACK_DAMAGE = 0.2;
    
    
    public BarkBeetleEntity(World world)
    {
        super(world);
        setSize(BOUNDS_WIDTH, BOUNDS_HEIGHT);
        defineBehavior();
    }


    public static void register(ModBase mod)
    {
        int id = ModBase.entityIdGen.nextId();
        
        EntityRegistry.registerModEntity(
                BarkBeetleEntity.class,
                NamingUtil.generateEntityName(GiantTreeMod.MODID, NAME_ID),
                id,
                mod,
                TRACKING_RANGE,
                UPDATE_FREQ,
                INCLUDE_VELOCITY_IN_UPDATES);
        
        setupEggSpawn(id);
        setupNaturalSpawn();
    }

    
    public static void setupEggSpawn(int entityId)
    {
        EntityList.addMapping(
                BarkBeetleEntity.class,
                NamingUtil.generateEntityName(GiantTreeMod.MODID, NAME_ID),
                entityId,
                EGG_BASE_COLOR,
                EGG_SPOT_COLOR);
    }
    
    
    public static void setupNaturalSpawn()
    {
        EntityUtil.addSpawnForAllBiomes(
                BarkBeetleEntity.class,
                getNaturalSpawnFrequency(),
                MIN_NUM_SPAWNED,
                MAX_NUM_SPAWNED,
                EnumCreatureType.monster);
    }
    
    
    private void defineBehavior()
    {
        defineNavigation();
        defineTasks();
        defineTargetTasks();
    }
    
    
    private void defineNavigation()
    {
        getNavigator().setAvoidSun(true);
        getNavigator().setAvoidsWater(true);
    }
    
    
    private void defineTasks()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        
        final double attackSpeed = 1.0;
        final boolean hasLongTermMemory = false;
        tasks.addTask(2, new EntityAIAttackOnCollide(
                this,
                EntityPlayer.class,
                attackSpeed,
                hasLongTermMemory));
        tasks.addTask(2, new EntityAIAttackOnCollide(
                this,
                EntityVillager.class,
                attackSpeed,
                hasLongTermMemory));
        
        final double wanderSpeed = 1.0;
        tasks.addTask(3, new EntityAIWander(this, wanderSpeed));
        
        final float maxWatchingDistance = 8.0F;
        final float watchingChance = 0.02F;
        tasks.addTask(4, new EntityAIWatchClosest(
                this,
                EntityPlayer.class,
                maxWatchingDistance,
                watchingChance));
        tasks.addTask(4, new EntityAIWatchClosest(
                this,
                EntityVillager.class,
                maxWatchingDistance,
                watchingChance));
        
        tasks.addTask(4, new EntityAILookIdle(this));
    }
    
    
    private void defineTargetTasks()
    {
        final boolean callsForHelp = true;
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, callsForHelp));

        final int targetChanceAlways = 0;
        final boolean onlyVisibleTargets = true;
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(
                this,
                EntityPlayer.class,
                targetChanceAlways,
                onlyVisibleTargets));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(
                this,
                EntityVillager.class,
                targetChanceAlways,
                onlyVisibleTargets));
    }
    
    
    private static int getNaturalSpawnFrequency()
    {
        switch (GtMobModule.mobSettings.mobFrequency())
        {
            case FEW:
                return 10;
            case NORMAL:
                return 25;
            case MANY:
                return 50;
            default:
                throw new IllegalStateException();
        }
    }
    
    
    public static int getSpawnQuantityForCloseToTreeSpawns(World world)
    {
        switch (GtMobModule.mobSettings.mobFrequency())
        {
            case FEW:
                if (!world.isDaytime())
                    return 5;
                return 2 +
                        (RandomUtil.sampleRandEvent(0.1F, world.rand) ? 1 : 0);
            case NORMAL:
                if (!world.isDaytime())
                    return 10;
                return 5 +
                        (RandomUtil.sampleRandEvent(0.3F, world.rand) ? 1 : 0) +
                        (RandomUtil.sampleRandEvent(0.1F, world.rand) ? 1 : 0);
            case MANY:
                if (!world.isDaytime())
                    return 20;
                return 10 +
                        (RandomUtil.sampleRandEvent(0.8F, world.rand) ? 2 : 0) +
                        (RandomUtil.sampleRandEvent(0.2F, world.rand) ? 1 : 0);
            default:
                throw new IllegalStateException();
        }
    }
    
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MAX_HEALTH);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MOVEMENT_SPEED);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(ATTACK_DAMAGE);
    }
    
    
    @Override
    public boolean getCanSpawnHere()
    {
        if (!super.getCanSpawnHere())
            return false;

        Coord3D pos = new Coord3D(
                MathHelper.floor_double(posX),
                MathHelper.floor_double(posY),
                MathHelper.floor_double(posZ));
        
        // Remove for speed sake.
//        if (!isWithinSpawningDistanceFromTree(worldObj, pos))
//        {
//            return false;
//        }
        
        System.out.println("Spawning Bark Beetle at " + pos.toString() + ".");
        return true;
    }
    
    
    private boolean isWithinSpawningDistanceFromTree(World world, Coord3D spawnPos)
    {
        // Check distance in square around the tree. This is faster because no math
        // is needed, only coordinate comparisons.
        Tree treeWithinRange = GiantTreeModule.treeRegistry.findAnyTreeWithinBox(
                world,
                spawnPos,
                SPAWN_DISTANCE_TO_TREE,
                SPAWN_BELOW_TREE_MARGIN);
        return (treeWithinRange != null); 
    }
    
    
    @Override
    protected Item getDropItem()
    {
        if (worldObj.rand.nextInt(4) < 1)
            return GtMobModule.chitinPowder;
        return super.getDropItem();
    }
}
