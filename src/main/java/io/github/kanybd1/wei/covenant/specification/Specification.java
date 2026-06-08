package io.github.kanybd1.wei.covenant.specification;

import io.github.kanybd1.wei.WeiModMain;
import io.github.kanybd1.wei.bedwar.bagshop.ModItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import java.util.List;

@EventBusSubscriber(modid = WeiModMain.MODID)
public class Specification {

    private static final String TAG_HAS_RECEIVED_BOOK = "your_mod_id.has_received_guide_book";

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity().level().isClientSide()) {
            return;
        }

        CompoundTag persistentData = event.getEntity().getPersistentData();

        boolean hasReceived = persistentData.getBoolean(TAG_HAS_RECEIVED_BOOK)
                .orElse(false);

        if (hasReceived) {
            return;
        }

        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);

        List<Component> rawPages = List.of(
                // --- 第一页：封面 ---
                Component.literal("\n\n\n\n")
                        .append(Component.literal("卫戍工艺\n").withStyle(ChatFormatting.BOLD))
                        .append(Component.literal("—— 爱卫募集卡 ——\n\n"))
                        .append(Component.literal("作者：shm1131").withStyle(ChatFormatting.ITALIC)),

                // --- 第二页：森林盟约 ---
                Component.literal("§l§n【森林盟约】 §0(大盟约)\n\n")
                        .append("§0所需物品：苹果、皮革套装、弓、弩、可可果、藤蔓。\n")
                        .append("§7(当前价值均设为 1，暂定)\n\n")
                        .append("§0激活条件：消耗 2点 价值\n\n")
                        .append("§0✦ 核心效果：\n")
                        .append(" [个人] 弓箭命中目标附带 1-5点真实伤害\n")
                        .append(" [团队] 跳跃提升 I / II (暂未实现)\n\n")
                        .append("§0叠层方式：\n")
                        .append(" 弓箭每次命中目标叠加 9 层"),

                // --- 第三页：要塞盟约 ---
                Component.literal("§l§n【要塞盟约】 §0(大盟约)\n\n")
                        .append("§0所需物品：[待补充...]\n\n")
                        .append("§0激活条件：[待补充...]\n\n")
                        .append("§0✦ 核心效果：\n")
                        .append(" [个人] 获得 0-80点减伤\n")
                        .append(" [团队] 获得 0/1/2/3层 黄金血\n\n")
                        .append("§0叠层方式：\n")
                        .append(" 任何情况下每次受伤叠加 5 层"),

                // --- 第四页：海洋盟约 ---
                Component.literal("§l§n【海洋盟约】 §0(大盟约)\n\n")
                        .append("§0所需物品：三叉戟、生/熟桂鱼、鹦鹉螺壳、\n")
                        .append(" 海龟帽、海洋之心、潮涌核心\n\n")
                        .append("§0激活条件：[待补充...]\n\n")
                        .append("§0✦ 核心效果：\n")
                        .append(" [个人] 进入水中获得海豚的眷顾\n")
                        .append(" (其他水下增益尚未开发...)\n\n")
                        .append("§0叠层方式：\n")
                        .append(" 在水中停留时，每 1秒 叠加 1 层"),

                // --- 第五页：智识盟约 ---
                Component.literal("§l§n【智识盟约】 §0(小盟约·暂定)\n\n")
                        .append("§0所需物品：书本 x1 (价值 1)\n\n")
                        .append("§0触发条件：消耗 3点 价值\n\n")
                        .append("§0✦ 核心效果：\n")
                        .append(" [被动] 经验盾！\n")
                        .append(" 非真实伤害将全额由经验值承担！！！\n\n")
                        .append("§0叠层方式：\n")
                        .append(" 通过吸收经验球进行叠加"),

                // --- 第六页：精准盟约 ---
                Component.literal("§l§n【精准盟约】 §0(小盟约·暂定)\n\n")
                        .append("§0所需物品：各类箭矢 火焰弹 风弹 \n\n")
                        .append("鸡蛋 雪球 （价值均1） \n\n")
                        .append("§0触发条件：消耗 2点 价值\n\n")
                        .append("§0✦ 核心效果：\n")
                        .append(" [被动] 砸中了！\n")
                        .append(" 投掷物命中玩家给与5-7秒恶心！！！\n\n")
                        .append("§0叠层方式：\n")
                        .append(" 投掷物命中玩家，每次叠加9层")
        );


        List<Filterable<Component>> filterablePages = rawPages.stream()
                .map(Filterable::passThrough)
                .toList();


        WrittenBookContent bookContent = new WrittenBookContent(
                Filterable.passThrough("卫戍工艺"),
                "shm1131",
                0,
                filterablePages,
                false
        );


        book.set(DataComponents.WRITTEN_BOOK_CONTENT, bookContent);

        if (WeiModMain.SHOP_OPENER != null) {
            ItemStack opener = new ItemStack(WeiModMain.SHOP_OPENER);
            if (!event.getEntity().getInventory().add(opener)) {
                event.getEntity().drop(opener, false);
            }
        } else {
            WeiModMain.LOGGER.error("SHOP_OPENER is null! Item registration may have failed.");
        }

        if (!event.getEntity().getInventory().add(book)) {
            event.getEntity().drop(book, false);
        }

        persistentData.putBoolean(TAG_HAS_RECEIVED_BOOK, true);

    }
}