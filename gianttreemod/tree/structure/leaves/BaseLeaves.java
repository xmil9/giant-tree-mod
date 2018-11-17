package xmilcode.gianttreemod.tree.structure.leaves;

import net.minecraft.world.World;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.GrowthStrategy;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.growth.growthstrategies.AllNeighborsExpansion;
import xmilcode.gianttreemod.tree.growth.growthstrategies.DirectionalGrowthStrategy;
import xmilcode.gianttreemod.tree.material.leaves.LeavesBlockAtPos;
import xmilcode.gianttreemod.tree.material.leaves.LeavesAttributes;
import xmilcode.gianttreemod.tree.material.leaves.standard.ActiveStdLeavesBlock;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.cuboid.Cuboid;
import xmilcode.mclib.pixelgeometry3d.cuboid.CuboidGeometry;
import xmilcode.mclib.pixelgeometry3d.triangularprism.TriangularPrism;



public abstract class BaseLeaves implements Leaves, BlockCreator
{
    protected LeavesSpec spec;
    protected TriangularPrism shape;
    protected Cuboid bounds;
    protected int atTreeHeight;
    protected Coord3D treeCorePos;
    private final GrowthStrategy growthStrategy;
    private GrowthManager growthMgr;
    
    
    protected BaseLeaves(
            LeavesSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        this.spec = spec;
        this.atTreeHeight = atHeight;
        this.treeCorePos = treeCorePos;
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
        this.growthStrategy = createGrowthStrategy();
        this.growthMgr = new NullGrowthManager();
    }
    
    
    protected static TriangularPrism createShape(LeavesSpec spec)
    {
        LeavesShapeGenerator gen = new LeavesShapeGenerator(spec);
        return gen.generateShape();
    }
    
    
    protected static Cuboid createBounds(LeavesSpec spec)
    {
        LeavesShapeGenerator gen = new LeavesShapeGenerator(spec);
        return gen.generateBounds();
    }
    
    private static GrowthStrategy createGrowthStrategy()
    {
        return new DirectionalGrowthStrategy(new AllNeighborsExpansion());
    }
    
    
    @Override
    public Vector3D branchAxis()
    {
        return spec.branchAxis();
    }
    
    
    @Override
    public Coord3D position()
    {
        return spec.position();
    }
    
    
    @Override
    public int length()
    {
        return spec.length();
    }
    
    
    @Override
    public int width()
    {
        return spec.width();
    }

    @Override
    public int height()
    {
        return spec.height();
    }
    
    
    @Override
    public int atTreeHeight()
    {
        return atTreeHeight;
    }
    
    
    @Override
    public Coord3D treeCorePosition()
    {
        return treeCorePos;
    }
    
    
    @Override
    public boolean isHit(Coord3D pos)
    {
        if (!bounds.isHit(pos))
            return false;
        return shape.isHit(pos);
    }

    
    @Override
    public void setGrowthManager(GrowthManager mgr)
    {
        growthMgr = mgr;
        growthMgr.registerGrowingPart(this);
    }

    
    @Override
    public boolean growAt(TreeBlockAtPos sourceBlock)
    {
        boolean hasGrown = false;
        
        Coord3D sourcePos = sourceBlock.position();

        if (isHit(sourcePos))
        {
            hasGrown = growthStrategy.growAt(
                    sourceBlock.world(),
                    sourcePos,
                    this,
                    sourceBlock);
        }
        
        return hasGrown;
    }
    
    
    @Override
    public boolean createOwnBlockAt(World world, Coord3D at)
    {
        if (canCreateLeavesBlockAt(world, at))
            return createLeavesBlockAt(world, at);
        return false;
    }

    
    @Override
    public boolean createAnyBlockAt(World world, Coord3D at)
    {
        if (createOwnBlockAt(world, at))
            return true;
        
        // Optimization: Skip checking whether other parts can grow at the
        // given pos. Not other parts are only reachable through leaves, so
        // nothing is lost by skipping this call, but since there are a lot
        // of leaves on a tree we save a lot by skipping it.
        //return growthMgr.createBlockAt(world, at);
        return false;
    }
    
    
    private boolean canCreateLeavesBlockAt(World world, Coord3D at)
    {
        return isHit(at) && LeavesAttributes.REPLACEMENT_STRATEGY.canReplace(world, at);
    }


    private boolean createLeavesBlockAt(World world, Coord3D at)
    {
        world.setBlock(at.x, at.y, at.z, GiantTreeModule.activeLeavesBlock());

        LeavesBlockAtPos grownBlock = new LeavesBlockAtPos(world, at);
        grownBlock.setTreeCorePosition(treeCorePos);
        
        return true;
    }
}
