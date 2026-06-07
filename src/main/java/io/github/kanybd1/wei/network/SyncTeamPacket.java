package io.github.kanybd1.wei.network;

import io.github.kanybd1.wei.client.ClientTeamData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;


import java.util.ArrayList;
import java.util.List;

public class SyncTeamPacket implements CustomPacketPayload {
    // 1. 使用 Identifier 定义唯一的 Type 标识符
    public static final Type<SyncTeamPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("wei", "sync_team"));

    // 2. 定义 StreamCodec 用于自动序列化和反序列化
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    SyncTeamPacket::teamName, // Getter 引用
                    ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.STRING_UTF8),
                    SyncTeamPacket::members, // Getter 引用
                    SyncTeamPacket::new // 构造方法引用
            );

    private final String teamName;
    private final List<String> members;

    // 3. 提供全参构造方法（供 StreamCodec 调用）
    public SyncTeamPacket(String teamName, List<String> members) {
        this.teamName = teamName;
        this.members = members;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Getter 方法（供 StreamCodec 提取数据）
    public String teamName() { return teamName; }
    public List<String> members() { return members; }

    // 4. 客户端处理逻辑
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (this.teamName == null || this.teamName.isEmpty()) {
                ClientTeamData.INSTANCE.clearTeam();
            } else {
                ClientTeamData.INSTANCE.updateTeam(this.teamName, this.members);
            }
        });
    }
}