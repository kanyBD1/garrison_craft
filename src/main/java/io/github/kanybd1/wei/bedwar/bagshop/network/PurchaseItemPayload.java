package io.github.kanybd1.wei.bedwar.bagshop.network;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.ShopList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static io.github.kanybd1.wei.bedwar.bagshop.data.ShopList.getShopLevel;

public record PurchaseItemPayload(int itemIndex) implements CustomPacketPayload {
    public static final Type<PurchaseItemPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WeiModMain.MODID, "purchase_item"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PurchaseItemPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, PurchaseItemPayload::itemIndex,
                    PurchaseItemPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PurchaseItemPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer serverPlayer)) return;

            PlayerShopData currentData = serverPlayer.getData(AttachmentShopData.PLAYER_SHOP_DATA);

            if (payload.itemIndex() < 0 || payload.itemIndex() >= currentData.currentShopItems().size()) {
                serverPlayer.sendSystemMessage(Component.literal("无效的商品索引！"));
                return;
            }

            ItemStack itemToBuy = currentData.currentShopItems().get(payload.itemIndex());

            int price = getShopLevel(itemToBuy);
            ItemStack arrow = new ItemStack(Items.ARROW,8);


            if (currentData.balance() >= price) {
                if(ItemStack.isSameItem(itemToBuy,arrow)) {
                    itemToBuy = arrow;
                }
                PlayerShopData newData = currentData.addBalance(-price).addPurchasedIndex(payload.itemIndex());
                serverPlayer.setData(AttachmentShopData.PLAYER_SHOP_DATA, newData);



                PacketDistributor.sendToPlayer(serverPlayer, new SyncShopDataPayload(newData));


                if (!serverPlayer.getInventory().add(itemToBuy.copy())) {
                    serverPlayer.drop(itemToBuy.copy(), false);
                }


                PacketDistributor.sendToPlayer(serverPlayer, new SyncShopDataPayload(newData));
            } else {
                serverPlayer.sendSystemMessage(Component.literal("余额不足！"));
            }
        });
    }
}