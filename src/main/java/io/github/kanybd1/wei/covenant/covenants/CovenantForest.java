package io.github.kanybd1.wei.covenant.covenants;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.EffectRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.FOREST_COVENANT_ITEMS;
import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.FOREST_COVENANT_ITEM_VALUE;


public class CovenantForest extends MobEffect {
    public CovenantForest() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFFFF);
    }

    public static float addDamage(int level) {
        return (level / 250f + 1);
    }

    public static void applyJump(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 200, 0));
    }

    public static void ActiveCovenantForest(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && FOREST_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += FOREST_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty() && FOREST_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += FOREST_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }

        if (totalValue > 1) {
            if (!player.hasEffect(EffectRegister.COVENANT_FOREST)) {
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID(), "Forest");
                player.addEffect(new MobEffectInstance(
                    EffectRegister.COVENANT_FOREST,
                    1000,
                    1
                ));
            }
        } else {
            if (player.hasEffect(EffectRegister.COVENANT_FOREST)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID(), "Forest");
                player.removeEffect(EffectRegister.COVENANT_FOREST);
            }
        }
    }
}
