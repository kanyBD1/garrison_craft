package io.github.kanybd1.wei.bedwar.bagshop.data;

import io.github.kanybd1.wei.WeiModMain;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.function.Supplier;

public class AttachmentShopData {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WeiModMain.MODID);



    public static final Supplier<AttachmentType<PlayerShopData>> PLAYER_SHOP_DATA =
            ATTACHMENT_TYPES.register("player_shop_data", () -> AttachmentType.<PlayerShopData>builder(() ->
                                    // 【修改】使用完整的五参数构造器，显式初始化所有字段
                                    new PlayerShopData(
                                            999,                    // balance
                                            1,                      // level
                                            Collections.emptySet(), // unlockedItems
                                            Collections.emptyList(),// displayedItems (服务端权威商品列表)
                                            Collections.emptySet(), // purchasedSlots (已购买槽位)
                                            0L
                                    )
                            )
                            // 使用 PlayerShopData 内部定义的完整 MAP_CODEC
                            // 它包含了全部5个字段的序列化/反序列化逻辑
                            .serialize(PlayerShopData.MAP_CODEC)
                            .copyOnDeath()
                            .build()
            );
}