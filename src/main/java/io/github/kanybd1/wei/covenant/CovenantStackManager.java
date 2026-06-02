package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
}
