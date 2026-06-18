package io.github.kanybd1.wei.covenant.covenants;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.PINPOINT_COVENANT_ITEMS;
import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.PINPOINT_COVENANT_ITEM_VALUE;


public class CovenantPinpoint extends MobEffect {
    public CovenantPinpoint() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFFFF);
    }

    public static void giveNausea(int level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 100 + (level * 40 / 999)));
    }

    public static void ActiveCovenantPinpoint(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && PINPOINT_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += PINPOINT_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty() && PINPOINT_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += PINPOINT_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }

        if (totalValue > 1) {
            if (!player.hasEffect(EffectRegister.COVENANT_PINPOINT)) {
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID(), "End");
                player.addEffect(new MobEffectInstance(
                    EffectRegister.COVENANT_PINPOINT,
                    1000,
                    1
                ));
            }
        } else {
            if (player.hasEffect(EffectRegister.COVENANT_PINPOINT)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID(), "End");
                player.removeEffect(EffectRegister.COVENANT_PINPOINT);
            }
        }
    }
}
