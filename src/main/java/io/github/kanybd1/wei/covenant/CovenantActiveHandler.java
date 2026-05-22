package io.github.kanybd1.wei.covenant;

import io.github.kanybd1.wei.WeiModMain;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


@EventBusSubscriber(modid = "wei")
public class CovenantActiveHandler {

    public static final Object2IntMap FORTRESS_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap();
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        int totalValue = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && WeiModMain.FORTRESS_COVENANT_ITEMS.contains(stack.getItem())) {
                totalValue += WeiModMain.FORTRESS_COVENANT_ITEM_VALUE.getInt(stack.getItem());
            }
        }
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()&&WeiModMain.FORTRESS_COVENANT_ITEMS.contains(itemStack.getItem())) {
                totalValue += WeiModMain.FORTRESS_COVENANT_ITEM_VALUE.getInt(itemStack.getItem());
            }
        }
        if (totalValue > 2) {
            //这里我该怎么激活盟约
        }
    }
}
