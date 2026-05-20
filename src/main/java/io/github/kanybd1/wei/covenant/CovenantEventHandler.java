package io.github.kanybd1.wei.covenant;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "wei")
public class CovenantEventHandler {
    @SubscribeEvent
    public void onContainerChange(PlayerContainerEvent event) {

    }

    @SubscribeEvent
    public void onItemUse(PlayerInteractEvent event) {
    }
}
