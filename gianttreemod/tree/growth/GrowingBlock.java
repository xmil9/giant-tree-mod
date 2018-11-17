package xmilcode.gianttreemod.tree.growth;


// Interface that gives a growth strategy context information about
// the block that is the source of the growth.
// Client code of a growth strategy has to provide this.   
public interface GrowingBlock
{
    // DEPRECATED
    public boolean canGrow();
    // Marks the source block as permanently not able to grow anymore.
    public void markAsDoneGrowing();
}
