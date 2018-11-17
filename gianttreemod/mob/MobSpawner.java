package xmilcode.gianttreemod.mob;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import xmilcode.gianttreemod.mob.barkbeetle.BarkBeetleEntity;
import xmilcode.gianttreemod.mob.stickman.StickManEntity;
import xmilcode.mclib.pixelgeometry3d.Coord3D;


// Spawns a given number of mobs around a given position.
public class MobSpawner
{
    // Iterates over the (x, z) positions within a square around a passed
    // position. The y-coord (altitude) is calculated to be the first
    // air block above the passed y-coord.
    private static class SpawnPositionIterator implements Iterator<Coord3D>
    {
        private static final int START_CORNER_IDX = 0;
        private static final int END_CORNER_IDX = 1;

        private World world;
        private Coord3D[] spawnSquareCorners;
        private Coord3D nextPos;
        
        public SpawnPositionIterator(int numSpawns, Coord3D spawnPos, World world)
        {
            this.world = world;
            this.spawnSquareCorners = calcSpawnSquare(numSpawns, spawnPos);
            this.nextPos = calcInitialSpawnPosition(
                    spawnSquareCorners[START_CORNER_IDX],
                    world);
        }
        
        private static Coord3D[] calcSpawnSquare(int numSpawns, Coord3D spawnPos)
        {
            int squareWidth = calcSpawnSquareWidth(numSpawns);
            Coord3D[] squareCorners =  new Coord3D[2];
            squareCorners[START_CORNER_IDX] = new Coord3D(
                    spawnPos.x - squareWidth/2,
                    spawnPos.y,
                    spawnPos.z - squareWidth/2);
            squareCorners[END_CORNER_IDX] = new Coord3D(
                    squareCorners[START_CORNER_IDX].x + squareWidth,
                    spawnPos.y,
                    squareCorners[START_CORNER_IDX].z + squareWidth);
            return squareCorners;
        }
        
        private static int calcSpawnSquareWidth(int numSpawns)
        {
            int width = 1;
            while (numSpawns > width*width)
                ++width;
            return width;
        }
        
        private static Coord3D calcInitialSpawnPosition(Coord3D spawnSquareCorner, World world)
        {
            return new Coord3D(
                    spawnSquareCorner.x,
                    findSpawnY(
                            spawnSquareCorner.x,
                            spawnSquareCorner.y,
                            spawnSquareCorner.z,
                            world),
                    spawnSquareCorner.z);
        }
        
        private static int findSpawnY(int spawnX, int initialY, int spawnZ, World world)
        {
            int spawnY = initialY;
            
            if (world.isAirBlock(spawnX, spawnY, spawnZ))
            {
                while (world.isAirBlock(spawnX, --spawnY, spawnZ))
                    ;
                // We overshot by one block.
                ++spawnY;
            }
            else
            {
                while (!world.isAirBlock(spawnX, ++spawnY, spawnZ))
                    ;
            }
            
            return spawnY;
        }
        
        @Override
        public boolean hasNext()
        {
            return (nextPos.x <= spawnSquareCorners[END_CORNER_IDX].x &&
                    nextPos.z <= spawnSquareCorners[END_CORNER_IDX].z);
        }

        @Override
        public Coord3D next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            Coord3D pos = nextPos;
            nextPos = advance();
            return pos;
        }
        
        private Coord3D advance()
        {
            int advancedX = 0;
            int advancedZ = 0;
            
            // Advance x-coord until we hit the end x-coord, then
            // move to next z-coord.
            if (nextPos.x + 1 <= spawnSquareCorners[END_CORNER_IDX].x)
            {
                advancedX = nextPos.x + 1;
                advancedZ = nextPos.z;
            }
            else
            {
                advancedX = spawnSquareCorners[START_CORNER_IDX].x;
                advancedZ = nextPos.z + 1;
            }
            
            return new Coord3D(
                    advancedX,
                    findSpawnY(advancedX, nextPos.y, advancedZ, world),
                    advancedZ);
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    } // class SpawnPositionIterator
    
    
    public static void spawn(String mobNameId, int count, Coord3D at, World world)
    {
        if (count == 0)
            return;
        
        SpawnPositionIterator spawnPosIt =
                new SpawnPositionIterator(count, at, world);
        
        for (int i = 0; i < count; ++i)
            spawnSingleMobAt(mobNameId, spawnPosIt.next(), world);
    }
    
    
    private static void spawnSingleMobAt(String mobNameId, Coord3D at, World world)
    {
        Entity mob = createMobEntity(mobNameId, world);
        
        if (mob != null)
        {
            mob.setLocationAndAngles(at.x, at.y, at.z, 0F, 0F);
            
            boolean success = world.spawnEntityInWorld(mob);
            if (success)
                System.out.println("Spawning " + mobNameId + " at " + at.toString() + ".");
        }
    }
    
    
    private static Entity createMobEntity(String mobNameId, World world)
    {
        if (mobNameId == StickManEntity.NAME_ID)
            return new StickManEntity(world);
        else if (mobNameId == BarkBeetleEntity.NAME_ID)
            return new BarkBeetleEntity(world);
        
        return null;
    }
}
