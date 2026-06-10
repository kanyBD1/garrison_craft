package io.github.kanybd1.wei.bedwar.bagshop.gui;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.ShopList;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class ShopScreen extends AbstractContainerScreen<ShopMenu> {

    private static final Random RANDOM = new Random();
    private static final int MAX_DISPLAY_COUNT = 27;

    private static final Identifier SHOP_BACKGROUND =
            Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    private static final int SLOT_SIZE = 18;

    private static final int REFRESH_SLOT_INDEX = 19;
    private static final int REFRESH_BUTTON_X_OFFSET = 8 + (REFRESH_SLOT_INDEX % 9) * SLOT_SIZE;
    private static final int REFRESH_BUTTON_Y_OFFSET = 18 + (REFRESH_SLOT_INDEX / 9) * SLOT_SIZE;

    // 使用箱子作为刷新图标
    private static final ItemStack REFRESH_ICON = new ItemStack(Items.CHEST);


    private List<ItemStack> displayedItems = Collections.emptyList();

    public ShopScreen(ShopMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 134);
        this.titleLabelY = -100;
        this.inventoryLabelY = -100;
    }

    @Override
    protected void init() {
        super.init();

        refreshDisplayedItems();
    }


    public void refreshData() {
        refreshDisplayedItems();
    }

    private void refreshDisplayedItems() {
        PlayerShopData data = ClientShopData.get();
        int playerLevel = (data != null) ? data.level() : 1;

        List<ItemStack> pool = ShopList.getPoolForLevel(playerLevel);

        if (pool == null || pool.isEmpty()) {
            this.displayedItems = Collections.emptyList();
            return;
        }

        List<ItemStack> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled, RANDOM);

        int count = Math.min(shuffled.size(), MAX_DISPLAY_COUNT);
        this.displayedItems = new ArrayList<>(shuffled.subList(0, count));
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                SHOP_BACKGROUND,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256
        );


        int middleRowStartSlot = 9;
        int maxMiddleRowSlots = 9;

        for (int i = 0; i < Math.min(this.displayedItems.size(), maxMiddleRowSlots); i++) {
            ItemStack stack = this.displayedItems.get(i);
            if (stack.isEmpty()) continue;


            int slotIndex = middleRowStartSlot + i;
            int column = slotIndex % 9;
            int row = slotIndex / 9;

            int x = this.leftPos + 8 + column * SLOT_SIZE;
            int y = this.topPos + 18 + row * SLOT_SIZE;

            graphics.item(stack, x, y);
            graphics.itemDecorations(this.font, stack, x, y);
        }

        int refreshX = this.leftPos + REFRESH_BUTTON_X_OFFSET;
        int refreshY = this.topPos + REFRESH_BUTTON_Y_OFFSET;
        graphics.item(REFRESH_ICON, refreshX, refreshY);
        graphics.itemDecorations(this.font, REFRESH_ICON, refreshX, refreshY);

        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        // 1. ✅ 优先拦截刷新按钮点击（仅响应左键）
        if (event.button() == 0) {
            int refreshX = this.leftPos + REFRESH_BUTTON_X_OFFSET;
            int refreshY = this.topPos + REFRESH_BUTTON_Y_OFFSET;

            // 判断鼠标是否在刷新图标的 16x16 区域内
            if (event.x() >= refreshX && event.x() < refreshX + 16
                    && event.y() >= refreshY && event.y() < refreshY + 16) {

                refreshDisplayedItems();


                // NetworkHandler.sendToServer(new RefreshShopPacket());

                return true; // 消费事件，阻止父类处理
            }
        }

        // 2. 未命中刷新按钮，交由父类处理正常的槽位点击逻辑
        return super.mouseClicked(event, doubleClick);
    }
}