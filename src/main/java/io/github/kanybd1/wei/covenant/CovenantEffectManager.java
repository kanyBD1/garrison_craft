package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.covenant.covenants.CovenantFortress;
import io.github.kanybd1.wei.covenant.covenants.CovenantOcean;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

import static io.github.kanybd1.wei.covenant.covenants.CovenantEnd.applySpeed;
import static io.github.kanybd1.wei.covenant.covenants.CovenantKnowledge.giveExperience;
import static io.github.kanybd1.wei.covenant.covenants.CovenantMiner.applyHaste;

@EventBusSubscriber(modid = "wei")
public class CovenantEffectManager {
    @SubscribeEvent
    public static void onPlayerDamagePre(LivingDamageEvent.Pre event) {
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
    public static void onPlayerTeleport(EntityTeleportEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!player.hasEffect(EffectRegister.COVENANT_END)) {
            return;
        }
        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_END);
        assert Objects.nonNull(effectCovenant);
        applySpeed(player,effectCovenant.getAmplifier());
    }

    @SubscribeEvent
    public static void onPlayerTickMiner(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) {return;}
        Player player = event.getEntity();
        if (!player.hasEffect(EffectRegister.COVENANT_MINER)) {
            return;
        }
        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_MINER);
        assert Objects.nonNull(effectCovenant);
        applyHaste(player,effectCovenant.getAmplifier());
    }
    @SubscribeEvent
    public static void onPlayerTickOcean(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) {return;}
        Player player = event.getEntity();

        if (!player.hasEffect(EffectRegister.COVENANT_OCEAN)) {return;}

        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_MINER);
        assert Objects.nonNull(effectCovenant);

        if (player.isUnderWater()) {CovenantOcean.applyDolphinsGrace(player);}
    }
    @SubscribeEvent
    public static void onPlayerLevelChange(PlayerXpEvent.LevelChange event) {
        if (event.getEntity().level().isClientSide()) {return;}
        Player player = event.getEntity();
        if (!player.hasEffect(EffectRegister.COVENANT_KNOWLEDGE)) {return;}

        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_MINER);
        assert Objects.nonNull(effectCovenant);

        giveExperience(player,effectCovenant.getAmplifier());
    }
}
