package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.gui.ShopScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncShopDataPayload(PlayerShopData data) implements CustomPacketPayload {

    public static final Type<SyncShopDataPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WeiModMain.MODID, "sync_shop_data"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncShopDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.fromCodec(PlayerShopData.CODEC),
                    SyncShopDataPayload::data,
                    SyncShopDataPayload::new
            );

    // ⭐ 客户端处理方法
    public static void handle(SyncShopDataPayload payload, IPayloadContext context) {
        // 网络包在 Netty 线程接收，必须切回游戏主线程才能操作 UI 和客户端数据
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();

            // 1. 写入客户端缓存
            ClientShopData.update(payload.data());

            // 2. 如果玩家当前正看着商店界面，立即刷新防止显示旧数据
            if (mc.screen instanceof ShopScreen shopScreen) {
                shopScreen.refreshData();
            }
        });
    }

    public SyncShopDataPayload(RegistryFriendlyByteBuf buf) {
        this(STREAM_CODEC.decode(buf).data());
    }
}