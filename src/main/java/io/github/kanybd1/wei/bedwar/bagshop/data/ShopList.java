package io.github.kanybd1.wei.bedwar.bagshop.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.*;

public class ShopList {
    public static final Map<Integer, List<ItemStack>> SHOP_POOLS = new TreeMap<>();

    private static boolean initialized = false;

    private static synchronized void ensureInitialized() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    // 原有的 init() 和 registerPool() 保持不变
    public static void init() {
        SHOP_POOLS.clear();
        registerPool(1, FORTRESS_COVENANT_ITEMS, FOREST_COVENANT_ITEMS);
        registerPool(2, MINER_COVENANT_ITEMS, OCEAN_COVENANT_ITEMS);
        registerPool(3, END_COVENANT_ITEMS, KNOWLEDGE_COVENANT_ITEMS, PINPOINT_COVENANT_ITEMS);
    }

    // ... registerPool 方法不变 ...


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

    public static List<ItemStack> getPoolForLevel(int level) {
        return SHOP_POOLS.getOrDefault(level, Collections.emptyList());
    }
}
