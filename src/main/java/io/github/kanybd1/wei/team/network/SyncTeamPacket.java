package io.github.kanybd1.wei.team.network;

import io.github.kanybd1.wei.team.client.ClientTeamData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;


import java.util.ArrayList;
import java.util.List;

public class SyncTeamPacket implements CustomPacketPayload {

    public static final Type<SyncTeamPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("wei", "sync_team"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    SyncTeamPacket::teamName,
                    ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.STRING_UTF8),
                    SyncTeamPacket::members,
                    SyncTeamPacket::new
            );

    private final String teamName;
    private final List<String> members;

    public SyncTeamPacket(String teamName, List<String> members) {
        this.teamName = teamName;
        this.members = members;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public String teamName() { return teamName; }
    public List<String> members() { return members; }

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
