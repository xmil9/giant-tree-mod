package xmilcode.gianttreemod.tree.structure.stairs;

import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.GrowthStrategy;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.growth.growthstrategies.AllUpAndSideNeighborsExpansion;
import xmilcode.gianttreemod.tree.growth.growthstrategies.DirectionalGrowthStrategy;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.material.wood.ActiveWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.WoodAttributes;
import xmilcode.gianttreemod.tree.material.wood.WoodBlockAtPos;
import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.CoordUtil2D;
import xmilcode.mclib.pixelgeometry2d.circle.CirclePixelGenerator;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithCenterPixel;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.cuboid.Cuboid;
import xmilcode.mclib.pixelgeometry3d.cuboid.CuboidGeometry;
import xmilcode.mclib.pixelgeometry3d.staircase.Staircase;
import xmilcode.mclib.util.MCConstants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


// Implements general stairs functionality.
public abstract class BaseStairs implements Stairs, BlockCreator
{
    protected Coord3D baseCenter;
    protected int radius;
    protected Staircase shape;
    protected Cuboid bounds;
    private final GrowthStrategy growthStrategy;
    private GrowthManager growthMgr;

    
    protected BaseStairs(StairsSpec spec)
    {
        this.baseCenter = spec.baseCenter();
        this.radius = spec.trunkRadius();
        this.shape = createShape(spec);
        this.bounds = createBounds(spec);
        this.growthStrategy = createGrowthStrategy();
        this.growthMgr = new NullGrowthManager();
    }
    
    
    private static GrowthStrategy createGrowthStrategy()
    {
        return new DirectionalGrowthStrategy(
                new AllUpAndSideNeighborsExpansion());
    }

    
    protected static Staircase createShape(StairsSpec spec)
    {
        Coord2D center2D = CoordUtil3D.mapTo2DXZPlane(spec.baseCenter());
        return new Staircase(
                new CircleWithCenterPixel(center2D, spec.trunkRadius()),
                center2D,
                spec.baseCenter().y,
                spec.height());
    }
    
    
    protected static Cuboid createBounds(StairsSpec spec)
    {
        final int OFFSET_TO_INCL_BORDER_PIXELS = 1;
        
        final int stairsRadius = spec.trunkRadius() + 1;
        final int stairsDiameter = 2 * stairsRadius;
        
        return new CuboidGeometry(
                new Coord3D(
                        spec.baseCenter().x - stairsRadius,
                        spec.baseCenter().y,
                        spec.baseCenter().z - stairsRadius),
                new Vector3D(
                        stairsDiameter + OFFSET_TO_INCL_BORDER_PIXELS,
                        spec.height() + OFFSET_TO_INCL_BORDER_PIXELS,
                        stairsDiameter + OFFSET_TO_INCL_BORDER_PIXELS));
    }
    
    
    @Override
    public int height()
    {
        return shape.height();
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
        if (canCreateStairsBlockAt(world, at))
            return createStairsBlockAt(world, at);
        return false;
    }


    @Override
    public boolean createAnyBlockAt(World world, Coord3D at)
    {
        if (createOwnBlockAt(world, at))
            return true;
        return growthMgr.createBlockAt(world, at, this);
    }
    
    
    private boolean canCreateStairsBlockAt(World world, Coord3D at)
    {
        return isHit(at) && WoodAttributes.replacementStrategy.canReplace(world, at);
    }


    private boolean createStairsBlockAt(World world, Coord3D at)
    {
        world.setBlock(
                at.x,
                at.y,
                at.z,
                GiantTreeModule.activeStairsWood,
                0,
                MCConstants.UPDATE_BLOCK_FLAG | MCConstants.NOTIFY_CLIENT_FLAG);
        
        if (!world.isRemote)
        {        
            WoodBlockAtPos grownBlock =
                    new WoodBlockAtPos(world, at, GiantTreeModule.passiveStairsWood);
            grownBlock.setTreeCorePosition(baseCenter);
        }
        
        return true;
    }
}
