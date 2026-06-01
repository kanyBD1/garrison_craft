package io.github.kanybd1.wei.covenant.covenantStacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public interface IStack {
    int getMaxStackSize();
    void setMaxStackSize(int maxStackSize);
    int getStack();
    void setStack(int stack);

    MapCodec<IStack> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("max_stack_size").forGetter(IStack::getMaxStackSize),
            Codec.INT.fieldOf("stack").forGetter(IStack::getStack)
    ).apply(instance, stackData::new));

    class stackData implements IStack {
        private int maxStack;
        private int stack;
        public stackData(int maxStack, int stack) {
            this.maxStack = maxStack;
            this.stack = stack;
        }
        @Override
        public int getMaxStackSize () {return maxStack;}

        @Override
        public int getStack () {return stack;}

        @Override
        public void setMaxStackSize ( int maxStackSize){}

        @Override
        public void setStack ( int stack){}


    }

    IStack EMPTY = new stackData(999,0);

}
