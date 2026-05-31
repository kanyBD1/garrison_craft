package com.example.wei.covenant.party1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TeamManager {
    private final Map<UUID,TeamSlim> teams=new HashMap<>();
    private final Map<UUID, UUID>playerTeamMap=new HashMap<>();

    private final MinecraftServer server;
    public TeamManager(MinecraftServer server){
        this.server = server;
    }

    public TeamSlim createTeam(UUID leaderUUID , String leaderName){
        if(playerTeamMap.containsKey(leaderUUID)){return null;}
        UUID teamUUID = UUID.randomUUID();
        String teamName = leaderName+"'s Team";
        TeamSlim newTeam = new TeamSlim(teamUUID,teamName,leaderUUID);

        teams.put(teamUUID,newTeam);
        playerTeamMap.put(leaderUUID,teamUUID);
        return newTeam;
    }
    public void disbandTeam(String ServerPlayer){}
    public Optional<Team> getPlayerTeam(ServerPlayer player){}
    public void addToTeam(String ServerPlayer){}
    public void removeFromTeam(ServerPlayer player){}
    public void tick(){}

}
