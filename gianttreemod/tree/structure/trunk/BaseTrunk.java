package xmilcode.gianttreemod.tree.structure.trunk;

import java.util.List;

import xmilcode.gianttreemod.GiantTreeMod;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.gianttreemod.tree.growth.BlockCreator;
import xmilcode.gianttreemod.tree.growth.GrowablePart;
import xmilcode.gianttreemod.tree.growth.GrowthManager;
import xmilcode.gianttreemod.tree.growth.GrowthStrategy;
import xmilcode.gianttreemod.tree.growth.NullGrowthManager;
import xmilcode.gianttreemod.tree.growth.growthstrategies.AllUpAndSideNeighborsExpansion;
import xmilcode.gianttreemod.tree.growth.growthstrategies.DirectionalExpansionStrategy;
import xmilcode.gianttreemod.tree.growth.growthstrategies.DirectionalGrowthStrategy;
import xmilcode.gianttreemod.tree.material.treecore.TreeBlockAtPos;
import xmilcode.gianttreemod.tree.material.wood.ActiveWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.BaseWoodBlock;
import xmilcode.gianttreemod.tree.material.wood.WoodAttributes;
import xmilcode.gianttreemod.tree.material.wood.WoodBlockAtPos;
import xmilcode.mclib.pixelgeometry2d.Coord2D;
import xmilcode.mclib.pixelgeometry2d.CoordUtil2D;
import xmilcode.mclib.pixelgeometry2d.circle.Circle;
import xmilcode.mclib.pixelgeometry2d.circle.CircleWithCenterPixel;
import xmilcode.mclib.pixelgeometry2d.shape.BasePixelShape;
import xmilcode.mclib.pixelgeometry2d.shape.PixelShape;
import xmilcode.mclib.pixelgeometry3d.Axis3D;
import xmilcode.mclib.pixelgeometry3d.Coord3D;
import xmilcode.mclib.pixelgeometry3d.CoordUtil3D;
import xmilcode.mclib.pixelgeometry3d.Vector3D;
import xmilcode.mclib.pixelgeometry3d.cuboid.Cuboid;
import xmilcode.mclib.pixelgeometry3d.cuboid.CuboidGeometry;
import xmilcode.mclib.pixelgeometry3d.cylinder.Cylinder;
import xmilcode.mclib.pixelgeometry3d.cylinder.CylinderWithCenterVoxel;
import xmilcode.mclib.util.MCConstants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


// Implements general trunk functionality.
public abstract class BaseTrunk implements Trunk, BlockCreator
{
    protected Cylinder shape;
    protected Cuboid bounds;
    private final GrowthStrategy growthStrategy;
    private GrowthManager growthMgr;
    

    protected BaseTrunk(TrunkSpec spec)
    {
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
    
    protected Cylinder createShape(TrunkSpec spec)
    {
        return new CylinderWithCenterVoxel(
                Axis3D.Y.axisVector(),
                spec.position(),
                spec.radius(),
                spec.height());
    }
    
    
    protected static Cuboid createBounds(TrunkSpec spec)
    {
        final int OFFSET_TO_INCL_BORDER_PIXELS = 1;
        final int diameter = 2 * spec.radius();
        
        return new CuboidGeometry(
                new Coord3D(
                        spec.position().x - spec.radius(),
                        spec.position().y,
                        spec.position().z - spec.radius()),
                new Vector3D(
                        diameter + OFFSET_TO_INCL_BORDER_PIXELS,
                        spec.height() + OFFSET_TO_INCL_BORDER_PIXELS,
                        diameter + OFFSET_TO_INCL_BORDER_PIXELS));
    }
    
    
    
    @Override
    public Coord3D position()
    {
        return shape.baseCenter();
    }
    
    
    @Override
    public int radius()
    {
        return shape.radius();
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
    public boolean isTop(Coord3D pos)
    {
        return (pos.y == position().y + height());
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
        if (canCreateTrunkBlockAt(world, at))
            return createTrunkBlockAt(world, at);
        return false;
    }


    @Override
    public boolean createAnyBlockAt(World world, Coord3D at)
    {
        if (createOwnBlockAt(world, at))
            return true;
        return growthMgr.createBlockAt(world, at, this);
    }
    
    
    private boolean canCreateTrunkBlockAt(World world, Coord3D at)
    {
        return isHit(at) && WoodAttributes.replacementStrategy.canReplace(world, at);
    }


    private boolean createTrunkBlockAt(World world, Coord3D at)
    {
        world.setBlock(
                at.x,
                at.y,
                at.z,
                GiantTreeModule.activeTrunkWood,
                0,
                MCConstants.UPDATE_BLOCK_FLAG | MCConstants.NOTIFY_CLIENT_FLAG);

        WoodBlockAtPos grownBlock =
                new WoodBlockAtPos(world, at, GiantTreeModule.passiveTrunkWood);
        grownBlock.setTreeCorePosition(position());
        
        return true;
    }
}
