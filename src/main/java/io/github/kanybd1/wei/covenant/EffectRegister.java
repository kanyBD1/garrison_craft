package io.github.kanybd1.wei.covenant;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EVENTS = DeferredRegister.create(Registries.MOB_EFFECT,"wei");

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_FORTRESS = EVENTS.register("fortress", CovenantFortress::new);
}
