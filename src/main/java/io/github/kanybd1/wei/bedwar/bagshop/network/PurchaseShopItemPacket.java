package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PurchaseShopItemPacket(int slotIndex) implements CustomPacketPayload {

    public static final Type<PurchaseShopItemPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("wei", "purchase_shop_item"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PurchaseShopItemPacket> STREAM_CODEC =
            StreamCodec.ofMember(
                    (p, b) -> b.writeInt(p.slotIndex),
                    b -> new PurchaseShopItemPacket(b.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer serverPlayer)) return;

            PlayerShopData currentData = serverPlayer.getData(AttachmentShopData.PLAYER_SHOP_DATA);

            // ⭐ 安全校验：防止越界访问
            if (slotIndex < 0 || slotIndex >= currentData.displayedItems().size()) return;

            // ⭐ 先获取要发放的物品副本（在 tryPurchase 修改数据之前）
            ItemStack itemToGive = currentData.displayedItems().get(slotIndex).copy();
            if (itemToGive.isEmpty()) return; // 已购买或空槽位，直接拒绝

            PlayerShopData newData = currentData.tryPurchase(slotIndex);
            if (newData == null) return; // 余额不足或其他失败条件

            // 写回 Attachment
            serverPlayer.setData(AttachmentShopData.PLAYER_SHOP_DATA, newData);

            // 发放物品
            serverPlayer.getInventory().add(itemToGive);

            // 同步最新状态给客户端
            serverPlayer.connection.send(new SyncShopDataPayload(newData));
        });
    }
}