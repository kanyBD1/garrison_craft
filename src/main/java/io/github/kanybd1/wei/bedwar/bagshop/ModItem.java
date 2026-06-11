package io.github.kanybd1.wei.bedwar.bagshop;

import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import io.github.kanybd1.wei.bedwar.bagshop.network.SyncShopDataPayload; // ⬅️ 新增导入
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ModItem extends net.minecraft.world.item.Item {

    public ModItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            // 1. 获取服务端数据
            PlayerShopData serverData = sp.getData(AttachmentShopData.PLAYER_SHOP_DATA);

            // 2. ⭐ 关键修复：在打开菜单前，将数据同步给客户端
            // 这样当 ShopScreen.init() 调用 ClientShopData.get() 时，数据已经就绪
            sp.connection.send(new SyncShopDataPayload(serverData));

            // 3. 打开菜单
            sp.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new ShopMenu(id, inv, serverData),
                    Component.translatable("container.wei.shop")
            ));
        }
        return InteractionResult.SUCCESS;
    }
}