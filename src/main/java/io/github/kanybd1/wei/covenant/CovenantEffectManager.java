package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import io.github.kanybd1.wei.covenant.covenants.CovenantFortress;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
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
    public static void onPlayerDamagePre_forest(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        Entity owner = projectile.getOwner();
        if (!(owner instanceof Player player)) {return;}
        if (!player.hasEffect(EffectRegister.COVENANT_FOREST)) {return;}
        if(!(projectile instanceof AbstractArrow)) {return;}
        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_FOREST);
        if (effectCovenant == null) {return;}

        if(event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {Entity hitEntity = entityHitResult.getEntity();
            if(hitEntity instanceof LivingEntity livingTarget) {
                livingTarget.setHealth(livingTarget.getHealth()-Math.max(StacksHelper.getForestStacks(player)/200,1));
            }
        }
    }

}
