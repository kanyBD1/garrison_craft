package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.WeiModMain;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CovenantFortress extends MobEffect {
    public CovenantFortress() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }
    public void activeCovenantFortress(LivingEntity livingEntity) {}

    public static float damageReduction(float damage, int level){
        return (1 - level / 999f * 0.8f) * damage;
    }

    public static void applyAbsorption(int value,Player player){ player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, value-2)); }
}
