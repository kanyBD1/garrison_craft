package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.covenant.covenants.CovenantFortress;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

import static io.github.kanybd1.wei.covenant.covenants.CovenantEnd.applySpeed;
import static io.github.kanybd1.wei.covenant.covenants.CovenantForest.applyJump;
import static io.github.kanybd1.wei.covenant.covenants.CovenantMiner.applyHaste;

@EventBusSubscriber(modid = "wei")
public class CovenantEffectManager {
    @SubscribeEvent
    public static void onPlayerDamagePre_Fortress(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!player.hasEffect(EffectRegister.COVENANT_FORTRESS)) {
            return;
        }


        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_FORTRESS);
        assert Objects.nonNull(effectCovenant);

        event.setNewDamage(Math.max(0,
            CovenantFortress.damageReduction(event.getNewDamage(), effectCovenant.getAmplifier())));
    }
    @SubscribeEvent
    public static void onPlayerTeleport_End(EntityTeleportEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!player.hasEffect(EffectRegister.COVENANT_END)) {
            return;
        }
        applySpeed(player);
    }
    @SubscribeEvent
    public static void onPlayerTick_Miner(PlayerTickEvent event) {
        Player player = event.getEntity();
        if (!player.hasEffect(EffectRegister.COVENANT_MINER)) {
            return;
        }
        applyHaste(player);
    }
    @SubscribeEvent
    public static void onPlayerTick_Forest(PlayerTickEvent event) {
        Player player = event.getEntity();
        if (!player.hasEffect(EffectRegister.COVENANT_FOREST)) {
            return;
        }
        applyJump(player);
    }
}
