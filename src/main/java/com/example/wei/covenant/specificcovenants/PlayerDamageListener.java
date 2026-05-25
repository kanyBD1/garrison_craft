package com.example.wei.covenant.specificcovenants;

import com.example.wei.covenant.ICovenant;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;


public class PlayerDamageListener {

    public class Covenant_Fortress extends AbstractCovenant implements ICovenant, AbstractCovenantEffect {
        int stacks = 0;

        String name = "Fortress";

        public String getId() {
            return "Fortress";
        }

        public int getRequiredPlayers() {
            return 2;
        }

        public int getMaxStacks() {
            return 999;
        }

        public int getRequiredItem() {
            return 2;
        }

        public int getStacks() {
            return stacks;
        }
        @Override
        public float applyDamageReduction(float damage) {
            int level = getStacks();
            float newDamage = (float) (1-level/999*0.8);
            return newDamage;
        }

        @EventBusSubscriber(modid = "wei")
        public class PlayerDamageHandler {

            @SubscribeEvent
            public void onPlayerDamage(LivingDamageEvent.Pre event) {
                if (event.getEntity() instanceof Player player) {
                    float currentDamage = event.getNewDamage();
                    event.setNewDamage(applyDamageReduction(currentDamage));
                }
            }
        }



    }
}
