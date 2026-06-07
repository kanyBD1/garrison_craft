package io.github.kanybd1.wei.bedwar.bagshop;

import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ShopMenu extends AbstractContainerMenu {
    private final SimpleContainer shopContainer = new SimpleContainer(18);

    // 这个签名与 MenuType 构造函数要求的 ExtendedMenuProvider 完美对应
    public ShopMenu(int containerId, Inventory playerInventory) {
        // 现在 .get() 一定会返回 MenuType<ShopMenu>，不会再报错
        super(WeiModMain.ModMenus.SHOP_MENU.get(), containerId);

        // 添加虚拟槽位...
        for (int i = 0; i < 18; i++) {
            int x = 8 + (i % 9) * 18;
            int y = 18 + (i / 9) * 18;
            this.addSlot(new Slot(this.shopContainer, i, x, y) {
                @Override public boolean mayPickup(Player p) { return false; }
                @Override public boolean mayPlace(ItemStack s) { return false; }
            });
        }
    }

    @Override
    public boolean stillValid(Player player) { return true; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) { return ItemStack.EMPTY; }
}
