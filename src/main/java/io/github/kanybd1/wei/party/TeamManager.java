package io.github.kanybd1.wei.party;

import io.github.kanybd1.wei.network.SyncTeamPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class TeamManager {
    private Map<String, Team> teams;

    private Map<UUID,String>playerTeamMap;

    public TeamManager(){
        teams = new HashMap<>();
        playerTeamMap = new HashMap<>();
    }

    public void syncTeamToPlayer(ServerPlayer player) {
        Optional<Team> teamOpt = getPlayerTeam(player);
        if (teamOpt.isEmpty()) {
            PacketDistributor.sendToPlayer(player, new SyncTeamPacket(null, Collections.emptyList()));
            return;
        }
        Team team = teamOpt.get();
        List<String> memberNames = new ArrayList<>();

        for (UUID memberUuid : team.getMembers()) {
            ServerPlayer member = player.level().getServer().getPlayerList().getPlayer(memberUuid);
            if (member != null) {
                memberNames.add(member.getName().getString());
            }
        }

        PacketDistributor.sendToPlayer(player, new SyncTeamPacket(team.getTeamName(), memberNames));
    }

    public Team createTeam(ServerPlayer ServerPlayer){
        String teamName=ServerPlayer.getDisplayName().getString()+"'s Team";
        Team newTeam = new Team(UUID.randomUUID(),teamName,ServerPlayer.getUUID());
        newTeam.addMember(ServerPlayer);
        teams.put(teamName,newTeam);
        playerTeamMap.put(ServerPlayer.getUUID(),teamName);
        ServerPlayer.sendSystemMessage(Component.literal("创建队伍: " +teamName));
        syncTeamToPlayer(ServerPlayer);
        return newTeam;
    }

    public Optional<Team> getPlayerTeam(ServerPlayer player){
        String teamId=playerTeamMap.get(player.getUUID());
        if(Objects.isNull(teamId)) {
            return Optional.empty();
        }

        Team team=teams.get(teamId);
        return Optional.ofNullable(team);
    }

    public Boolean addToTeam(ServerPlayer ServerPlayer,String teamName){
        UUID playerUUID=ServerPlayer.getUUID();
        Team team=teams.get(teamName);
        if(Objects.isNull(team)) {
            ServerPlayer.sendSystemMessage(Component.literal("队伍不存在: " +teamName));
            return false;
        }

        String playerTeamName=playerTeamMap.get(playerUUID);

        if (Objects.nonNull(playerTeamName)) {
            if (playerTeamName.equals(teamName)) {
                ServerPlayer.sendSystemMessage(Component.literal("已在此队伍："+teamName));
                return true;
            }

            removeFromTeam(ServerPlayer);
        }

        boolean added = team.addMember(ServerPlayer);

        if(!added){
            return false;
        }

        playerTeamMap.put(playerUUID,teamName);
        ServerPlayer.sendSystemMessage(Component.literal("成功加入此队伍："+teamName));
        syncTeamToPlayer(ServerPlayer);
        return true;
    }

    public boolean removeFromTeam(ServerPlayer player){
        UUID playerUUID=player.getUUID();
        String currentTeamName = this.playerTeamMap.remove(playerUUID);

        if(Objects.isNull(currentTeamName)) {
            return false;
        }

        Team team=teams.get(currentTeamName);
        if(Objects.isNull(team)){
            return false;
        }

        team.removeMember(player);
        player.sendSystemMessage(Component.literal("成功退出队伍："+ currentTeamName));
        syncTeamToPlayer(player);
        return true;
    }

    public Set<String> getTeamNames(){
        return teams.keySet();
    }

    public Set<UUID> getTeamMembers(String teamName){
        Team team=teams.get(teamName);
        return team.getMembers();
    }
}
