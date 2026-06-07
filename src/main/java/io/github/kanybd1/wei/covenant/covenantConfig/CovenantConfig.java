package io.github.kanybd1.wei.covenant.covenantConfig;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Set;

public class CovenantConfig {
    public static Set<Item> FORTRESS_COVENANT_ITEMS = Set.of(
            Items.STONE_AXE,
            Items.STONE_SWORD,

            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_CHESTPLATE,
            Items.CHAINMAIL_LEGGINGS,
            Items.CHAINMAIL_BOOTS,

            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,

            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS,

            Items.SHIELD,
            Items.MACE
    );
    public static Set<Item> END_COVENANT_ITEMS = Set.of(
            Items.ENDER_EYE,
            Items.ENDER_PEARL
    );
    public static Set<Item> MINER_COVENANT_ITEMS = Set.of(
            Items.IRON_PICKAXE,
            Items.DIAMOND_PICKAXE,
            Items.TNT,
            Items.NETHERITE_PICKAXE,
            Items.WATER_BUCKET,
            Items.LAVA_BUCKET,
            Items.BUCKET
    );

    public static Set<Item> OCEAN_COVENANT_ITEMS = Set.of(
            Items.TRIDENT,
            Items.COOKED_COD,
            Items.COOKED_SALMON,
            Items.FISHING_ROD,
            Items.HEART_OF_THE_SEA,
            Items.NAUTILUS_SHELL,
            Items.TURTLE_HELMET
    );

    public static Set<Item> KNOWLEDGE_COVENANT_ITEMS = Set.of(
            Items.BOOK,
            Items.WRITABLE_BOOK
    );

    public static final Set<Item> FOREST_COVENANT_ITEMS = Set.of(
            Items.APPLE,
            Items.BOW,
            Items.LEATHER_BOOTS,
            Items.LEATHER_HELMET,
            Items.LEATHER_CHESTPLATE,
            Items.LEATHER_LEGGINGS,
            Items.VINE,
            Items.COCOA_BEANS,
            Items.CROSSBOW
    );

    public static final Set<Item> PINPOINT_COVENANT_ITEMS = Set.of(
            Items.ARROW,
            Items.SPECTRAL_ARROW,
            Items.TIPPED_ARROW,
            Items.SNOWBALL,
            Items.WIND_CHARGE,
            Items.FIRE_CHARGE,
            Items.EGG
    );



    public static Object2IntMap<Item> FORTRESS_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public static Object2IntMap<Item> END_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public static Object2IntMap<Item> MINER_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public static Object2IntMap<Item> OCEAN_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public static Object2IntMap<Item> KNOWLEDGE_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public static Object2IntMap<Item> FOREST_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public static Object2IntMap<Item> PINPOINT_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();

    public static void init(){


        FORTRESS_COVENANT_ITEM_VALUE.put(Items.STONE_SWORD, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.STONE_AXE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_BOOTS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_BOOTS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_BOOTS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.SHIELD, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.MACE, 1);


        END_COVENANT_ITEM_VALUE.put(Items.ENDER_EYE, 1);
        END_COVENANT_ITEM_VALUE.put(Items.ENDER_PEARL, 1);


        MINER_COVENANT_ITEM_VALUE.put(Items.IRON_PICKAXE, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.DIAMOND_PICKAXE, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.TNT, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.NETHERITE_PICKAXE, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.BUCKET, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.WATER_BUCKET, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.LAVA_BUCKET, 1);


        OCEAN_COVENANT_ITEM_VALUE.put(Items.TRIDENT, 1);
        OCEAN_COVENANT_ITEM_VALUE.put(Items.FISHING_ROD, 1);
        OCEAN_COVENANT_ITEM_VALUE.put(Items.COOKED_COD, 1);
        OCEAN_COVENANT_ITEM_VALUE.put(Items.COOKED_SALMON, 1);
        OCEAN_COVENANT_ITEM_VALUE.put(Items.HEART_OF_THE_SEA, 1);
        OCEAN_COVENANT_ITEM_VALUE.put(Items.TURTLE_HELMET, 1);
        OCEAN_COVENANT_ITEM_VALUE.put(Items.NAUTILUS_SHELL, 1);


        KNOWLEDGE_COVENANT_ITEM_VALUE.put(Items.BOOK, 1);
        KNOWLEDGE_COVENANT_ITEM_VALUE.put(Items.WRITABLE_BOOK, 1);


        FOREST_COVENANT_ITEM_VALUE.put(Items.COCOA_BEANS, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.APPLE, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.VINE, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.BOW, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.LEATHER_BOOTS, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.LEATHER_HELMET, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.LEATHER_CHESTPLATE, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.LEATHER_LEGGINGS, 1);
        FOREST_COVENANT_ITEM_VALUE.put(Items.CROSSBOW, 1);



        PINPOINT_COVENANT_ITEM_VALUE.put(Items.ARROW,1);
        PINPOINT_COVENANT_ITEM_VALUE.put(Items.TIPPED_ARROW,1);
        PINPOINT_COVENANT_ITEM_VALUE.put(Items.SPECTRAL_ARROW,1);
        PINPOINT_COVENANT_ITEM_VALUE.put(Items.EGG, 1);
        PINPOINT_COVENANT_ITEM_VALUE.put(Items.SNOWBALL, 1);
        PINPOINT_COVENANT_ITEM_VALUE.put(Items.FIRE_CHARGE, 1);
        PINPOINT_COVENANT_ITEM_VALUE.put(Items.WIND_CHARGE, 1);
    }
}
