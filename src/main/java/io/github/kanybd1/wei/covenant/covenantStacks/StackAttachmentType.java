package io.github.kanybd1.wei.covenant.covenantStacks;

import io.github.kanybd1.wei.WeiModMain;
import net.neoforged.neoforge.attachment.AttachmentType;
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
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_END =
            ATTACHMENT_TYPES.register("end_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .build()
            );

    public static final Supplier<AttachmentType<IStack>> STACK_FOREST =
            ATTACHMENT_TYPES.register("forest_stack",()-> AttachmentType.<IStack>builder(()-> IStack.EMPTY)
                    .serialize(IStack.CODEC)
                    .copyOnDeath()
                    .build()
            );

}
