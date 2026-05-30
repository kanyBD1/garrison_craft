package io.github.kanybd1.wei.party1;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType; // 引入字符串参数类型
import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class TeamCommand {

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("team")
                        .then(Commands.literal("create")
                                // 1. 定义一个名为 "teamName" 的字符串参数
                                .then(Commands.argument("teamName", StringArgumentType.string())
                                        // 2. 使用 Lambda 表达式作为 executes 的执行体
                                        .executes(context -> {
                                            // --- 桥接逻辑开始 ---

                                            // A. 从上下文获取玩家 (如果是控制台输入会抛出异常)
                                            ServerPlayer player = context.getSource().getPlayerOrException();

                                            // B. 获取用户输入的队伍名称
                                            String teamName = StringArgumentType.getString(context, "teamName");

                                            // C. 调用你的 TeamManager 业务逻辑
                                            // 假设你的 manager 是静态获取的，或者通过构造函数传入的
                                            WeiModMain.TEAM_MANAGER.createTeam(player);

                                            // D. 返回 1 表示指令执行成功
                                            return 1;
                                        })
                                )
                        )
        );
        // 注册 "join" 子指令
        dispatcher.register(Commands.literal("team")
                .then(Commands.literal("join")
                        // 1. 定义一个字符串参数，名为 "teamName"
                        .then(Commands.argument("teamName", StringArgumentType.string())
                                .executes(context -> {
                                    // 2. 获取执行指令的玩家
                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                    // 3. 获取参数中输入的队伍名称
                                    String teamName = StringArgumentType.getString(context, "teamName");

                                    // 4. 调用业务逻辑并返回结果
                                    boolean success = WeiModMain.TEAM_MANAGER.addToTeam(player, teamName);

                                    if (success) {
                                        return 1; // 返回 1 表示指令执行成功
                                    } else {
                                        return 0; // 返回 0 表示失败
                                    }
                                })
                        )
                )
        );
        // 注册 "leave" 子指令
        dispatcher.register(Commands.literal("team")
                .then(Commands.literal("leave")
                        .executes(context -> {
                            // 1. 获取执行指令的玩家
                            ServerPlayer player = context.getSource().getPlayerOrException();

                            // 2. 调用业务逻辑移除玩家
                            boolean success = WeiModMain.TEAM_MANAGER.removeFromTeam(player);

                            if (success) {
                                return 1;
                            } else {
                                return 0;
                            }
                        })
                )
        );
    }

}
