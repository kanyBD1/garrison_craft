package io.github.kanybd1.wei.gui;

import io.github.kanybd1.wei.client.ClientTeamData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.GuiLayer;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TeamGui implements GuiLayer {

    public static final TeamGui INSTANCE = new TeamGui();
    private final Minecraft minecraft = Minecraft.getInstance();


    private static final int RIGHT_MARGIN = 10;
    private static final int PADDING = 4;
    private static final int LINE_HEIGHT = 12;
    private static final int TEAM_COLOR = 0xFF55FFFF;
    private static final int MEMBER_COLOR = 0xFFFFFFFF;

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (minecraft.player == null || minecraft.level == null) return;

        ClientTeamData teamData = ClientTeamData.INSTANCE;
        // 如果玩家不在队伍中，不渲染
        if (!teamData.isInTeam()) return;

        Font font = minecraft.font;
        List<String> members = teamData.getMemberNames();
        String teamName = teamData.getCurrentTeamName();

        // --- 核心计算：右侧 & 垂直居中 ---
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        // 1. 计算整个面板的宽度和高度
        int maxTextWidth = font.width(teamName);
        for (String member : members) {
            maxTextWidth = Math.max(maxTextWidth, font.width(member));
        }
        int panelWidth = maxTextWidth + PADDING * 2;

        int panelHeight = LINE_HEIGHT + 4 + (members.size() * LINE_HEIGHT);


        int x = screenWidth - RIGHT_MARGIN - panelWidth;


        int y = (screenHeight - panelHeight) / 2;



        guiGraphics.fill(x, y, x + panelWidth, y + panelHeight, 0x80000000);


        int titleX = x + (panelWidth - font.width(teamName)) / 2;
        guiGraphics.text(font, teamName, titleX, y + PADDING, TEAM_COLOR, false);


        int lineY = y + LINE_HEIGHT + 2;
        guiGraphics.fill(x + 2, lineY, x + panelWidth - 2, lineY + 1, 0xFFFFFFFF);


        int currentY = lineY + 4;
        for (String memberName : members) {
            guiGraphics.text(font, memberName, x + PADDING, currentY, MEMBER_COLOR, false);
            currentY += LINE_HEIGHT;
        }
    }
}