package io.github.kanybd1.wei.covenant;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static io.github.kanybd1.wei.covenant.covenants.CovenantEnd.ActiveCovenantEnd;
import static io.github.kanybd1.wei.covenant.covenants.CovenantForest.ActiveCovenantForest;
import static io.github.kanybd1.wei.covenant.covenants.CovenantFortress.ActiveCovenantFortress;
import static io.github.kanybd1.wei.covenant.covenants.CovenantKnowledge.ActiveCovenantKnowledge;
import static io.github.kanybd1.wei.covenant.covenants.CovenantMiner.ActiveCovenantMiner;
import static io.github.kanybd1.wei.covenant.covenants.CovenantOcean.ActiveCovenantOcean;
import static io.github.kanybd1.wei.covenant.covenants.CovenantPinpoint.ActiveCovenantPinpoint;


@EventBusSubscriber(modid = "wei")
public class CovenantActiveHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        ActiveCovenantFortress(event);
        ActiveCovenantEnd(event);
        ActiveCovenantMiner(event);
        ActiveCovenantOcean(event);
        ActiveCovenantKnowledge(event);
        ActiveCovenantForest(event);
        ActiveCovenantPinpoint(event);
    }
}