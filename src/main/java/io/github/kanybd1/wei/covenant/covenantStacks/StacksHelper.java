package io.github.kanybd1.wei.covenant.covenantStacks;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public class StacksHelper {

    public static void addStack(Player player, Supplier<AttachmentType<IStack>> typeSupplier, int amount) {
        AttachmentType<IStack> type = typeSupplier.get();
        IStack current = player.getData(type);

        int newAmount = Math.min(current.getMaxStackSize(), current.getStack() + amount);

        IStack newStack = new IStack.stackData(current.getMaxStackSize(), newAmount);
        player.setData(type, newStack);
    }

    public static void addFortressStacks(Player player, int amount){
        addStack(player, StackAttachmentType.STACK_FORTRESS, amount);
    }

    public static void addMinerStacks(Player player, int amount){
        addStack(player, StackAttachmentType.STACK_MINER, amount);
    }

    public static void addEndStacks(Player player, int amount){
        addStack(player, StackAttachmentType.STACK_END, amount);
    }

    public static void addOceanStacks(Player player, int amount){
        addStack(player, StackAttachmentType.STACK_OCEAN, amount);
    }

    public static void addKnowledgeStacks(Player player, int amount){
        addStack(player, StackAttachmentType.STACK_KNOWLEDGE, amount);
    }

    public static void addForestStacks(Player player, int amount){
        addStack(player, StackAttachmentType.STACK_FOREST, amount);
    }

    public static int getStack(Player player, Supplier<AttachmentType<IStack>> typeSupplier) {
        return player.getData(typeSupplier.get()).getStack();
    }
}
