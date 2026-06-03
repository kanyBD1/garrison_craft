package io.github.kanybd1.wei.covenant.covenantStacks;

import net.minecraft.world.entity.player.Player;

public class StacksHelper {
    public static void addFortressStacks(Player player,int amount){
        IStack stack = player.getData(StackAttachmentType.STACK_FORTRESS.get());

        stack.setStack(Math.min(stack.getMaxStackSize(), stack.getStack() + amount));
    }
    public static int getFortressStacks(Player player){
        IStack stack = player.getData(StackAttachmentType.STACK_FORTRESS.get());
        return stack.getStack();
    }
    public static void addForestStacks(Player player,int amount){
        IStack stack = player.getData(StackAttachmentType.STACK_FOREST.get());

        stack.setStack(Math.min(stack.getMaxStackSize(), stack.getStack() + amount));
    }
    public static int getForestStacks(Player player){
        IStack stack = player.getData(StackAttachmentType.STACK_FOREST.get());
        return stack.getStack();
    }
}
