package io.github.kanybd1.wei.party1;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class ClientBoundTeamSyncPacket {
    private List<TeamSlim> commands;
    private List<String> removedTeamIds;

    public void encode(FriendlyByteBuf friendlyByteBuf){}
    public void decode(FriendlyByteBuf friendlyByteBuf){}
    public void handle(LocalPlayer Level){}

}
