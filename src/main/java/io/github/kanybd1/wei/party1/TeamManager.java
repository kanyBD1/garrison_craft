package io.github.kanybd1.wei.party1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TeamManager {
    private Map<String, Team> teams;
    private Map<UUID,String>playerTeamMap;

    private MinecraftServer server;
    public TeamManager(MinecraftServer server){this.server = server;}
    public TeamSlim createTeam(String ServerPlayer){

    }
    public void disbandTeam(String ServerPlayer){}
    public Optional<Team> getPlayerTeam(ServerPlayer player){}
    public void addToTeam(String ServerPlayer){}
    public void removeFromTeam(ServerPlayer player){}
    public void tick(){}

}
