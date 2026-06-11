package io.github.kanybd1.wei.bedwar.bagshop.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public record PlayerShopData(int balance,int level, Set<ItemStack> unlockedItems) {

    private static final Codec<Set<ItemStack>> STRING_SET_CODEC =
            ItemStack.CODEC.listOf().xmap(
                    list -> new HashSet<>(list),
                    set -> new ArrayList<>(set)
            );

    public static final MapCodec<PlayerShopData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("balance").forGetter(PlayerShopData::balance),
                    Codec.INT.fieldOf("level").forGetter(PlayerShopData::level),
                    STRING_SET_CODEC.fieldOf("unlocked_items").forGetter(PlayerShopData::unlockedItems)
            ).apply(instance, PlayerShopData::new)
    );

    public static final Codec<PlayerShopData> CODEC = MAP_CODEC.codec();
    // === 业务逻辑方法（每次修改返回新的 record 实例）===

    public PlayerShopData addBalance(int amount) {
        return new PlayerShopData(this.balance + amount,this.level, this.unlockedItems);
    }

    public boolean tryUnlock(ItemStack itemStack) {
        if (this.unlockedItems.contains(itemStack)) return false;
        Set<ItemStack> newSet = new HashSet<>(this.unlockedItems);
        newSet.add(itemStack);
        return true; // 实际使用时应配合下面的 withUnlocked 方法
    }

    public PlayerShopData withUnlocked(ItemStack itemStack) {
        if (this.unlockedItems.contains(itemStack)) return this;
        Set<ItemStack> newSet = new HashSet<>(this.unlockedItems);
        newSet.add(itemStack);
        return new PlayerShopData(this.balance,this.level, newSet);
    }

    public boolean isUnlocked(ItemStack itemStack) {
        return this.unlockedItems.contains(itemStack);
    }
}