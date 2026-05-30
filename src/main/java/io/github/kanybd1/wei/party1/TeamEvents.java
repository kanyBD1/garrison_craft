package io.github.kanybd1.wei.party1;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.CovenantFortress;
import io.github.kanybd1.wei.covenant.EffectRegister;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = "wei")
public class TeamEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {

        Set<String> Names = WeiModMain.TEAM_MANAGER.getTeamNames();

        for (String teamName : Names) {
            Set<UUID> members = WeiModMain.TEAM_MANAGER.getTeamMembers(teamName);
            int value = 0;
            for (UUID member : members) {
                Player player = event.getEntity().level().getPlayerByUUID(member);
                if (player != null) {
                    if (player.hasEffect(EffectRegister.COVENANT_FORTRESS)) {
                        value++;
                    }
                }
            }
            if (value >= 2) {
                for (UUID member : members) {
                    Player player = event.getEntity().level().getPlayerByUUID(member);
                    if (player != null) {
                        CovenantFortress.applyAbsorption(value,player);
                    }
                }
            }
        }

    }


}
