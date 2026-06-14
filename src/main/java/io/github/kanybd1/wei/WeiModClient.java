package io.github.kanybd1.wei;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.gui.BalanceGui;
import io.github.kanybd1.wei.bedwar.bagshop.gui.ShopScreen;
import io.github.kanybd1.wei.covenant.covenantGui.StackGui;
import io.github.kanybd1.wei.team.gui.TeamGui;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static io.github.kanybd1.wei.WeiModMain.MODID;

@Mod(value = MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class WeiModClient {
    public WeiModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {

        // 2. 将 covenant_hud 注册在【原版热栏】的下方
        event.registerBelow(
                VanillaGuiLayers.HOTBAR, // 直接使用原版热栏作为基准
                Identifier.fromNamespaceAndPath(MODID, "covenant_hud"),
                StackGui.INSTANCE
        );

        // 3. 将 team_hud 注册在【原版热栏】的上方
        event.registerAbove(
                VanillaGuiLayers.HOTBAR, // 直接使用原版热栏作为基准
                Identifier.fromNamespaceAndPath(MODID, "team_hud"),
                TeamGui.INSTANCE
        );

        // 4. 注册在最顶层的 GUI (这个不需要改)
        event.registerAboveAll(
                Identifier.fromNamespaceAndPath(MODID, "balance_gui"),
                BalanceGui.INSTANCE
        );
    }

    @SubscribeEvent
    static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(WeiModMain.SHOP_MENU, ShopScreen::new);
    }

    @SubscribeEvent
    static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ClientShopData.clear();
    }

}
