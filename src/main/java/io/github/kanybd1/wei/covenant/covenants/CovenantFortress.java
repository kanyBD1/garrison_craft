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

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.FORTRESS_COVENANT_ITEMS;
import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.FORTRESS_COVENANT_ITEM_VALUE;

public class CovenantFortress extends MobEffect {
    public CovenantFortress() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFFFF);
    }

    public static float damageReduction(float damage, int level){
        return (1 - level / 999f * 0.8f) * damage;
    }

    public static void applyAbsorption(int value,Player player){ player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, value*200, value-2)); }

    public static void ActiveCovenantFortress(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && FORTRESS_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += FORTRESS_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()&&FORTRESS_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += FORTRESS_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }
        if (totalValue > 1) {
            if(!player.hasEffect(EffectRegister.COVENANT_FORTRESS)){
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID(),"Fortress");
                player.addEffect(new MobEffectInstance(EffectRegister.COVENANT_FORTRESS, 1000, StacksHelper.getStack(player, StackAttachmentType.STACK_FORTRESS)));
            }
        }
        else{
            if(player.hasEffect(EffectRegister.COVENANT_FORTRESS)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID(),"Fortress");
                player.removeEffect(EffectRegister.COVENANT_FORTRESS);
            }
        }
    }



}
