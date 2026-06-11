package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record LevelUpPayload ()implements CustomPacketPayload {

    public static final Type<LevelUpPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WeiModMain.MODID, "level_up_shop"));

    public static final StreamCodec<RegistryFriendlyByteBuf, LevelUpPayload> STREAM_CODEC =
            StreamCodec.unit(new LevelUpPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LevelUpPayload payload, IPayloadContext context){
        context.enqueueWork(() -> {

            var player = context.player();
            if (player == null || player.level().isClientSide()) return;

            var currentData = player.getData(io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData.PLAYER_SHOP_DATA);

            if (currentData.level() == 5) {
                player.sendSystemMessage(Component.literal("商店等级已最高！当前等级: " + currentData.level()));
                return;
            }

            if (currentData.balance() >= 1){
                PlayerShopData newData = currentData.addBalance(-1).addLevel(1);

                player.setData(AttachmentShopData.PLAYER_SHOP_DATA,newData);

                net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                        (net.minecraft.server.level.ServerPlayer) player,
                        new SyncShopDataPayload(newData)
                );

                player.sendSystemMessage(Component.literal("商店升级成功！当前等级: " + newData.level()));

            } else {
                // 余额不足，发个消息提示玩家
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("余额不足！"));
            }
        });
    }
}
