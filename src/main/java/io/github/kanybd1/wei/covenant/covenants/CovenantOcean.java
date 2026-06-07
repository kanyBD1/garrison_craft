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

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.OCEAN_COVENANT_ITEMS;
import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.OCEAN_COVENANT_ITEM_VALUE;


public class CovenantOcean extends MobEffect {
    public CovenantOcean() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFFFF);
    }
    public static void applyDolphinsGrace(Player player) {player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100, 0));}

    public static void ActiveCovenantOcean(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && OCEAN_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += OCEAN_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()&&OCEAN_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += OCEAN_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }
        if (totalValue > 2) {
            if(!player.hasEffect(EffectRegister.COVENANT_OCEAN)){
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID(),"Ocean");
                player.addEffect(new MobEffectInstance(EffectRegister.COVENANT_OCEAN, 40, StacksHelper.getStack(player, StackAttachmentType.STACK_OCEAN)));
            }
        }
        else{
            if(player.hasEffect(EffectRegister.COVENANT_OCEAN)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID(),"Ocean");
                player.removeEffect(EffectRegister.COVENANT_OCEAN);
            }
        }
    }
}
