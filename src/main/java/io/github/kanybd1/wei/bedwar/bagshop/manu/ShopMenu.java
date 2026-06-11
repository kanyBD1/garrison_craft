package io.github.kanybd1.wei.bedwar.bagshop.manu;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.AttachmentShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.ShopList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ShopMenu extends AbstractContainerMenu {
    // 仅服务端持有，客户端为 null
    private final PlayerShopData serverShopData;
    private static final SimpleContainer EMPTY_CONTAINER = new SimpleContainer(27);



    // ShopMenu.java 构造器修改建议
    public ShopMenu(int containerId, Inventory playerInventory, PlayerShopData shopData) {
        super(WeiModMain.SHOP_MENU, containerId);

        // 如果是服务端且商品为空，刷新并持久化
        if (!playerInventory.player.level().isClientSide()
                && shopData != null
                && shopData.displayedItems().isEmpty()) {

            List<ItemStack> pool = ShopList.getPoolForLevel(shopData.level());
            PlayerShopData refreshed = shopData.refreshShop(pool);

            // ✅ 必须写回 Attachment！
            ((ServerPlayer) playerInventory.player).setData(AttachmentShopData.PLAYER_SHOP_DATA, refreshed);
            this.serverShopData = refreshed;
        } else {
            this.serverShopData = shopData;
        }
        initSlots();
    }

    private void initSlots() {
        for (int i = 0; i < 27; i++) {
            int x = 8 + (i % 9) * 18;
            int y = 18 + (i / 9) * 18;

            this.addSlot(new Slot(EMPTY_CONTAINER, i, x, y) {
                @Override public boolean mayPickup(Player p) { return false; }
                @Override public boolean mayPlace(ItemStack s) { return false; }
                @Override public ItemStack getItem() { return ItemStack.EMPTY; }
                @Override public void set(ItemStack stack) { /* no-op */ }
                @Override public void setChanged() { /* no-op */ }
            });
        }
    }

    @Override
    public boolean stillValid(Player player) { return true; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) { return ItemStack.EMPTY; }
}
