package io.github.kanybd1.wei.bedwar.bagshop.handler;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.network.SyncShopDataPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = WeiModMain.MODID)
public class BalanceHandler {

    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {

        Entity killer = event.getSource().getEntity();


        if (!(killer instanceof ServerPlayer player)) {
            return;
        }

        if (player.level().isClientSide()) return;

        PlayerShopData currentData = player.getData(AttachmentShopData.PLAYER_SHOP_DATA);
        PlayerShopData newData = currentData.addBalance(100);

        player.setData(AttachmentShopData.PLAYER_SHOP_DATA, newData);

        PacketDistributor.sendToPlayer(player, new SyncShopDataPayload(newData));
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {

            PlayerShopData data = serverPlayer.getData(AttachmentShopData.PLAYER_SHOP_DATA);

            PacketDistributor.sendToPlayer(serverPlayer, new SyncShopDataPayload(data));
        }
    }
}
