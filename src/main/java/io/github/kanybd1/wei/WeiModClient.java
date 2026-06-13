package io.github.kanybd1.wei;

import io.github.kanybd1.wei.client.ClientTeamData;
import io.github.kanybd1.wei.gui.StackGui;
import io.github.kanybd1.wei.gui.TeamGui;
import io.github.kanybd1.wei.network.SyncTeamPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

@Mod(value = WeiModMain.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = WeiModMain.MODID, value = Dist.CLIENT)
public class WeiModClient {
    public WeiModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

    }

    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event) {
        WeiModMain.LOGGER.info("HELLO FROM CLIENT SETUP");
        WeiModMain.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    private static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {

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
    private static void registerPayloadClient(final RegisterClientPayloadHandlersEvent event) {
        event.register(SyncTeamPacket.TYPE, WeiModClient::handle);
    }

    /**
     * 封包 {@code SyncTeamPacket} 数据处理方法。
     */
    @OnlyIn(Dist.CLIENT)
    public static void handle(final SyncTeamPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Objects.isNull(packet.teamName()) || packet.teamName().isEmpty()) {
                ClientTeamData.INSTANCE.clearTeam();
                return;
            }

            ClientTeamData.INSTANCE.updateTeam(packet.teamName(), packet.members());
        });
    }
}
