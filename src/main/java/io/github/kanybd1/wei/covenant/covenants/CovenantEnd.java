package io.github.kanybd1.wei.covenant.covenants;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class CovenantEnd extends MobEffect {
    public CovenantEnd() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }

    public static void applySpeed(Player player,int amount) {player.addEffect(new MobEffectInstance(MobEffects.SPEED, amount,amount/100));}
    public static void applyInvisible(Player player,int amount) {player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, amount/5,0));}
    public static void ActiveCovenantEnd(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && WeiModMain.END_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += WeiModMain.END_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()&&WeiModMain.END_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += WeiModMain.END_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }
        if (totalValue > 2) {
            if(!player.hasEffect(EffectRegister.COVENANT_END)){
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID(),"End");
                player.addEffect(new MobEffectInstance(EffectRegister.COVENANT_END, 200, StacksHelper.getEndStacks(player)));
            }
        }
        else{
            if(player.hasEffect(EffectRegister.COVENANT_END)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID(),"End");
                player.removeEffect(EffectRegister.COVENANT_END);
            }
        }
    }
}
