package io.github.kanybd1.wei.bedwar.bagshop.data;

import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.*;

public class ShopList {
    private static final Map<Integer, List<ItemStack>> SHOP_POOLS = new TreeMap<>();
    private static volatile boolean initialized = false;

    public static List<ItemStack> getPoolForLevel(int level) {
        if (!initialized) {
            synchronized (SHOP_POOLS) {
                if (!initialized) { // ✅ 双重检查锁定
                    init();
                    initialized = true;
                }
            }
        }
        return SHOP_POOLS.getOrDefault(level, Collections.emptyList());
    }

    private static void init() {
        SHOP_POOLS.clear();

        registerPool(1, FORTRESS_COVENANT_ITEMS, FOREST_COVENANT_ITEMS);
        registerPool(2, MINER_COVENANT_ITEMS, OCEAN_COVENANT_ITEMS);
        registerPool(3, END_COVENANT_ITEMS, KNOWLEDGE_COVENANT_ITEMS, PINPOINT_COVENANT_ITEMS);

        System.out.println("[ShopList] 初始化完成，共注册 " + SHOP_POOLS.size() + " 个等级卡池");
    }


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

}
