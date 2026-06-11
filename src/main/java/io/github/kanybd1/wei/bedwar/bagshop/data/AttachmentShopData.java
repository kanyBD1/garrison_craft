package io.github.kanybd1.wei.bedwar.bagshop.data;

import io.github.kanybd1.wei.WeiModMain;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Supplier;


public class AttachmentShopData {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WeiModMain.MODID);

    public static final Supplier<AttachmentType<PlayerShopData>> PLAYER_SHOP_DATA =
            ATTACHMENT_TYPES.register("player_shop_data", () -> AttachmentType.<PlayerShopData>builder(() -> new PlayerShopData(0,1,new HashSet<>(),new ArrayList<>(),new HashSet<>()))
                    .serialize(PlayerShopData.MAP_CODEC)
                    .copyOnDeath()
                    .build()
            );
}
