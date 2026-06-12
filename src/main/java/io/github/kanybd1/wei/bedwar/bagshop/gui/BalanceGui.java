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
    private static final int LINE_SPACING = 2;

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (mc.player == null || mc.level == null) return;

        Font font = mc.font;
        PlayerShopData shopData = ClientShopData.get();


        String balanceText = "资金: " + shopData.balance();
        String levelText = "商店等级: " + shopData.level();


        int balanceWidth = font.width(balanceText);
        int levelWidth = font.width(levelText);
        int maxWidth = Math.max(balanceWidth, levelWidth);


        int x = LEFT_MARGIN;
        int y = TOP_MARGIN;
        int bgLeft = 0;
        int bgRight = x + maxWidth + PADDING;
        int bgTop = y - PADDING / 2;
        int bgBottom = y + (font.lineHeight * 2) + LINE_SPACING + PADDING / 2;


        guiGraphics.fill(bgLeft, bgTop, bgRight, bgBottom, 0x80000000);


        guiGraphics.text(font, balanceText, x, y, 0xFFFFFFFF, true);


        int levelY = y + font.lineHeight + LINE_SPACING;
        guiGraphics.text(font, levelText, x, levelY, 0xFFFFFFFF, true);
    }
}