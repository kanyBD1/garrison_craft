package io.github.kanybd1.wei.covenant.covenantStacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface IStack {
    int getMaxStackSize();
    int getStack();

    StreamCodec<FriendlyByteBuf, IStack> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(FriendlyByteBuf buf, IStack stack) {
            buf.writeVarInt(stack.getMaxStackSize());
            buf.writeVarInt(stack.getStack());
        }

        @Override
        public IStack decode(FriendlyByteBuf buf) {
            return new stackData(buf.readVarInt(), buf.readVarInt());
        }
    };

    MapCodec<IStack> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("max_stack_size").forGetter(IStack::getMaxStackSize),
            Codec.INT.fieldOf("stack").forGetter(IStack::getStack)
    ).apply(instance, stackData::new));

    record stackData (int maxStack,int stack) implements IStack {

        @Override
        public int getMaxStackSize () {return maxStack;}

        @Override
        public int getStack () {return stack;}
    }

    IStack EMPTY = new stackData(999,0);
}
