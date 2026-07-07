package io.github.kanybd1.wei.bedwar.bagshop;

import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.ShopList;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import io.github.kanybd1.wei.bedwar.bagshop.network.SyncShopDataPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModItem extends net.minecraft.world.item.Item {

    public ModItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {

            PlayerShopData serverData = sp.getData(AttachmentShopData.PLAYER_SHOP_DATA);

            if (serverData.currentShopItems().isEmpty()) {
                List<ItemStack> pool = ShopList.getPoolForLevel(serverData.level());
                if (!pool.isEmpty()) {
                    List<ItemStack> shuffled = new ArrayList<>(pool);
                    Collections.shuffle(shuffled);
                    int count = Math.min(shuffled.size(), 27);
                    List<ItemStack> finalList = new ArrayList<>(shuffled.subList(0, count));

                    PlayerShopData newData = serverData.withCurrentShopItems(finalList);
                    sp.setData(AttachmentShopData.PLAYER_SHOP_DATA, newData);

                    PacketDistributor.sendToPlayer(sp, new SyncShopDataPayload(newData));

                    serverData = newData;
                }
            }

            final PlayerShopData finalServerData = serverData;
            sp.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new ShopMenu(id, inv, finalServerData),
                    Component.translatable("container.wei.shop")
            ));
        }
        return InteractionResult.SUCCESS;
    }
}
