package xmilcode.mclib.util;


public class MCConstants
{
    // Dimension of a world chunk in blocks.
    public static final int CHUNK_SIZE_X = 16;
    public static final int CHUNK_SIZE_Y = 256;
    public static final int CHUNK_SIZE_Z = 16;
    
    // Flags for World.setBlock...() calls.
    public static final int UPDATE_BLOCK_FLAG = 0x1;
    public static final int NOTIFY_CLIENT_FLAG = 0x2;
    public static final int NO_RERENDER_ON_CLIENT_FLAG = 0x4;
    
    // World dimension identifiers.
    public static final int DIM_NETHER = -1;
    public static final int DIM_SURFACE = 0;
    public static final int DIM_END = 1;

    // Highest entity id used for build-in Minecraft entities.
    public static final int HIGHEST_MC_ENTITY_ID = 200;
}
