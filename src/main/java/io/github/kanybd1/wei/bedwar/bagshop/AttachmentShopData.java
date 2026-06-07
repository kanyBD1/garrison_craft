package io.github.kanybd1.wei.bedwar.bagshop;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.kanybd1.wei.WeiModMain;
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

    // 【关键】将 Set <-> List 转换 Codec 提取为静态常量，切断 RecordCodecBuilder 中的泛型推断链
    private static final Codec<Set<String>> STRING_SET_CODEC =
            Codec.STRING.listOf().xmap(
                    list -> new HashSet<>(list),
                    set -> new ArrayList<>(set)
            );

    // 【关键】将 PlayerShopData 的 Codec 也提取为静态常量，避免在 register lambda 中嵌套复杂泛型
    public static final Codec<PlayerShopData> SHOP_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("balance").forGetter(PlayerShopData::balance),
                    STRING_SET_CODEC.fieldOf("unlocked_items").forGetter(PlayerShopData::unlockedItems)
            ).apply(instance, PlayerShopData::new)
    );

    public static final Supplier<AttachmentType<PlayerShopData>> PLAYER_SHOP_DATA =
            ATTACHMENT_TYPES.register("player_shop_data", () -> AttachmentType.<PlayerShopData>builder(() -> new PlayerShopData(0, new HashSet<>()))
                    .serialize(PlayerShopData.MAP_CODEC)
                    .copyOnDeath() // 如果死亡不保留商店数据，请删除此行
                    .build()
            );
}
