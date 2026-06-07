package io.github.kanybd1.wei.gui;

import io.github.kanybd1.wei.covenant.EffectRegister;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.GuiLayer;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class StackGui implements GuiLayer {

    public static final StackGui INSTANCE = new StackGui();
    private final Minecraft minecraft = Minecraft.getInstance();

    // 配置常量
    private static final int RIGHT_MARGIN = 10;
    private static final int TOP_MARGIN = 30;
    private static final int LINE_HEIGHT = 12;
    private static final int PADDING = 4;

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        // 1. 安全检查
        if (minecraft.player == null || minecraft.level == null) return;

        // 2. 获取玩家效果并过滤出我们关心的契约效果
        List<MobEffectInstance> relevantEffects = new ArrayList<>();
        for (MobEffectInstance effect : minecraft.player.getActiveEffects()) {
            MobEffect type = effect.getEffect().value();
            if (type == EffectRegister.COVENANT_FORTRESS.get() ||
                    type == EffectRegister.COVENANT_END.get() ||
                    type == EffectRegister.COVENANT_MINER.get() ||
                    type == EffectRegister.COVENANT_OCEAN.get() ||
                    type == EffectRegister.COVENANT_KNOWLEDGE.get()){
                relevantEffects.add(effect);
            }
        }

        // 3. 如果没有相关效果，直接返回，不进行任何渲染
        if (relevantEffects.isEmpty()) return;

        // 4. 准备渲染参数
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int x = screenWidth - RIGHT_MARGIN;
        int y = TOP_MARGIN;
        Font font = minecraft.font;

        // 5. 开始渲染每个效果
        for (MobEffectInstance effect : relevantEffects) {
            MobEffect mobEffect = effect.getEffect().value();

            // 构建显示文本：效果名 + 等级
            Component effectName = mobEffect.getDisplayName();
            int level = effect.getAmplifier() + 1; // 转换为玩家看得懂的等级
            String displayText = effectName.getString() + " " + level;

            // 计算背景区域大小
            int textWidth = font.width(displayText);
            int bgLeft = x - textWidth - PADDING;
            int bgTop = y - 2;
            int bgRight = x + PADDING;
            int bgBottom = y + 10;

            // 绘制半透明背景 (使用 GuiGraphics 的 fill 方法，更安全)
            // 0x80000000 是半透明黑色
            guiGraphics.fill(bgLeft, bgTop, bgRight, bgBottom, 0x80000000);

            // 绘制文字
            // GuiGraphics 会自动处理颜色和混合模式，无需手动调用 RenderSystem
            guiGraphics.text(font, displayText, bgLeft + PADDING/2, y, mobEffect.getColor(), false);

            // 更新 Y 坐标，绘制下一行
            y += LINE_HEIGHT;
        }
    }
}