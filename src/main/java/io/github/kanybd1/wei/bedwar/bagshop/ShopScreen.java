package io.github.kanybd1.wei.bedwar.bagshop;

import io.github.kanybd1.wei.WeiModMain;
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

    // 自定义背景纹理路径
    private static final Identifier SHOP_BG = Identifier.fromNamespaceAndPath(
            WeiModMain.MODID, "textures/gui/shop.png"
    );

    public ShopScreen(ShopMenu menu, Inventory playerInventory, Component title) {
        // imageWidth=176, imageHeight=60 (根据你之前的设定)
        super(menu, playerInventory, title, 176, 60);

        // 隐藏原版标签文字（这些字段仍然可写）
        this.titleLabelY = -100;
        this.inventoryLabelY = -100;
    }


    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // ⚠️ 必须先调用 super，它会处理标签、槽位高亮等基础渲染
        super.extractContents(graphics, mouseX, mouseY, partialTick);

        // 绘制自定义背景纹理
        // 注意：新版使用 blitSprite 或 blit，参数可能略有不同
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                SHOP_BG,
                0, 0,           // 相对于 leftPos/topPos 的偏移（已在 super 中 translate）
                this.imageWidth,
                this.imageHeight
        );

        // 如果你需要在背景之上、槽位之下绘制额外内容，放在这里
    }

    /**
     * 如果需要绘制前景内容（如标题文字、自定义按钮覆盖层），
     * 可以重写此方法或在 extractContents 末尾添加
     */
    // @Override
    // protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
    //     super.extractLabels(graphics, mouseX, mouseY);
    //     // 自定义前景文字...
    // }
}