package io.github.kanybd1.wei.gui;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantStacks.IStack;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.GuiLayer;

@OnlyIn(Dist.CLIENT)
public class StackGui implements GuiLayer {

    public static final StackGui INSTANCE = new StackGui();
    private final Minecraft minecraft = Minecraft.getInstance();

    private static final int RIGHT_MARGIN = 10;
    private static final int TOP_MARGIN = 30;
    private static final int LINE_HEIGHT = 12;
    private static final int PADDING = 4;

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (minecraft.player == null || minecraft.level == null) return;

        Font font = minecraft.font;
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int x = screenWidth - RIGHT_MARGIN;
        int y = TOP_MARGIN;


        for (MobEffectInstance activeEffect : minecraft.player.getActiveEffects()) {
            MobEffect type = activeEffect.getEffect().value();


            IStack stack = getStackForEffect(type);


            if (stack != null && stack.getStack() >= 0) {
                String displayName = activeEffect.getEffect().value().getDisplayName().getString();
                int realLevel = stack.getStack() + 1;
                String displayText = displayName + " " + realLevel;

                int textWidth = font.width(displayText);
                int bgLeft = x - textWidth - PADDING;
                int bgTop = y - 2;
                int bgRight = x + PADDING;
                int bgBottom = y + 10;

                guiGraphics.fill(bgLeft, bgTop, bgRight, bgBottom, 0x80000000);
                guiGraphics.text(font, displayText, bgLeft + PADDING / 2, y, type.getColor(), true);

                y += LINE_HEIGHT;
            }
        }
    }

    private IStack getStackForEffect(MobEffect effect) {
        if (effect == EffectRegister.COVENANT_FORTRESS.get()) return minecraft.player.getData(StackAttachmentType.STACK_FORTRESS);
        if (effect == EffectRegister.COVENANT_END.get()) return minecraft.player.getData(StackAttachmentType.STACK_END);
        if (effect == EffectRegister.COVENANT_MINER.get()) return minecraft.player.getData(StackAttachmentType.STACK_MINER);
        if (effect == EffectRegister.COVENANT_OCEAN.get()) return minecraft.player.getData(StackAttachmentType.STACK_OCEAN);
        if (effect == EffectRegister.COVENANT_KNOWLEDGE.get()) return minecraft.player.getData(StackAttachmentType.STACK_KNOWLEDGE);
        if (effect == EffectRegister.COVENANT_FOREST.get()) return minecraft.player.getData(StackAttachmentType.STACK_FOREST);

        return null;
    }
}