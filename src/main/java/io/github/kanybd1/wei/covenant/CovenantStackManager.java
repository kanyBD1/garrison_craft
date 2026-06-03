package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Objects;

@EventBusSubscriber(modid = WeiModMain.MODID)
public class CovenantStackManager {
    @SubscribeEvent
    public static void onPlayerDamagePre(LivingDamageEvent.Pre event){
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!player.hasEffect(EffectRegister.COVENANT_FORTRESS)) {
            return;
        }
        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_FORTRESS);

        if (Objects.isNull(effectCovenant)) {return;}

        StacksHelper.addFortressStacks(player,(int)event.getNewDamage());
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
                StacksHelper.addForestStacks(player,7);
            }
        }
    }
}
