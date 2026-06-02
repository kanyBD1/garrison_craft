package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

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

        StacksHelper.addFortressStacks(player,5);
    }
    @SubscribeEvent
    public static void onPlayerTeleport(EntityTeleportEvent event){
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!player.hasEffect(EffectRegister.COVENANT_END)) {
            return;
        }
        final MobEffectInstance effectCovenant = player.getEffect(EffectRegister.COVENANT_END);

        if (Objects.isNull(effectCovenant)) {return;}
        StacksHelper.addEndStacks(player,10);
    }
    @SubscribeEvent
    public static void onPlayerBreakBlock(BreakBlockEvent event){
        Player player = event.getPlayer();
        if (!player.hasEffect(EffectRegister.COVENANT_MINER)) {return;}
        StacksHelper.addMinerStacks(player,3);
    }
}
