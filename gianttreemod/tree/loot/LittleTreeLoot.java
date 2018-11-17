package xmilcode.gianttreemod.tree.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xmilcode.mclib.loot.ChestLoot;


public class LittleTreeLoot extends ChestLoot
{
    private static final Item[] FOOD_LOOT = {
        Items.apple, Items.mushroom_stew, Items.wheat, Items.bread, Items.porkchop,
        Items.egg, Items.fish, Items.cake, Items.cookie, Items.beef, Items.chicken,
        Items.melon, Items.potato, Items.carrot, Items.sugar
    };
    
    private static final Item[] MISC_LOOT = {
        Items.bone, Items.book, Items.bowl, Items.bucket, Items.coal, Items.cooked_beef,
        Items.dye, Items.feather, Items.flint, Items.glass_bottle, Items.leather,
        Items.milk_bucket, Items.pumpkin_pie, Items.pumpkin_seeds, Items.spider_eye,
        Items.string, Items.wheat, Items.wheat_seeds
    };
    
    private static final Item[] TOOLS_ARMOR_LOOT = {
        Items.iron_axe, Items.iron_hoe, Items.iron_ingot, Items.iron_boots,
        Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings,
        Items.leather_boots, Items.leather_chestplate, Items.leather_helmet,
        Items.leather_leggings, Items.stone_axe, Items.stone_hoe, Items.stone_pickaxe,
        Items.stone_shovel, Items.stone_sword, Items.wooden_axe, Items.wooden_hoe,
        Items.wooden_pickaxe, Items.wooden_shovel, Items.wooden_sword
    };
    

    @Override
    public boolean haveLoot()
    {
        return true;
    }

    @Override
    protected List<ItemStack> getItemsForChestSlots(int maxSlots, Random rand)
    {
        List<ItemStack> slots = new ArrayList<ItemStack>();
        slots.add(new ItemStack(Items.diamond, 1 + rand.nextInt(2)));
        slots.add(chooseItemWithQuantity(FOOD_LOOT, 1 + rand.nextInt(3), rand));
        slots.add(chooseItemWithQuantity(MISC_LOOT, 1, rand));
        slots.add(chooseItemWithQuantity(TOOLS_ARMOR_LOOT, 1, rand));
        return slots;
    }
    
    
    private static ItemStack chooseItemWithQuantity(Item[] items, int quantity, Random rand)
    {
        return new ItemStack(items[rand.nextInt(items.length)], quantity);
    }
}
