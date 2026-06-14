package io.github.kanybd1.wei.bedwar.bagshop.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ShopList {
    private static final Map<Integer, List<ItemStack>> SHOP_POOLS = new TreeMap<>();
    private static volatile boolean initialized = false;


    public static List<ItemStack> getPoolForLevel(int level) {
        if (!initialized) {
            synchronized (SHOP_POOLS) {
                if (!initialized) {
                    init();
                    initialized = true;
                }
            }
        }
        return SHOP_POOLS.getOrDefault(level, Collections.emptyList());
    }

    private static void init() {
        SHOP_POOLS.clear();

        registerPool(1, POOL_1);
        registerPool(2, POOL_2);
        registerPool(3, POOL_3);
        registerPool(4, POOL_4);
        registerPool(5, POOL_5);

        System.out.println("[ShopList] 初始化完成，共注册 " + SHOP_POOLS.size() + " 个等级卡池");
    }

    private static final Set<Item> POOL_1 = Set.of(
            Items.APPLE,
            Items.COOKED_SALMON,
            Items.COOKED_COD,
            Items.LEATHER_BOOTS,
            Items.LEATHER_HELMET,
            Items.LEATHER_CHESTPLATE,
            Items.LEATHER_LEGGINGS,
            Items.NAUTILUS_SHELL,
            Items.SNOWBALL,
            Items.WOODEN_SWORD
    );

    private static final Set<Item> POOL_2 = Set.of(
            Items.APPLE,
            Items.COOKED_SALMON,
            Items.COOKED_COD,
            Items.LEATHER_BOOTS,
            Items.LEATHER_HELMET,
            Items.LEATHER_CHESTPLATE,
            Items.LEATHER_LEGGINGS,
            Items.NAUTILUS_SHELL,
            Items.SNOWBALL,
            Items.WOODEN_SWORD,

            Items.STONE_AXE,
            Items.STONE_SWORD,
            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_CHESTPLATE,
            Items.CHAINMAIL_LEGGINGS,
            Items.CHAINMAIL_BOOTS,
            Items.ENDER_EYE,
            Items.IRON_PICKAXE,
            Items.BOOK,
            Items.FISHING_ROD,
            Items.ARROW
    );

    private static final Set<Item> POOL_3 = Set.of(
            Items.APPLE,
            Items.COOKED_SALMON,
            Items.COOKED_COD,
            Items.LEATHER_BOOTS,
            Items.LEATHER_HELMET,
            Items.LEATHER_CHESTPLATE,
            Items.LEATHER_LEGGINGS,
            Items.NAUTILUS_SHELL,
            Items.SNOWBALL,
            Items.WOODEN_SWORD,

            Items.STONE_AXE,
            Items.STONE_SWORD,
            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_BOOTS,
            Items.ENDER_EYE,
            Items.IRON_PICKAXE,
            Items.BOOK,
            Items.FISHING_ROD,
            Items.ARROW,

            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.DIAMOND_PICKAXE,
            Items.HEART_OF_THE_SEA,
            Items.WATER_BUCKET,
            Items.BOW,
            Items.IRON_AXE,
            Items.IRON_SWORD
    );

    private static final Set<Item> POOL_4 = Set.of(
            Items.APPLE,
            Items.COOKED_SALMON,
            Items.COOKED_COD,
            Items.LEATHER_BOOTS,
            Items.LEATHER_HELMET,
            Items.NAUTILUS_SHELL,
            Items.SNOWBALL,
            Items.WOODEN_SWORD,

            Items.STONE_AXE,
            Items.STONE_SWORD,
            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_CHESTPLATE,
            Items.CHAINMAIL_LEGGINGS,
            Items.CHAINMAIL_BOOTS,
            Items.ENDER_EYE,
            Items.IRON_PICKAXE,
            Items.BOOK,
            Items.FISHING_ROD,
            Items.ARROW,

            Items.IRON_HELMET,
            Items.IRON_BOOTS,
            Items.DIAMOND_PICKAXE,
            Items.HEART_OF_THE_SEA,
            Items.WATER_BUCKET,
            Items.BOW,
            Items.IRON_AXE,
            Items.IRON_SWORD,

            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_BOOTS,
            Items.GOLDEN_APPLE,
            Items.NETHERITE_PICKAXE
    );

    private static final Set<Item> POOL_5 = Set.of(
            Items.APPLE,
            Items.COOKED_SALMON,
            Items.COOKED_COD,
            Items.LEATHER_BOOTS,
            Items.LEATHER_HELMET,
            Items.NAUTILUS_SHELL,
            Items.SNOWBALL,
            Items.WOODEN_SWORD,

            Items.STONE_AXE,
            Items.STONE_SWORD,
            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_CHESTPLATE,
            Items.CHAINMAIL_LEGGINGS,
            Items.CHAINMAIL_BOOTS,
            Items.ENDER_EYE,
            Items.IRON_PICKAXE,
            Items.BOOK,
            Items.FISHING_ROD,
            Items.ARROW,

            Items.IRON_HELMET,
            Items.IRON_BOOTS,
            Items.DIAMOND_PICKAXE,
            Items.HEART_OF_THE_SEA,
            Items.WATER_BUCKET,
            Items.BOW,
            Items.IRON_AXE,
            Items.IRON_SWORD,

            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_BOOTS,
            Items.GOLDEN_APPLE,
            Items.NETHERITE_PICKAXE,

            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_AXE,
            Items.DIAMOND_SWORD,
            Items.TOTEM_OF_UNDYING
    );


    @SafeVarargs
    private static void registerPool(int level, Set<Item>... itemSets) {
        List<ItemStack> pool = new ArrayList<>();
        for (Set<Item> itemSet : itemSets) {
            for (Item item : itemSet) {
                pool.add(new ItemStack(item));
            }
        }
        SHOP_POOLS.put(level, Collections.unmodifiableList(pool));
    }

    public static int getShopLevel(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (POOL_1.contains(item)) {return 1;}
        if (POOL_2.contains(item)) {return 2;}
        if (POOL_3.contains(item)) {return 3;}
        if (POOL_4.contains(item)) {return 4;}
        if (POOL_5.contains(item)) {return 5;}
        return 0;
    }
}
