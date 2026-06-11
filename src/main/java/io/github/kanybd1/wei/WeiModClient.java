package io.github.kanybd1.wei;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.gui.ShopScreen;
import io.github.kanybd1.wei.bedwar.bagshop.network.RefreshShopPacket;
import io.github.kanybd1.wei.bedwar.bagshop.network.SyncShopDataPayload;
import io.github.kanybd1.wei.covenant.covenantGui.StackGui;
import io.github.kanybd1.wei.team.gui.TeamGui;
import io.github.kanybd1.wei.team.network.SyncTeamPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import static io.github.kanybd1.wei.WeiModMain.MODID;

@Mod(value = MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class WeiModClient {
    public WeiModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {

        event.registerBelow(
                Identifier.fromNamespaceAndPath("minecraft", "hotbar"),
                Identifier.fromNamespaceAndPath(MODID, "covenant_hud"),
                StackGui.INSTANCE
        );

        event.registerAbove(
                Identifier.fromNamespaceAndPath("minecraft", "hotbar"),
                Identifier.fromNamespaceAndPath(MODID, "team_hud"),
                TeamGui.INSTANCE
        );
    }
    @SubscribeEvent
    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MODID);
        registrar.playToClient(SyncTeamPacket.TYPE, SyncTeamPacket.STREAM_CODEC, SyncTeamPacket::handle);
        registrar.playToClient(SyncShopDataPayload.TYPE, SyncShopDataPayload.STREAM_CODEC, SyncShopDataPayload::handle);
        registrar.playToClient(RefreshShopPacket.TYPE, RefreshShopPacket.STREAM_CODEC, RefreshShopPacket::handle);
    }

    @SubscribeEvent
    static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(WeiModMain.SHOP_MENU, ShopScreen::new);
    }

    // ⭐ 2. 玩家登出时清理客户端缓存（自动被 EventBusSubscriber 捕获）
    @SubscribeEvent
    static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ClientShopData.clear();
    }
}
