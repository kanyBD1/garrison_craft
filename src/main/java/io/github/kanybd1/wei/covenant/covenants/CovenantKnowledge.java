package io.github.kanybd1.wei.covenant.covenants;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantStacks.StacksHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.KNOWLEDGE_COVENANT_ITEMS;
import static io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig.KNOWLEDGE_COVENANT_ITEM_VALUE;

public class CovenantKnowledge extends MobEffect{
    public CovenantKnowledge() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFFFF);
    }

    public static void giveExperience(Player player, int amount){player.giveExperiencePoints(amount/10);}

    public static float experienceBlock(Player player,float damage){
        int experienceLevel = player.experienceLevel;
        if(experienceLevel > damage){
            player.giveExperienceLevels((int)-damage);
            return 0;
        }
        else if(experienceLevel <= damage){
            player.giveExperienceLevels(-experienceLevel);
            return damage-experienceLevel;
        }
        return 0;
    }

    public static void ActiveCovenantKnowledge(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && KNOWLEDGE_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += KNOWLEDGE_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()&&KNOWLEDGE_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += KNOWLEDGE_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }
        if (totalValue > 2) {
            if(!player.hasEffect(EffectRegister.COVENANT_KNOWLEDGE)){
                WeiModMain.COVENANT_MANAGER.activeCovenant(player.getUUID(),"Knowledge");
                player.addEffect(new MobEffectInstance(EffectRegister.COVENANT_KNOWLEDGE, 40, StacksHelper.getKnowledgeStacks(player)));
            }
        }
        else{
            if(player.hasEffect(EffectRegister.COVENANT_KNOWLEDGE)) {
                WeiModMain.COVENANT_MANAGER.removeCovenant(player.getUUID(),"Knowledge");
                player.removeEffect(EffectRegister.COVENANT_KNOWLEDGE);
            }
        }
    }
}

