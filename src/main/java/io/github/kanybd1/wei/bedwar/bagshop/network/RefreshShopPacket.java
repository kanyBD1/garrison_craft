package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.ShopList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record RefreshShopPacket() implements CustomPacketPayload {

    public static final Type<RefreshShopPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("wei", "refresh_shop"));

    // 无参包，StreamCodec 使用 unit
    public static final StreamCodec<RegistryFriendlyByteBuf, RefreshShopPacket> STREAM_CODEC =
            StreamCodec.unit(new RefreshShopPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        // RefreshShopPacket.handle() 内
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer serverPlayer)) return;

            PlayerShopData currentData = serverPlayer.getData(AttachmentShopData.PLAYER_SHOP_DATA);

            // ⭐ 冷却检查（确保 PlayerShopData 已添加 lastRefreshTime 字段）
            long now = System.currentTimeMillis();
            if (now - currentData.lastRefreshTime() < 3000L) return;

            // ✅ 从 ShopList 获取当前玩家等级对应的商品池
            List<ItemStack> shopPool = ShopList.getPoolForLevel(currentData.level());
            if (shopPool.isEmpty()) {
                WeiModMain.LOGGER.warn("玩家 {} 的等级 {} 没有对应的商店卡池",
                        serverPlayer.getName().getString(), currentData.level());
                return;
            }

            // ✅ 调用正确的刷新方法并传入商品池
            PlayerShopData newData = currentData.refreshShop(shopPool);

            serverPlayer.setData(AttachmentShopData.PLAYER_SHOP_DATA, newData);
            serverPlayer.connection.send(new SyncShopDataPayload(newData));
        });
    }
}