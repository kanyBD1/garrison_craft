package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record RefreshShopPayload() implements CustomPacketPayload {
    public static final Type<RefreshShopPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WeiModMain.MODID, "refresh_shop"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RefreshShopPayload> STREAM_CODEC =
            StreamCodec.unit(new RefreshShopPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // 服务端接收并处理这个包
    public static void handle(RefreshShopPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player == null || player.level().isClientSide()) return;

            // 1. 在服务端获取真实数据
            var currentData = player.getData(io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData.PLAYER_SHOP_DATA);

            // 2. 检查余额是否足够
            if (currentData.balance() >= 1) {
                // 3. 扣除余额
                var newData = currentData.addBalance(-1);

                // 4. 【核心新增】：重新生成商品列表
                List<ItemStack> pool = io.github.kanybd1.wei.bedwar.bagshop.data.ShopList.getPoolForLevel(newData.level());
                List<ItemStack> shuffled = new ArrayList<>(pool);
                Collections.shuffle(shuffled);
                int count = Math.min(shuffled.size(), 27);
                List<ItemStack> finalList = new ArrayList<>(shuffled.subList(0, count));

                // 5. 【核心新增】：把新余额和新商品列表一起存入服务端数据
                PlayerShopData finalData = newData.withCurrentShopItems(finalList).clearPurchasedIndices();
                player.setData(io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData.PLAYER_SHOP_DATA, finalData);

                // 6. 将包含新余额和新商品列表的最新数据同步回客户端（触发 UI 刷新）
                net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                        (net.minecraft.server.level.ServerPlayer) player,
                        new SyncShopDataPayload(finalData)
                );
            } else {
                // 余额不足，发个消息提示玩家
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("余额不足！"));
            }
        });
    }
}