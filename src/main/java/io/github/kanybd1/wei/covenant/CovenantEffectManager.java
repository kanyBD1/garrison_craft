package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.covenant.covenants.CovenantForest;
import io.github.kanybd1.wei.covenant.covenants.CovenantFortress;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

import static io.github.kanybd1.wei.covenant.covenants.CovenantEnd.applySpeed;
import static io.github.kanybd1.wei.covenant.covenants.CovenantForest.applyJump;
import static io.github.kanybd1.wei.covenant.covenants.CovenantMiner.applyHaste;

@EventBusSubscriber(modid = "wei")
public class CovenantEffectManager {
    @SubscribeEvent
    public static void onPlayerDamagePre_fortress(LivingDamageEvent.Pre event) {
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
    public static void onPlayerTeleport_end(EntityTeleportEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!player.hasEffect(EffectRegister.COVENANT_END)) {
            return;
        }
        applySpeed(player);
    }
    @SubscribeEvent
    public static void onPlayerTick_miner(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.hasEffect(EffectRegister.COVENANT_MINER)) {
            return;
        }
        applyHaste(player);
    }
    @SubscribeEvent
    public static void onPlayerTick_forest(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.hasEffect(EffectRegister.COVENANT_FOREST)) {
            return;
        }
        applyJump(player);
    }
    @SubscribeEvent
    public static void onPlayerDamagePre_forest(LivingDamageEvent.Pre event) {
        var damageSource = event.getSource();
        var directEntity = damageSource.getDirectEntity();
        var causingEntity =damageSource.getEntity();

        if (!(directEntity instanceof Projectile) || !(causingEntity instanceof Player player)) {
            return;
        }
        if (!player.hasEffect(EffectRegister.COVENANT_FOREST)) {
            return;
        }
        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_FOREST);
        assert Objects.nonNull(effectCovenant);

        float originalDamage = event.getNewDamage();
        float increasedDamage = CovenantForest.damageIncrease(originalDamage, effectCovenant.getAmplifier());
        event.setNewDamage(increasedDamage);
    }

}
