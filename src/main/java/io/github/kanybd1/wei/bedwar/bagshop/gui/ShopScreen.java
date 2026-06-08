package io.github.kanybd1.wei.bedwar.bagshop.gui;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.manu.ShopMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShopScreen extends AbstractContainerScreen<ShopMenu> {

    // 使用原版通用容器背景
    private static final Identifier SHOP_BACKGROUND =
            Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    public ShopScreen(ShopMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 134);
        this.titleLabelY = -100;
        this.inventoryLabelY = -100;
    }

    // ⭐ 核心修复：重写 extractContents 代替已移除的 renderBg
    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // ⭐ 使用 RenderPipelines.GUI_TEXTURED + 10参数 blit（与父类高亮槽位写法一致）
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                SHOP_BACKGROUND,
                this.leftPos,
                this.topPos,
                0, 0,
                this.imageWidth,
                this.imageHeight,
                256, 256
        );


        super.extractContents(graphics, mouseX, mouseY, partialTick);

        PlayerShopData data = ClientShopData.get();
        if (data != null) {

        }
    }

    // ⭐ 供 Payload handle 调用的刷新方法
    public void refreshData() {
        this.init();
    }
}