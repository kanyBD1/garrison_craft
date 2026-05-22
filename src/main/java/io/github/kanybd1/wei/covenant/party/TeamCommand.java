package io.github.kanybd1.wei.covenant.party;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class TeamCommand {

    private final TeamManager teamManager;

    public TeamCommand(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("team")
                .then(Commands.literal("create")
                    .executes(this::createTeam)
                )
        );
    }

    private int createTeam(CommandContext<CommandSourceStack> context) {
        var player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("只由玩家执行"));
            return 0;
        } else {
            String playerName = player.getName().getString();
            UUID teamUUID = UUID.randomUUID();
            String teamName = playerName + "'s Team";
            TeamSlim newTeam = new TeamSlim(teamUUID, teamName, player.getUUID());
            context.getSource().sendSuccess(() -> Component.literal("成功创建" + teamName), true);
            return 1;
        }
    }

}
