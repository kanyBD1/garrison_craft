package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.covenant.covenants.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EVENTS = DeferredRegister.create(Registries.MOB_EFFECT,"wei");

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_FORTRESS = EVENTS.register("fortress", CovenantFortress::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_END = EVENTS.register("end", CovenantEnd::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_MINER = EVENTS.register("miner", CovenantMiner::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_OCEAN = EVENTS.register("ocean", CovenantOcean::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_KNOWLEDGE = EVENTS.register("knowledge", CovenantKnowledge::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_FOREST = EVENTS.register("forest", CovenantKnowledge::new);

    public static final DeferredHolder<MobEffect,MobEffect> COVENANT_PINPOINT = EVENTS.register("pinpoint", CovenantKnowledge::new);

}
