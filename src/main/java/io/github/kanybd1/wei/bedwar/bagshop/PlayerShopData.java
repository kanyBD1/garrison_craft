package io.github.kanybd1.wei.bedwar.bagshop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public record PlayerShopData(int balance, Set<String> unlockedItems) {

    // 【关键】改为 public static，供外部（Attachment、网络包等）统一调用
    private static final Codec<Set<String>> STRING_SET_CODEC =
            Codec.STRING.listOf().xmap(
                    list -> new HashSet<>(list),
                    set -> new ArrayList<>(set)
            );

    public static final MapCodec<PlayerShopData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("balance").forGetter(PlayerShopData::balance),
                    STRING_SET_CODEC.fieldOf("unlocked_items").forGetter(PlayerShopData::unlockedItems)
            ).apply(instance, PlayerShopData::new)
    );

    public static final Codec<PlayerShopData> CODEC = MAP_CODEC.codec();
    // === 业务逻辑方法（每次修改返回新的 record 实例）===

    public PlayerShopData addBalance(int amount) {
        return new PlayerShopData(this.balance + amount, this.unlockedItems);
    }

    public boolean tryUnlock(String itemRegistryName) {
        if (this.unlockedItems.contains(itemRegistryName)) return false;
        Set<String> newSet = new HashSet<>(this.unlockedItems);
        newSet.add(itemRegistryName);
        return true; // 实际使用时应配合下面的 withUnlocked 方法
    }

    public PlayerShopData withUnlocked(String itemRegistryName) {
        if (this.unlockedItems.contains(itemRegistryName)) return this;
        Set<String> newSet = new HashSet<>(this.unlockedItems);
        newSet.add(itemRegistryName);
        return new PlayerShopData(this.balance, newSet);
    }

    public boolean isUnlocked(String itemRegistryName) {
        return this.unlockedItems.contains(itemRegistryName);
    }
}