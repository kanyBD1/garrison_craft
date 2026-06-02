package io.github.kanybd1.wei.covenant.covenantStacks;

import net.minecraft.world.entity.player.Player;

public class StacksHelper {
    public static void addFortressStacks(Player player,int amount){
        IStack stack = player.getData(StackAttachmentType.STACK_FORTRESS.get());
        stack.setStack(Math.max(stack.getMaxStackSize(), stack.getStack() + amount));
    }
    public static int getFortressStacks(Player player){
        IStack stack = player.getData(StackAttachmentType.STACK_FORTRESS.get());
        return stack.getStack();
    }

    public static void addMinerStacks(Player player,int amount){
        IStack stack = player.getData(StackAttachmentType.STACK_MINER.get());

        stack.setStack(Math.max(stack.getMaxStackSize(), stack.getStack() + amount));
    }
    public static int getMinerStacks(Player player){
        IStack stack = player.getData(StackAttachmentType.STACK_MINER.get());
        return stack.getStack();
    }

    public static void addEndStacks(Player player,int amount){
        IStack stack = player.getData(StackAttachmentType.STACK_END.get());

        stack.setStack(Math.max(stack.getMaxStackSize(), stack.getStack() + amount));
    }
    public static int getEndStacks(Player player){
        IStack stack = player.getData(StackAttachmentType.STACK_END.get());
        return stack.getStack();
    }

    public static void addOceanStacks(Player player,int amount){
        IStack stack = player.getData(StackAttachmentType.STACK_END.get());

        stack.setStack(Math.max(stack.getMaxStackSize(), stack.getStack() + amount));
    }
    public static int getOceanStacks(Player player){
        IStack stack = player.getData(StackAttachmentType.STACK_END.get());
        return stack.getStack();
    }

}
