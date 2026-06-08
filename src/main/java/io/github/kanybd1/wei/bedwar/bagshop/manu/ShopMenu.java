package io.github.kanybd1.wei.bedwar.bagshop.manu;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ShopMenu extends AbstractContainerMenu {
    // 仅服务端持有，客户端为 null
    private final PlayerShopData serverShopData;
    private static final SimpleContainer EMPTY_CONTAINER = new SimpleContainer(27);

    public ShopMenu(int containerId, Inventory playerInventory, PlayerShopData shopData) {
        super(WeiModMain.SHOP_MENU, containerId);
        this.serverShopData = shopData;
        initSlots();
    }

    public ShopMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, null);
    }

    private void initSlots() {
        for (int i = 0; i < 27; i++) {
            int x = 8 + (i % 9) * 18;
            int y = 18 + (i / 9) * 18;
            // 使用空容器占位，实际渲染由 Screen 端 ClientCache 处理
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
