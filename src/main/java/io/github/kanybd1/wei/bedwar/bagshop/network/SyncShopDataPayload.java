package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncShopDataPayload(PlayerShopData data) implements CustomPacketPayload {

    public static final Type<SyncShopDataPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("wei", "sync_shop_state"));

    // ✅ 使用 composite + fromCodecWithRegistries 桥接 Codec → StreamCodec
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncShopDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.fromCodecWithRegistries(PlayerShopData.CODEC),
                    SyncShopDataPayload::data,
                    SyncShopDataPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // ✅ 添加 @OnlyIn(Dist.CLIENT) 防止服务端加载客户端类导致崩溃
    @OnlyIn(Dist.CLIENT)
    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> ClientShopData.update(data));
    }
}