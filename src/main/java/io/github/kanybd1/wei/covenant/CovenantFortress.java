package io.github.kanybd1.wei.covenant;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CovenantFortress extends MobEffect {
    public CovenantFortress() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }
    public void activeCovenantFortress(LivingEntity livingEntity) {

    }
    public static float damageReduction(float damage, int level){
        return (1 - level / 999f * 0.8f) * damage;
    }
}
