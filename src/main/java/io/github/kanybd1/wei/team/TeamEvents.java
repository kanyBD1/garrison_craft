<<<<<<<< HEAD:src/main/java/io/github/kanybd1/wei/team/TeamEvents.java
package io.github.kanybd1.wei.team;
========
package io.github.kanybd1.wei.party;
>>>>>>>> 06705d23469cda40ba19c583d369db0931e7db20:src/main/java/io/github/kanybd1/wei/party/TeamEvents.java

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import io.github.kanybd1.wei.covenant.covenants.CovenantEnd;
import io.github.kanybd1.wei.covenant.covenants.CovenantFortress;
import io.github.kanybd1.wei.covenant.EffectRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = WeiModMain.MODID)
public class TeamEvents {
    private static int globalCountdownTicks = 0;
    private static final int COOL_DOWN = 600; // 30秒

    @SubscribeEvent
    public static void onLevelTickFortress(LevelTickEvent.Post event) {
        Level level = event.getLevel();

        if (level.isClientSide()) return;

        if (level.players().isEmpty()) return;

        globalCountdownTicks++;
        if (globalCountdownTicks < COOL_DOWN) {
            return;
        }
        globalCountdownTicks = 0;

        Set<String> teamNames = WeiModMain.TEAM_MANAGER.getTeamNames();
        if (teamNames == null || teamNames.isEmpty()) return;

        for (String teamName : teamNames) {
            Set<UUID> members = WeiModMain.TEAM_MANAGER.getTeamMembers(teamName);
            if (members == null || members.isEmpty()) continue;

            int fortressCount = 0;

            for (UUID member : members) {
                Player player = level.getPlayerByUUID(member);

                if (player != null && player.isAlive() && player.hasEffect(EffectRegister.COVENANT_FORTRESS)) {
                    fortressCount++;
                }
            }

            if (fortressCount >= 2) {
                for (UUID member : members) {
                    Player player = level.getPlayerByUUID(member);
                    if (player != null && player.isAlive()) {
                        CovenantFortress.applyAbsorption(fortressCount, player);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onLevelTickEnd(LevelTickEvent.Post event) {
        Level level = event.getLevel();

        if (level.isClientSide()) return;

        if (level.players().isEmpty()) return;

        globalCountdownTicks++;
        if (globalCountdownTicks < COOL_DOWN) {
            return;
        }
        globalCountdownTicks = 0;

        Set<String> teamNames = WeiModMain.TEAM_MANAGER.getTeamNames();
        if (teamNames == null || teamNames.isEmpty()) return;

        for (String teamName : teamNames) {
            Set<UUID> members = WeiModMain.TEAM_MANAGER.getTeamMembers(teamName);
            if (members == null || members.isEmpty()) continue;

            int fortressCount = 0;

            for (UUID member : members) {
                Player player = level.getPlayerByUUID(member);

                if (player != null && player.isAlive() && player.hasEffect(EffectRegister.COVENANT_END)) {
                    fortressCount++;
                }
            }

            if (fortressCount >= 2) {
                for (UUID member : members) {
                    Player player = level.getPlayerByUUID(member);
                    if (player != null && player.isAlive()) {
                        CovenantEnd.applyInvisible(player, StacksHelper.getStack(player, StackAttachmentType.STACK_END));
                    }
                }
            }
        }
    }
}