package io.github.kanybd1.wei.covenant.covenantStacks;

import io.github.kanybd1.wei.WeiModMain;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class StackAttachmentType {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WeiModMain.MODID);

    public static final Supplier<AttachmentType<IStack>> STACK_FORTRESS =
            ATTACHMENT_TYPES.register("fortress_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_END =
            ATTACHMENT_TYPES.register("end_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_MINER =
            ATTACHMENT_TYPES.register("miner_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_OCEAN =
            ATTACHMENT_TYPES.register("ocean_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_KNOWLEDGE =
            ATTACHMENT_TYPES.register("knowledge_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_FOREST =
            ATTACHMENT_TYPES.register("forest_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_PINPOINT =
            ATTACHMENT_TYPES.register("pinpoint_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .sync(IStack.STREAM_CODEC)
                    .build()
            );
}
