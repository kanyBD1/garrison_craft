package com.example.wei.covenant.party1;

import net.minecraft.server.level.ServerPlayer;

public class Team {
    private TeamSlim data;
    public  Team(TeamSlim data) {this.data=data;}

    public void addMember(ServerPlayer player){}
    public void removeMember(ServerPlayer player){}
    public void Leader(ServerPlayer player){}
}
