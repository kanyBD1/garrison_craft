package io.github.kanybd1.wei.gui;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantStacks.IStack;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.GuiLayer;

import javax.annotation.CheckForNull;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class StackGui implements GuiLayer {

    public static final StackGui INSTANCE = new StackGui();
    private final Minecraft minecraft = Minecraft.getInstance();

    private static final int RIGHT_MARGIN = 10;
    private static final int TOP_MARGIN = 30;
    private static final int LINE_HEIGHT = 12;
    private static final int PADDING = 4;

    // XXX: 由于客户端 Effect 可能并不可靠，这里可以考虑改用网络封包（Networking Payload Packet）加状态机（Stater）。
    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (minecraft.player == null || minecraft.level == null) return;

        Font font = minecraft.font;
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int x = screenWidth - RIGHT_MARGIN;
        int y = TOP_MARGIN;

        // 遍历玩家身上的所有效果
        for (MobEffectInstance activeEffect : minecraft.player.getActiveEffects()) {
            MobEffect type = activeEffect.getEffect().value();

            // 检查是不是我们的契约效果
            IStack stack = this.getStackForEffect(type);

            // 如果是契约效果，并且层数大于0，则渲染
            if (Objects.isNull(stack) || stack.getStack() < 0) {
                return;
            }

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

    @CheckForNull
    private IStack getStackForEffect(MobEffect effect) {
        final Identifier id = BuiltInRegistries.MOB_EFFECT.getKeyOrNull(effect);
        if (Objects.isNull(id) || !id.getNamespace().equals("wei")) {
            return null;
        }

        assert minecraft.player != null;

        return switch (id.getPath()) {
            case "fortress" -> minecraft.player.getData(StackAttachmentType.STACK_FORTRESS);
            case "end" -> minecraft.player.getData(StackAttachmentType.STACK_END);
            case "miner" -> minecraft.player.getData(StackAttachmentType.STACK_MINER);
            case "ocean" -> minecraft.player.getData(StackAttachmentType.STACK_OCEAN);
            case "forest" -> minecraft.player.getData(StackAttachmentType.STACK_FOREST);
            default -> null;
        };
    }
}
