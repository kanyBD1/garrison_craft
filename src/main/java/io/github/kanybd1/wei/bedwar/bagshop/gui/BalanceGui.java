package io.github.kanybd1.wei.bedwar.bagshop.gui;

import io.github.kanybd1.wei.bedwar.bagshop.client.ClientShopData;
import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.GuiLayer;

@OnlyIn(Dist.CLIENT)
public class BalanceGui implements GuiLayer {

    public static final BalanceGui INSTANCE = new BalanceGui();
    private final Minecraft mc = Minecraft.getInstance();

    private static final int LEFT_MARGIN = 15;
    private static final int TOP_MARGIN = 15;
    private static final int PADDING = 15;
    private static final int LINE_SPACING = 2; // 【新增】两行文字之间的间距

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (mc.player == null || mc.level == null) return;

        Font font = mc.font;
        PlayerShopData shopData = ClientShopData.get();

        // 1. 准备两行文本
        String balanceText = "资金: " + shopData.balance();
        String levelText = "商店等级: " + shopData.level();

        // 2. 计算最宽的文本宽度（用于撑开背景框）
        int balanceWidth = font.width(balanceText);
        int levelWidth = font.width(levelText);
        int maxWidth = Math.max(balanceWidth, levelWidth);

        // 3. 计算背景框的边界（高度需要包含两行文字和间距）
        int x = LEFT_MARGIN;
        int y = TOP_MARGIN;
        int bgLeft = 0;
        int bgRight = x + maxWidth + PADDING;
        int bgTop = y - PADDING / 2;
        int bgBottom = y + (font.lineHeight * 2) + LINE_SPACING + PADDING / 2;

        // 4. 渲染半透明黑色背景
        guiGraphics.fill(bgLeft, bgTop, bgRight, bgBottom, 0x80000000);

        // 5. 渲染第一行：资金
        guiGraphics.text(font, balanceText, x, y, 0xFFFFFFFF, true);

        // 6. 渲染第二行：商店等级（Y 坐标向下偏移一行的高度 + 间距）
        int levelY = y + font.lineHeight + LINE_SPACING;
        guiGraphics.text(font, levelText, x, levelY, 0xFFFFFFFF, true);
    }
}