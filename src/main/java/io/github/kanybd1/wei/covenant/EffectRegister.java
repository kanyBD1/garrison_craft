package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.covenant.covenants.CovenantEnd;
import io.github.kanybd1.wei.covenant.covenants.CovenantForest;
import io.github.kanybd1.wei.covenant.covenants.CovenantFortress;
import io.github.kanybd1.wei.covenant.covenants.CovenantMiner;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EVENTS = DeferredRegister.create(Registries.MOB_EFFECT,"wei");

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_FORTRESS = EVENTS.register("fortress", CovenantFortress::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_END = EVENTS.register("end", CovenantEnd::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_MINER = EVENTS.register("end", CovenantMiner::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_FOREST = EVENTS.register("end", CovenantForest::new);
}
