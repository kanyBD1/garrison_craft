package io.github.kanybd1.wei.bedwar.bagshop.gui;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import io.github.kanybd1.wei.bedwar.bagshop.network.PurchaseShopItemPacket;
import io.github.kanybd1.wei.bedwar.bagshop.network.RefreshShopPacket;
import net.minecraft.client.Minecraft;
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
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // 1. 先调用 super 绘制基础容器背景和玩家背包物品
        super.extractContents(graphics, mouseX, mouseY, partialTick);

        // 2. 覆盖绘制自定义大箱子背景
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                SHOP_BACKGROUND,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256
        );

        // ⭐ 3. 【必须保留】手动绘制虚拟商品列表
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

            // 绘制物品及数量/耐久度等装饰
            graphics.item(stack, x, y);
            graphics.itemDecorations(this.font, stack, x, y);

            // ⭐ 新增：鼠标悬停时绘制半透明高亮框，提供交互反馈
            if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
                graphics.fill(x, y, x + 16, y + 16, 0x80FFFFFF);
            }
        }

        // 4. 绘制刷新按钮图标
        int refreshX = this.leftPos + REFRESH_BUTTON_X_OFFSET;
        int refreshY = this.topPos + REFRESH_BUTTON_Y_OFFSET;
        graphics.item(REFRESH_ICON, refreshX, refreshY);
        graphics.itemDecorations(this.font, REFRESH_ICON, refreshX, refreshY);

        if (mouseX >= refreshX && mouseX < refreshX + 16
                && mouseY >= refreshY && mouseY < refreshY + 16) {
            graphics.fill(refreshX, refreshY, refreshX + 16, refreshY + 16, 0x80FFFFFF);
        }
    }

    @Override
    protected void init() {
        super.init();
        updateDisplayFromCache();
    }

    // ✅ 新增：当收到 SyncShopDataPayload 时调用此方法刷新UI
    public void updateDisplayFromCache() {
        PlayerShopData data = ClientShopData.get();
        this.displayedItems = (data != null) ? data.displayedItems() : Collections.emptyList();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {

            int refreshX = this.leftPos + REFRESH_BUTTON_X_OFFSET;
            int refreshY = this.topPos + REFRESH_BUTTON_Y_OFFSET;
            if (event.x() >= refreshX && event.x() < refreshX + 16
                    && event.y() >= refreshY && event.y() < refreshY + 16) {
                Minecraft.getInstance().getConnection().send(new RefreshShopPacket());
                return true;
            }

            // ✅ 2. 新增：商品槽位点击购买逻辑
            int middleRowStartSlot = 9;
            int maxMiddleRowSlots = 9;
            for (int i = 0; i < Math.min(this.displayedItems.size(), maxMiddleRowSlots); i++) {
                int slotIndex = middleRowStartSlot + i;
                int column = slotIndex % 9;
                int row = slotIndex / 9;
                int x = this.leftPos + 8 + column * SLOT_SIZE;
                int y = this.topPos + 18 + row * SLOT_SIZE;

                // 判断鼠标是否在该商品 16x16 区域内
                if (event.x() >= x && event.x() < x + 16
                        && event.y() >= y && event.y() < y + 16) {

                    // ✅ 发送购买包给服务端（注意：传入的是数据列表索引 i，不是容器槽位索引）
                    Minecraft.getInstance().getConnection().send(new PurchaseShopItemPacket(i));
                    return true;
                }
            }
        }
        return super.mouseClicked(event, doubleClick);
    }
}