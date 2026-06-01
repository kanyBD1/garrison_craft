package io.github.kanybd1.wei;

import com.mojang.logging.LogUtils;
import io.github.kanybd1.wei.covenant.CovenantManager;
import io.github.kanybd1.wei.covenant.EffectRegister;

import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.party1.TeamManager;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import org.slf4j.Logger;

import java.util.Set;

@Mod(WeiModMain.MODID)
public class WeiModMain {
    public static final String MODID = "wei";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static CovenantManager COVENANT_MANAGER;
    public static TeamManager TEAM_MANAGER;

    public static Set<Item> FORTRESS_COVENANT_ITEMS;
    public static Set<Item> END_COVENANT_ITEMS;
    public static Set<Item> MINER_COVENANT_ITEMS;

    public static Object2IntMap<Item> FORTRESS_COVENANT_ITEM_VALUE;
    public static Object2IntMap<Item> END_COVENANT_ITEM_VALUE;
    public static Object2IntMap<Item> MINER_COVENANT_ITEM_VALUE;

    public WeiModMain(IEventBus modEventBus, ModContainer modContainer) {

        COVENANT_MANAGER = new CovenantManager();
        TEAM_MANAGER = new TeamManager();

        MINER_COVENANT_ITEMS = Set.of(
                Items.IRON_PICKAXE,
                Items.DIAMOND_PICKAXE,
                Items.TNT,
                Items.NETHERITE_PICKAXE,
                Items.WATER_BUCKET,
                Items.LAVA_BUCKET,
                Items.BUCKET
        );

        END_COVENANT_ITEMS = Set.of(
                Items.ENDER_EYE,
                Items.ENDER_PEARL
        );

        FORTRESS_COVENANT_ITEMS = Set.of(
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

        FORTRESS_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.STONE_SWORD, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.STONE_AXE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.IRON_BOOTS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.CHAINMAIL_BOOTS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_HELMET, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_CHESTPLATE, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_LEGGINGS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.DIAMOND_BOOTS, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.SHIELD, 1);
        FORTRESS_COVENANT_ITEM_VALUE.put(Items.MACE, 1);

        // 初始化 END
        END_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
        END_COVENANT_ITEM_VALUE.put(Items.ENDER_EYE, 1);
        END_COVENANT_ITEM_VALUE.put(Items.ENDER_PEARL, 1);

        // 初始化 MINER
        MINER_COVENANT_ITEM_VALUE = new Object2IntOpenHashMap<>();
        MINER_COVENANT_ITEM_VALUE.put(Items.IRON_INGOT, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.DIAMOND_PICKAXE, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.TNT, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.NETHERITE_PICKAXE, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.BUCKET, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.WATER_BUCKET, 1);
        MINER_COVENANT_ITEM_VALUE.put(Items.LAVA_BUCKET, 1);


        EffectRegister.EVENTS.register(modEventBus);
        StackAttachmentType.ATTACHMENT_TYPES.register(modEventBus);

    }
}
