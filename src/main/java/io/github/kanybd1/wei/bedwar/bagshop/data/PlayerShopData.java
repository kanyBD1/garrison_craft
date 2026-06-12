package io.github.kanybd1.wei.bedwar.bagshop.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record PlayerShopData(int balance,
                             int level,
                             Set<ItemStack> unlockedItems,
                             List<ItemStack> currentShopItems,
                             Set<Integer> purchasedIndices) {


    private static final Codec<Set<ItemStack>> ITEM_SET_CODEC =
            ItemStack.CODEC.listOf().xmap(HashSet::new, ArrayList::new);

    private static final Codec<List<ItemStack>> ITEM_LIST_CODEC =
            ItemStack.CODEC.listOf();

    private static final Codec<Set<Integer>> INT_SET_CODEC =
            Codec.INT.listOf().xmap(HashSet::new, ArrayList::new);

    public static final MapCodec<PlayerShopData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("balance").forGetter(PlayerShopData::balance),
                    Codec.INT.fieldOf("level").forGetter(PlayerShopData::level),
                    ITEM_SET_CODEC.fieldOf("unlocked_items").forGetter(PlayerShopData::unlockedItems),
                    ITEM_LIST_CODEC.optionalFieldOf("current_shop_items", new ArrayList<>()).forGetter(PlayerShopData::currentShopItems),
                    INT_SET_CODEC.optionalFieldOf("purchased_indices", new HashSet<>()).forGetter(PlayerShopData::purchasedIndices) // 兼容旧存档
            ).apply(instance, PlayerShopData::new)
    );

    public static final Codec<PlayerShopData> CODEC = MAP_CODEC.codec();


    public PlayerShopData addBalance(int amount) {
        return new PlayerShopData(this.balance + amount, this.level, this.unlockedItems, this.currentShopItems, this.purchasedIndices);
    }

    public PlayerShopData withCurrentShopItems(List<ItemStack> items) {
        return new PlayerShopData(this.balance, this.level, this.unlockedItems, items, this.purchasedIndices);
    }

    public PlayerShopData addPurchasedIndex(int index) {
        Set<Integer> newSet = new HashSet<>(this.purchasedIndices);
        newSet.add(index);
        return new PlayerShopData(this.balance, this.level, this.unlockedItems, this.currentShopItems, newSet);
    }

    public PlayerShopData clearPurchasedIndices() {
        return new PlayerShopData(this.balance, this.level, this.unlockedItems, this.currentShopItems, new HashSet<>());
    }

    public PlayerShopData addLevel(int i) {
        return new PlayerShopData(this.balance, this.level+i, this.unlockedItems, this.currentShopItems, this.purchasedIndices);
    }
}