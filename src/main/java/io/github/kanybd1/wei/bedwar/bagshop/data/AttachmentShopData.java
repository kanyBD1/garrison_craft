package io.github.kanybd1.wei.bedwar.bagshop.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;


public class AttachmentShopData {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WeiModMain.MODID);

    private static final Codec<Set<ItemStack>> STRING_SET_CODEC =
            ItemStack.CODEC.listOf().xmap(
                    list -> new HashSet<>(list),
                    set -> new ArrayList<>(set)
            );

    public static final Codec<PlayerShopData> SHOP_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("balance").forGetter(PlayerShopData::balance),
                    Codec.INT.fieldOf("level").forGetter(PlayerShopData::level),
                    STRING_SET_CODEC.fieldOf("unlocked_items").forGetter(PlayerShopData::unlockedItems)
            ).apply(instance, PlayerShopData::new)
    );

    public static final Supplier<AttachmentType<PlayerShopData>> PLAYER_SHOP_DATA =
            ATTACHMENT_TYPES.register("player_shop_data", () -> AttachmentType.<PlayerShopData>builder(() -> new PlayerShopData(999,1,new HashSet<>()))
                    .serialize(PlayerShopData.MAP_CODEC)
                    .copyOnDeath()
                    .build()
            );
}
