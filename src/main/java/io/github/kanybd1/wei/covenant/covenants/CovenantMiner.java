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

public class CovenantMiner extends MobEffect {
    public CovenantMiner() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }

    public static void applyHaste(Player player) {player.addEffect(new MobEffectInstance(MobEffects.HASTE, 200, 0));}

    public static void ActiveCovenantMiner(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && WeiModMain.MINER_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += WeiModMain.MINER_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()&&WeiModMain.MINER_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += WeiModMain.MINER_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }
        if (totalValue > 2) {
            if(!player.hasEffect(EffectRegister.COVENANT_MINER)){
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID());
                player.addEffect(new MobEffectInstance(EffectRegister.COVENANT_MINER, 200, 0, false, false));
            }
        }
        else{
            if(player.hasEffect(EffectRegister.COVENANT_MINER)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID());
                player.removeEffect(EffectRegister.COVENANT_MINER);
            }
        }
    }
}
