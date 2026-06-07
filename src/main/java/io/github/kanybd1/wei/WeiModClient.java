package io.github.kanybd1.wei;

import io.github.kanybd1.wei.gui.StackGui;
import io.github.kanybd1.wei.gui.TeamGui;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.minecraft.resources.Identifier;

@Mod(value = WeiModMain.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = WeiModMain.MODID, value = Dist.CLIENT)
public class WeiModClient {
    public WeiModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        WeiModMain.LOGGER.info("HELLO FROM CLIENT SETUP");
        WeiModMain.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
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
}
