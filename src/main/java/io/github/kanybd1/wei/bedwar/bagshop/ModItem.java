package io.github.kanybd1.wei.bedwar.bagshop;

import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItem extends net.minecraft.world.item.Item {

    public ModItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            sp.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> {
                        // ⭐ 从服务端 Attachment 获取数据，而非 ClientShopData
                        PlayerShopData serverData = p.getData(AttachmentShopData.PLAYER_SHOP_DATA);
                        return new ShopMenu(id, inv, serverData);
                    },
                    Component.translatable("container.wei.shop")
            ));
        }
        return InteractionResult.SUCCESS;
    }
}
