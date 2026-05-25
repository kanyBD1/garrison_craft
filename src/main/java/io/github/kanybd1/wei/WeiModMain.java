package io.github.kanybd1.wei;

import com.mojang.logging.LogUtils;
import io.github.kanybd1.wei.covenant.CovenantManager;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.StackComponent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Set;

@Mod(WeiModMain.MODID)
public class WeiModMain {
    public static final String MODID = "wei";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CovenantManager COVENANT_MANAGER = new CovenantManager();


    public static final Set<Item> FORTRESS_COVENANT_ITEMS = Set.of(
            Items.STONE_AXE,
            Items.STONE_SWORD,

            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_CHESTPLATE,
            Items.CHAINMAIL_LEGGINGS,
            Items.CHAINMAIL_BOOTS,

            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,

            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS,

            Items.SHIELD,
            Items.MACE
    );
    public static final Object2IntMap<Item> FORTRESS_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
    public WeiModMain(IEventBus modEventBus, ModContainer modContainer) {
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.STONE_SWORD, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.STONE_AXE, 1);

        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_BOOTS, 1);

       FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_BOOTS, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_CHESTPLATE, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_LEGGINGS, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_HELMET, 1);

       FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_BOOTS, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_CHESTPLATE, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_LEGGINGS, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_HELMET, 1);

       FORTRESS_COVENANT_ITEM_VALUE.put(Items.SHIELD, 1);
       FORTRESS_COVENANT_ITEM_VALUE.put(Items.MACE, 1);

       EffectRegister.EVENTS.register(modEventBus);


    }
}
