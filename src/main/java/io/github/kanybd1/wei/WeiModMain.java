package io.github.kanybd1.wei;

import com.mojang.logging.LogUtils;
import io.github.kanybd1.wei.bedwar.bagshop.ShopMenu;
import io.github.kanybd1.wei.covenant.CovenantManager;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.network.SyncTeamPacket;
import io.github.kanybd1.wei.party1.TeamManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(WeiModMain.MODID)
public class WeiModMain {
    public static final String MODID = "wei";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CovenantManager COVENANT_MANAGER = new CovenantManager();
    public static TeamManager TEAM_MANAGER = new TeamManager();

    public WeiModMain(IEventBus modEventBus, ModContainer modContainer) {
        CovenantConfig.init();
        EffectRegister.EVENTS.register(modEventBus);
        StackAttachmentType.ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MODID);

        registrar.playToClient(
                SyncTeamPacket.TYPE,
                SyncTeamPacket.STREAM_CODEC,
                SyncTeamPacket::handle
        );
    }
    public class ModMenus {
        public static final DeferredRegister<MenuType<?>> MENUS =
                DeferredRegister.create(BuiltInRegistries.MENU, WeiModMain.MODID);

        public static final DeferredHolder<MenuType<?>, MenuType<ShopMenu>> SHOP_MENU =
                MENUS.register("shop_menu", () -> new MenuType<>(
                        ShopMenu::new,
                        FeatureFlagSet.of()
                ));
    }
}