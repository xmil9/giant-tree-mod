package xmilcode.gianttreemod.tree.loot;

import xmilcode.gianttreemod.config.GtConfigModule;
import xmilcode.gianttreemod.tree.GiantTreeModule;
import xmilcode.mclib.loot.Loot;


public class TreeLootFactory
{
    public static Loot createTreeLoot()
    {
        switch (GiantTreeModule.lootSettings.lootQuantity())
        {
            case LITTLE:
                return new LittleTreeLoot();
            case NORMAL:
                return new NormalTreeLoot();
            case LOTS:
                return new LotsTreeLoot();
            default:
                throw new IllegalStateException();
        }
    }
}
