package xmilcode.gianttreemod.tree.structure.branch;

import net.minecraft.world.World;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.GrowthStrategy;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.growth.growthstrategies.AllNeighborsExpansion;
import xmilcode.gianttreemod.tree.growth.growthstrategies.DirectionalGrowthStrategy;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.material.wood.ActiveWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.WoodAttributes;
import xmilcode.gianttreemod.tree.material.wood.WoodBlockAtPos;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.cuboid.Cuboid;
import xmilcode.mclib.pixelgeometry3d.cuboid.CuboidGeometry;
import xmilcode.mclib.pixelgeometry3d.cylinder.Cylinder;
import xmilcode.mclib.pixelgeometry3d.cylinder.CylinderWithCenterVoxel;
import xmilcode.mclib.pixelgeometry3d.shape.VoxelShape;
import xmilcode.mclib.util.MCConstants;


public abstract class BaseBranch implements Branch, BlockCreator
{
    protected BranchSpec spec;
    protected int atTreeHeight;
    protected Coord3D treeCorePos;
    protected Cylinder shape;
    protected Cuboid bounds;
    private final GrowthStrategy growthStrategy;
    private GrowthManager growthMgr;
    
    
    protected BaseBranch(
            BranchSpec spec,
            int atHeight,
            Coord3D treeCorePos)
    {
        this.growthMgr = new NullGrowthManager();
        this.spec = spec;
        this.atTreeHeight = atHeight;
        this.treeCorePos = treeCorePos;
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
        this.growthStrategy = createGrowthStrategy();
    }
    
    
    protected static Cylinder createShape(BranchSpec spec)
    {
        return new CylinderWithCenterVoxel(
                spec.axis(),
                spec.position(),
                spec.radius(),
                spec.length());
    }

    
    protected static Cuboid createBounds(BranchSpec spec)
    {
        final int OFFSET_TO_INCL_BORDER_PIXELS = 1;
        final int diameter = 2 * spec.radius();
        
        // Either the x- or the z-axis have to be center axis of a branch.
        final boolean isXAxisTheCenterAxis = (spec.axis().x != 0);
        
        final int directionSign = isXAxisTheCenterAxis ? spec.axis().x : spec.axis().z;
        final int directionalLength = directionSign * spec.length();
        
        final int distToSideX = isXAxisTheCenterAxis ? 0 : spec.radius();
        final int distToSideZ = !isXAxisTheCenterAxis ? 0 : spec.radius();
        final int branchSizeX = isXAxisTheCenterAxis ? directionalLength : diameter;
        final int branchSizeZ = !isXAxisTheCenterAxis ? directionalLength : diameter;
        
        return new CuboidGeometry(
                new Coord3D(
                        spec.position().x - distToSideX,
                        spec.position().y - spec.radius(),
                        spec.position().z - distToSideZ),
                new Vector3D(
                        branchSizeX + OFFSET_TO_INCL_BORDER_PIXELS,
                        diameter + OFFSET_TO_INCL_BORDER_PIXELS,
                        branchSizeZ + OFFSET_TO_INCL_BORDER_PIXELS));
    }

    
    private static GrowthStrategy createGrowthStrategy()
    {
        return new DirectionalGrowthStrategy(new AllNeighborsExpansion());
    }
    
    
    protected GrowthManager growthManager()
    {
        return growthMgr;
    }
    
    
    @Override
    public BranchSpec spec()
    {
        return spec;
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
            hasGrown = growthStrategy.growAt(sourceBlock.world(), sourcePos, this, sourceBlock);
        
        return hasGrown;
    }


    @Override
    public boolean createOwnBlockAt(World world, Coord3D at)
    {
        if (canCreateBranchBlockAt(world, at))
            return createBranchBlockAt(world, at);
        return false;
    }


    @Override
    public boolean createAnyBlockAt(World world, Coord3D at)
    {
        if (createOwnBlockAt(world, at))
            return true;
        return growthMgr.createBlockAt(world, at, this);
    }
    
    
    private boolean canCreateBranchBlockAt(World world, Coord3D at)
    {
        return isHit(at) && WoodAttributes.replacementStrategy.canReplace(world, at);
    }


    private boolean createBranchBlockAt(World world, Coord3D at)
    {
        world.setBlock(
                at.x,
                at.y,
                at.z,
                GiantTreeModule.activeBranchWood,
                0,
                MCConstants.UPDATE_BLOCK_FLAG | MCConstants.NOTIFY_CLIENT_FLAG);

        WoodBlockAtPos grownBlock =
                new WoodBlockAtPos(world, at, GiantTreeModule.passiveBranchWood);
        grownBlock.setTreeCorePosition(treeCorePos);
        
        return true;
    }
}
