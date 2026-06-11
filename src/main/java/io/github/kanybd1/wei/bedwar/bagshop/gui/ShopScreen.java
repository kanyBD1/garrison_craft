package io.github.kanybd1.wei.bedwar.bagshop.gui;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import io.github.kanybd1.wei.bedwar.bagshop.network.LevelUpPayload; // 【新增】导入升级包
import io.github.kanybd1.wei.bedwar.bagshop.network.PurchaseItemPayload;
import io.github.kanybd1.wei.bedwar.bagshop.network.RefreshShopPayload;
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

import static net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer;

@OnlyIn(Dist.CLIENT)
public class ShopScreen extends AbstractContainerScreen<ShopMenu> {

    private static final int MAX_DISPLAY_COUNT = 9;

    private static final Identifier SHOP_BACKGROUND =
            Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    private static final int SLOT_SIZE = 18;

    // 刷新按钮配置
    private static final int REFRESH_SLOT_INDEX = 19;
    private static final int REFRESH_BUTTON_X_OFFSET = 8 + (REFRESH_SLOT_INDEX % 9) * SLOT_SIZE;
    private static final int REFRESH_BUTTON_Y_OFFSET = 18 + (REFRESH_SLOT_INDEX / 9) * SLOT_SIZE;
    private static final ItemStack REFRESH_ICON = new ItemStack(Items.CHEST);

    // 【新增】升级按钮配置（刷新按钮右侧第3个，即索引 22）
    private static final int LEVEL_UP_SLOT_INDEX = 22;
    private static final int LEVEL_UP_BUTTON_X_OFFSET = 8 + (LEVEL_UP_SLOT_INDEX % 9) * SLOT_SIZE;
    private static final int LEVEL_UP_BUTTON_Y_OFFSET = 18 + (LEVEL_UP_SLOT_INDEX / 9) * SLOT_SIZE;
    private static final ItemStack LEVEL_UP_ICON = new ItemStack(Items.SLIME_BALL);

    // 商品展示区域配置（中间行）
    private static final int SHOP_ROW_START_SLOT = 9;
    private static final int SHOP_ROW_MAX_SLOTS = 9;

    private List<ItemStack> displayedItems = Collections.emptyList();

    // 【新增】记录已被购买的商品索引，防止重绘时再次显示
    private final Set<Integer> purchasedIndices = new HashSet<>();

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

        // 【核心修改】：直接使用服务端同步过来的商品列表，不再客户端随机！
        List<ItemStack> serverItems = (data != null) ? data.currentShopItems() : Collections.emptyList();

        if (serverItems.isEmpty()) {
            this.displayedItems = Collections.emptyList();
            return;
        }

        // 最多显示 9 个
        int count = Math.min(serverItems.size(), MAX_DISPLAY_COUNT);
        this.displayedItems = new ArrayList<>(serverItems.subList(0, count));

        this.purchasedIndices.clear();
        if (data != null) {
            this.purchasedIndices.addAll(data.purchasedIndices());
        }
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // 绘制背景
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                SHOP_BACKGROUND,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256
        );

        // 绘制商品（跳过已购买的）
        for (int i = 0; i < Math.min(this.displayedItems.size(), SHOP_ROW_MAX_SLOTS); i++) {

            if (purchasedIndices.contains(i)) continue;

            ItemStack stack = this.displayedItems.get(i);
            if (stack.isEmpty()) continue;

            int slotIndex = SHOP_ROW_START_SLOT + i;
            int column = slotIndex % 9;
            int row = slotIndex / 9;

            int x = this.leftPos + 8 + column * SLOT_SIZE;
            int y = this.topPos + 18 + row * SLOT_SIZE;

            graphics.item(stack, x, y);
            graphics.itemDecorations(this.font, stack, x, y);
        }

        // 绘制刷新按钮
        int refreshX = this.leftPos + REFRESH_BUTTON_X_OFFSET;
        int refreshY = this.topPos + REFRESH_BUTTON_Y_OFFSET;
        graphics.item(REFRESH_ICON, refreshX, refreshY);
        graphics.itemDecorations(this.font, REFRESH_ICON, refreshX, refreshY);

        // 【新增】绘制升级按钮
        int levelUpX = this.leftPos + LEVEL_UP_BUTTON_X_OFFSET;
        int levelUpY = this.topPos + LEVEL_UP_BUTTON_Y_OFFSET;
        graphics.item(LEVEL_UP_ICON, levelUpX, levelUpY);
        graphics.itemDecorations(this.font, LEVEL_UP_ICON, levelUpX, levelUpY);

        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) { // 仅处理左键
            // 1. 检测刷新按钮点击
            int refreshX = this.leftPos + REFRESH_BUTTON_X_OFFSET;
            int refreshY = this.topPos + REFRESH_BUTTON_Y_OFFSET;
            if (isMouseOver(event.x(), event.y(), refreshX, refreshY)) {
                sendToServer(new RefreshShopPayload());
                return true;
            }

            // 2. 【新增】检测升级按钮点击
            int levelUpX = this.leftPos + LEVEL_UP_BUTTON_X_OFFSET;
            int levelUpY = this.topPos + LEVEL_UP_BUTTON_Y_OFFSET;
            if (isMouseOver(event.x(), event.y(), levelUpX, levelUpY)) {
                sendToServer(new LevelUpPayload()); // 发送升级包给服务端
                return true;
            }

            // 3. 检测商品点击购买
            for (int i = 0; i < Math.min(this.displayedItems.size(), SHOP_ROW_MAX_SLOTS); i++) {
                if (purchasedIndices.contains(i)) continue; // 已购买的不可再次点击

                int slotIndex = SHOP_ROW_START_SLOT + i;
                int column = slotIndex % 9;
                int row = slotIndex / 9;

                int itemX = this.leftPos + 8 + column * SLOT_SIZE;
                int itemY = this.topPos + 18 + row * SLOT_SIZE;

                if (isMouseOver(event.x(), event.y(), itemX, itemY)) {
                    handlePurchase(i);
                    return true; // 消费事件
                }
            }
        }

        return super.mouseClicked(event, doubleClick);
    }


    private boolean isMouseOver(double mouseX, double mouseY, int x, int y) {
        return mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16;
    }


    private void handlePurchase(int index) {
        if (index < 0 || index >= this.displayedItems.size()) return;

        ItemStack item = this.displayedItems.get(index);
        if (item.isEmpty()) return;

        this.purchasedIndices.add(index);

        this.setFocused(null);
        this.rebuildWidgets();

        sendToServer(new PurchaseItemPayload(index));
    }
}