package io.github.kanybd1.wei.party1;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "wei")
public class TeamCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("teamcom")

                        .then(Commands.literal("create").executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            WeiModMain.TEAM_MANAGER.createTeam(player);
                            return 1;
                        }))


                        .then(Commands.literal("leave").executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            boolean success = WeiModMain.TEAM_MANAGER.removeFromTeam(player);
                            return success ? 1 : 0;
                        }))


                        .then(Commands.literal("join")
                                .then(Commands.argument("teamName", StringArgumentType.string())
                                        .executes(context -> {
                                            ServerPlayer player = context.getSource().getPlayerOrException();
                                            String teamName = StringArgumentType.getString(context, "teamName");

                                            boolean success = WeiModMain.TEAM_MANAGER.addToTeam(player, teamName);
                                            return success ? 1 : 0;
                                        })
                                )
                        )
        );
    }
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

}
