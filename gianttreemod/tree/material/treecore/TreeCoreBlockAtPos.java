package xmilcode.gianttreemod.tree.material.treecore;

import java.util.ArrayList;
import java.util.Random;

import xmilcode.gianttreemod.mob.GtMobModule;
import xmilcode.gianttreemod.mob.MobSpawner;
import xmilcode.gianttreemod.mob.barkbeetle.BarkBeetleEntity;
import xmilcode.gianttreemod.mob.stickman.StickManEntity;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.structure.NullTree;
import xmilcode.gianttreemod.tree.structure.Tree;
import xmilcode.gianttreemod.tree.structure.TreeGenerator;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.util.MathUtil;
import xmilcode.mclib.util.RandomUtil;
import xmilcode.mclib.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;


public class TreeCoreBlockAtPos implements TreeBlockAtPos
{
    // Needs to be far enough from trunk to fit half of the width
    // of a square within which the max number of mobs can spawn.
    private static final int SPAWN_OFFSET_FROM_TRUNK = 6;

    private final World world;
    private final Coord3D pos;
    // Occasionally a block is not part of the world and its metadata
    // cannot be accessed through the world object. In this case the
    // metadata can be passed explicitly into the ctor and is cached in
    // a field.
    private final int cachedMetadata;
    private final boolean useCachedMetadata;
    
    
    TreeCoreBlockAtPos(World world, Coord3D pos, int metadata)
    {
        this(world, pos, metadata, true);
    }
    
    
    TreeCoreBlockAtPos(World world, Coord3D pos)
    {
        this(world, pos, 0, false);
    }
    
   
    private TreeCoreBlockAtPos(
            World world,
            Coord3D pos,
            int metadata,
            boolean useMetadata)
    {
        this.world = world;
        this.pos = pos;
        this.cachedMetadata = metadata;
        this.useCachedMetadata = useMetadata;
    }
    
    
    @Override
    public Coord3D position()
    {
        return pos;
    }
    
    
    @Override
    public World world()
    {
        return world;
    }


    @Override
    public boolean canGrow()
    {
        return getPersistentAttributes().canGrow();
    }


    @Override
    public void markAsDoneGrowing()
    {
        getPersistentAttributes().markAsDoneGrowing();
    }
    
    
    void updateTick(Random rand)
    {
        grow();
        spawnMobs();
    }

    
    private void grow()
    {
        tree().growCoreBlockAt(this);
    }
    
    
    private Tree tree()
    {
        return getPersistentAttributes().tree();
    }
    
    
    // Spawns mobs at a location close to the core block's tree.
    private void spawnMobs()
    {
        if (WorldUtil.isServerSide(world))
        {
            MobSpawner.spawn(
                    StickManEntity.NAME_ID,
                    StickManEntity.getSpawnQuantityForCloseToTreeSpawns(world),
                    calcSpawnPositionA(),
                    world);

            MobSpawner.spawn(
                    BarkBeetleEntity.NAME_ID,
                    BarkBeetleEntity.getSpawnQuantityForCloseToTreeSpawns(world),
                    calcSpawnPositionB(),
                    world);
        }
    }
    
    
    private Coord3D calcSpawnPositionA()
    {
        Coord3D corePos = tree().corePosition();
        return new Coord3D(
                corePos.x + tree().trunkRadius() + SPAWN_OFFSET_FROM_TRUNK,
                corePos.y,
                corePos.z);
    }
    
    
    private Coord3D calcSpawnPositionB()
    {
        Coord3D corePos = tree().corePosition();
        return new Coord3D(
                corePos.x,
                corePos.y,
                corePos.z + tree().trunkRadius() + SPAWN_OFFSET_FROM_TRUNK);
    }
    
    
    void onPostBlockPlaced()
    {
        createTree();
        scheduleInitialGrowth();
    }
    
    
    void onWasPlanted()
    {
        createTree();
        scheduleInitialGrowth();
    }
    
    
    private void createTree()
    {
        if (WorldUtil.isServerSide(world) && !hasTree())
        {
            Tree newTree = TreeGenerator.generateTreeAt(pos, world.rand);
            getPersistentAttributes().setTree(newTree);
            GiantTreeModule.treeRegistry.registerTree(world, newTree);
        }
    }

    
    private void scheduleInitialGrowth()
    {
        if (WorldUtil.isServerSide(world))
        {
            final int INITIAL_TICK = 20;
            Block thisBlock = world.getBlock(pos.x, pos.y, pos.z);
            world.scheduleBlockUpdate(
                    pos.x,
                    pos.y,
                    pos.z,
                    thisBlock,
                    INITIAL_TICK);
        }
    }

    
    private boolean hasTree()
    {
        return (getPersistentAttributes().tree().typeId() != NullTree.TYPE_ID);
    }
    
    
    private TreeCoreBlockPersistentAttributes getPersistentAttributes()
    {
        TileEntity te = world.getTileEntity(pos.x, pos.y, pos.z);
        if (te != null && te instanceof TreeCoreBlockTileEntity)
        {
            return (TreeCoreBlockTileEntity)te;
        }
        else
        {
            System.out.println(
                    "Warning: Giant tree block at " + pos.toString() + " has no tile entity!");
            return new TreeCoreBlockNullAttributes();
        }
    }
    
    
    ArrayList<ItemStack> getDrops(int fortune)
    {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(new ItemStack(GiantTreeModule.giantTreeSeed, 1, 0));
        return drops;
    }
    
    
    void onBlockDestroyedByPlayer()
    {
        onBlockDestroyed();
    }
    
    
    void onBlockDestroyedByExplosion(Explosion explosion)
    {
        onBlockDestroyed();
    }
    
    
    private void onBlockDestroyed()
    {
        GiantTreeModule.treeRegistry.unregisterTree(world, tree());
    }
}
