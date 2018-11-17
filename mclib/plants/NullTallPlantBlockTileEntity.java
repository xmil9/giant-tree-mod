package xmilcode.mclib.plants;


// Null object pattern for TallPlantBlockTileEntity objects.
public class NullTallPlantBlockTileEntity extends TallPlantBlockTileEntity 
{
    @Override // TallPlantBlockTileEntity
    public int getHeight()
    {
        return 0;
    }
    
    
    @Override // TallPlantBlockTileEntity
    public void setHeight(int height)
    {
    }
}
