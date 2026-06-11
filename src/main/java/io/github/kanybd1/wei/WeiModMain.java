package io.github.kanybd1.wei;

import com.mojang.logging.LogUtils;
import io.github.kanybd1.wei.bedwar.bagshop.ModItem;
import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import io.github.kanybd1.wei.bedwar.bagshop.network.PurchaseShopItemPacket;
import io.github.kanybd1.wei.bedwar.bagshop.network.RefreshShopPacket;
import io.github.kanybd1.wei.bedwar.bagshop.network.SyncShopDataPayload;
import io.github.kanybd1.wei.covenant.CovenantManager;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.team.network.SyncTeamPacket;
import io.github.kanybd1.wei.team.TeamManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(WeiModMain.MODID)
public class WeiModMain {
    public static final String MODID = "wei";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CovenantManager COVENANT_MANAGER = new CovenantManager();
    public static TeamManager TEAM_MANAGER = new TeamManager();

    public static ModItem SHOP_OPENER;
    public static MenuType<ShopMenu> SHOP_MENU;

    public WeiModMain(IEventBus modEventBus, ModContainer modContainer) {
        CovenantConfig.init();
        EffectRegister.EVENTS.register(modEventBus);
        StackAttachmentType.ATTACHMENT_TYPES.register(modEventBus);
        AttachmentShopData.ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::onRegisterItems);
        modEventBus.addListener(this::onRegisterMenus);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MODID);
        registrar.playToClient(SyncTeamPacket.TYPE, SyncTeamPacket.STREAM_CODEC, SyncTeamPacket::handle);
        registrar.playToClient(SyncShopDataPayload.TYPE, SyncShopDataPayload.STREAM_CODEC, SyncShopDataPayload::handle);
        registrar.playToServer(PurchaseShopItemPacket.TYPE, PurchaseShopItemPacket.STREAM_CODEC,PurchaseShopItemPacket::handle);
        registrar.playToClient(RefreshShopPacket.TYPE, RefreshShopPacket.STREAM_CODEC, RefreshShopPacket::handle);
    }

    private void onRegisterItems(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            Identifier shopOpenerId = Identifier.fromNamespaceAndPath(MODID, "shop_opener");

            ResourceKey<Item> shopOpenerKey = ResourceKey.create(Registries.ITEM, shopOpenerId);

            SHOP_OPENER = new ModItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .setId(shopOpenerKey)
            );


            event.register(Registries.ITEM, shopOpenerId, () -> SHOP_OPENER);
        }
    }

    private void onRegisterMenus(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.MENU)) {
            Identifier shopMenuId = Identifier.fromNamespaceAndPath(MODID, "shop_menu");


            SHOP_MENU = IMenuTypeExtension.create((containerId, inventory, buf) ->
                    new ShopMenu(containerId, inventory, null)
            );

            event.register(Registries.MENU, shopMenuId, () -> SHOP_MENU);
        }
    }
}