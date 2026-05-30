package io.github.kanybd1.wei.party1;

import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class TeamManager {
    private Map<String, Team> teams;

    private Map<UUID,String>playerTeamMap;

    public TeamManager(){
        teams = new HashMap<>();
        playerTeamMap = new HashMap<>();
    }

    public Team createTeam(ServerPlayer ServerPlayer){
        String teamName=ServerPlayer.getDisplayName().getString()+"'s Team";
        Team newTeam = new Team(UUID.randomUUID(),teamName,ServerPlayer.getUUID());
        teams.put(teamName,newTeam);
        playerTeamMap.put(ServerPlayer.getUUID(),teamName);
        return newTeam;
    }

    public void disbandTeam(ServerPlayer ServerPlayer){

    }

    public Optional<Team> getPlayerTeam(ServerPlayer player){
        String teamId=playerTeamMap.get(player.getUUID());
        if(teamId==null) {
            return Optional.empty();
        }
        Team team=teams.get(teamId);
        return Optional.ofNullable(team);
    }

    public Boolean addToTeam(ServerPlayer ServerPlayer,String teamName){
        UUID playerUUID=ServerPlayer.getUUID();
        Team team=teams.get(teamName);
        if(team==null){
            System.out.println("队伍不存在: " +teamName);
            return false;
        }

        String playerTeamName=playerTeamMap.get(playerUUID);

        if(playerTeamName!=null&&!playerTeamName.equals(teamName)){
            removeFromTeam(ServerPlayer);
        }

        else if(playerTeamName!=null&&playerTeamName.equals(teamName)){
            System.out.println("已在此队伍："+teamName);
            return true;
        }

        boolean added = this.addToTeam(ServerPlayer,teamName);

        if(added){
            playerTeamMap.put(playerUUID,teamName);
            System.out.println("成功加入此队伍："+teamName);
            return true;
        }
        return false;
    }

    public boolean removeFromTeam(ServerPlayer player){
        UUID playerUUID=player.getUUID();
        String currentTeamId = this.playerTeamMap.remove(playerUUID);

        if(currentTeamId!=null){
            Team team=teams.get(currentTeamId);
            if(team!=null){
                teams.remove(player);
                System.out.println("成功退出队伍："+currentTeamId);
                return true;
            }
        }
        return false;
    }

    public Set<String> getTeamNames(){
        return teams.keySet();
    }

    public Set<UUID> getTeamMembers(String teamName){
        Team team=teams.get(teamName);
        return team.getMembers();
    }
}
