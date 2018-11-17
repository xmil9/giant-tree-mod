package xmilcode.gianttreemod.tree.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xmilcode.mclib.loot.ChestLoot;



public class NormalTreeLoot extends ChestLoot
{
    private static final Item[] FOOD_LOOT = {
        Items.apple, Items.mushroom_stew, Items.wheat, Items.bread, Items.porkchop,
        Items.egg, Items.fish, Items.cake, Items.cookie, Items.beef, Items.chicken,
        Items.melon, Items.potato, Items.carrot, Items.sugar
    };
    
    private static final Item[] MISC_LOOT = {
        Items.bone, Items.book, Items.bowl, Items.bucket, Items.coal, Items.cooked_beef,
        Items.dye, Items.feather, Items.flint, Items.glass_bottle, Items.gold_ingot,
        Items.emerald, Items.golden_pickaxe, Items.golden_shovel, Items.iron_axe,
        Items.iron_hoe, Items.iron_ingot, Items.iron_boots, Items.iron_sword,
        Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.leather,
        Items.leather_boots, Items.leather_chestplate, Items.leather_helmet,
        Items.leather_leggings, Items.milk_bucket, Items.pumpkin_pie, Items.pumpkin_seeds,
        Items.spider_eye, Items.stone_axe, Items.stone_hoe, Items.stone_pickaxe,
        Items.stone_shovel, Items.stone_sword, Items.string, Items.wheat, Items.wheat_seeds,
        Items.wooden_axe, Items.wooden_hoe, Items.wooden_pickaxe, Items.wooden_shovel,
        Items.wooden_sword
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
        slots.add(new ItemStack(Items.diamond, 3 + rand.nextInt(6)));
        slots.add(chooseItemWithQuantity(FOOD_LOOT, 2 + rand.nextInt(5), rand));
        slots.add(chooseItemWithQuantity(FOOD_LOOT, 2 + rand.nextInt(5), rand));
        slots.add(chooseItemWithQuantity(MISC_LOOT, 1, rand));
        slots.add(chooseItemWithQuantity(MISC_LOOT, 1, rand));
        slots.add(chooseItemWithQuantity(MISC_LOOT, 1, rand));
        slots.add(chooseItemWithQuantity(MISC_LOOT, 1, rand));
        return slots;
    }
    
    
    private static ItemStack chooseItemWithQuantity(Item[] items, int quantity, Random rand)
    {
        return new ItemStack(items[rand.nextInt(items.length)], quantity);
    }
}
