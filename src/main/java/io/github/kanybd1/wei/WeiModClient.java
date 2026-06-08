package io.github.kanybd1.wei;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.gui.ShopScreen;
import io.github.kanybd1.wei.covenant.covenantGui.StackGui;
import io.github.kanybd1.wei.team.gui.TeamGui;
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

@Mod(value = WeiModMain.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = WeiModMain.MODID, value = Dist.CLIENT)
public class WeiModClient {
    public WeiModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {

        event.registerBelow(
                Identifier.fromNamespaceAndPath("minecraft", "hotbar"),
                Identifier.fromNamespaceAndPath(WeiModMain.MODID, "covenant_hud"),
                StackGui.INSTANCE
        );

        event.registerAbove(
                Identifier.fromNamespaceAndPath("minecraft", "hotbar"),
                Identifier.fromNamespaceAndPath(WeiModMain.MODID, "team_hud"),
                TeamGui.INSTANCE
        );
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
