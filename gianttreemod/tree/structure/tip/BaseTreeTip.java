package xmilcode.gianttreemod.tree.structure.tip;

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
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.cone.Cone;
import xmilcode.mclib.pixelgeometry3d.cone.ConeWithCenterPixel;
import xmilcode.mclib.pixelgeometry3d.cuboid.Cuboid;
import xmilcode.mclib.pixelgeometry3d.cuboid.CuboidGeometry;


public abstract class BaseTreeTip implements TreeTip, BlockCreator
{
    protected Cone shape;
    protected Cuboid bounds;
    protected Coord3D treeCorePos;
    private final GrowthStrategy growthStrategy;
    private GrowthManager growthMgr;


    protected BaseTreeTip(
            TreeTipSpec spec,
            Coord3D treeCorePos)
    {
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
        this.treeCorePos = treeCorePos;
        this.growthStrategy = createGrowthStrategy();
        this.growthMgr = new NullGrowthManager();
    }

    
    protected static Cone createShape(TreeTipSpec spec)
    {
        return new ConeWithCenterPixel(
                Axis3D.Y.axisVector(),
                spec.baseCenter(),
                spec.baseRadius(),
                spec.height());
    }
    
    
    protected static Cuboid createBounds(TreeTipSpec spec)
    {
        final int OFFSET_TO_INCL_BORDER_PIXELS = 1;

        final int diameter = 2 * spec.baseRadius();
        
        return new CuboidGeometry(
                new Coord3D(
                        spec.baseCenter().x - spec.baseRadius(),
                        spec.baseCenter().y,
                        spec.baseCenter().z - spec.baseRadius()),
                new Vector3D(
                        diameter + OFFSET_TO_INCL_BORDER_PIXELS,
                        spec.height() + OFFSET_TO_INCL_BORDER_PIXELS,
                        diameter + OFFSET_TO_INCL_BORDER_PIXELS));
    }
    
    
    private static GrowthStrategy createGrowthStrategy()
    {
        return new DirectionalGrowthStrategy(new AllNeighborsExpansion());
    }

    
    @Override
    public Coord3D baseCenter()
    {
        return shape.baseCenter();
    }

    
    @Override
    public int baseRadius()
    {
        return shape.radius();
    }

    
    @Override
    public int height()
    {
        return shape.height();
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
            hasGrown = growthStrategy.growAt(sourceBlock.world(), sourcePos, this, sourceBlock);
        
        return hasGrown;
    }

    
    @Override
    public boolean createOwnBlockAt(World world, Coord3D at)
    {
        if (canCreateTipBlockAt(world, at))
            return createTipBlockAt(world, at);
        return false;
    }

    
    @Override
    public boolean createAnyBlockAt(World world, Coord3D at)
    {
        if (createOwnBlockAt(world, at))
            return true;
        return growthMgr.createBlockAt(world, at, this);
    }
    
    
    private boolean canCreateTipBlockAt(World world, Coord3D at)
    {
        return isHit(at) && LeavesAttributes.REPLACEMENT_STRATEGY.canReplace(world, at);
    }


    private boolean createTipBlockAt(World world, Coord3D at)
    {
        world.setBlock(at.x, at.y, at.z, GiantTreeModule.activeLeavesBlock());

        LeavesBlockAtPos grownBlock = new LeavesBlockAtPos(world, at);
        grownBlock.setTreeCorePosition(treeCorePos);
        
        return true;
    }
}
