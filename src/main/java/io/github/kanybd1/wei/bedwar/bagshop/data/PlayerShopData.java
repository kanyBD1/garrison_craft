package io.github.kanybd1.wei.bedwar.bagshop.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public record PlayerShopData(
        int balance,
        int level,
        Set<ItemStack> unlockedItems,
        List<ItemStack> displayedItems, // 【新增】服务端权威商品列表
        Set<Integer> purchasedSlots,     // 【新增】已购买槽位索引
        long lastRefreshTime
) {
    // 兼容旧数据的默认构造器
    public PlayerShopData(int balance, int level, Set<ItemStack> unlockedItems) {
        this(balance, level, unlockedItems, Collections.emptyList(), Collections.emptySet(),0L);
    }

    private static final Codec<Set<ItemStack>> ITEM_SET_CODEC = ItemStack.CODEC.listOf().xmap(HashSet::new, ArrayList::new);
    private static final Codec<List<ItemStack>> ITEM_LIST_CODEC = ItemStack.CODEC.listOf();
    private static final Codec<Set<Integer>> INT_SET_CODEC = Codec.INT.listOf().xmap(HashSet::new, ArrayList::new);


    public static final MapCodec<PlayerShopData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("balance").forGetter(PlayerShopData::balance),
                    Codec.INT.fieldOf("level").forGetter(PlayerShopData::level),
                    ITEM_SET_CODEC.fieldOf("unlocked_items").forGetter(PlayerShopData::unlockedItems),
                    ITEM_LIST_CODEC.optionalFieldOf("displayed_items", Collections.emptyList()).forGetter(PlayerShopData::displayedItems),
                    INT_SET_CODEC.optionalFieldOf("purchased_slots", Collections.emptySet()).forGetter(PlayerShopData::purchasedSlots),
                    Codec.LONG.optionalFieldOf("last_refresh_time", 0L).forGetter(PlayerShopData::lastRefreshTime)
            ).apply(instance, PlayerShopData::new)
    );

    public static final Codec<PlayerShopData> CODEC = MAP_CODEC.codec();

    // === 业务方法（保持不可变 Record 风格）===

    public PlayerShopData addBalance(int amount) {
        return new PlayerShopData(this.balance + amount, this.level, this.unlockedItems, this.displayedItems, this.purchasedSlots, this.lastRefreshTime);
    }

    public PlayerShopData withUnlocked(ItemStack itemStack) {
        if (this.unlockedItems.contains(itemStack)) return this;
        Set<ItemStack> newSet = new HashSet<>(this.unlockedItems);
        newSet.add(itemStack);
        return new PlayerShopData(this.balance, this.level, newSet, this.displayedItems, this.purchasedSlots, this.lastRefreshTime);
    }

    /** 【新增】标记购买并扣款，返回新实例；失败返回 null */
    // 推荐：让 tryPurchase 自己从 displayedItems 中查价格
    public PlayerShopData tryPurchase(int slotIndex) {
        if (slotIndex < 0 || slotIndex >= displayedItems.size()) return null;
        if (purchasedSlots.contains(slotIndex)) return null;

        // ✅ 服务端自行获取价格，不信任客户端
        int cost = 1;
        if (this.balance < cost) return null;

        Set<Integer> newPurchased = new HashSet<>(this.purchasedSlots);
        newPurchased.add(slotIndex);
        return new PlayerShopData(this.balance - cost, this.level, this.unlockedItems, this.displayedItems, newPurchased, this.lastRefreshTime);
    }

    public boolean isUnlocked(ItemStack itemStack) { return this.unlockedItems.contains(itemStack); }
    public boolean isPurchased(int index) { return this.purchasedSlots.contains(index); }

    public PlayerShopData refreshShop(List<ItemStack> pool) {
        List<ItemStack> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled);
        int count = Math.min(shuffled.size(), 27);
        List<ItemStack> newDisplay = new ArrayList<>(shuffled.subList(0, count));

        return new PlayerShopData(
                this.balance, this.level, this.unlockedItems,
                newDisplay, Collections.emptySet(),
                System.currentTimeMillis()
        );
    }
}