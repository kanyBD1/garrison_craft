package io.github.kanybd1.wei.party1;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Team {
    private String teamId;
    private String teamName;
    private Set<UUID> members;
    private UUID leader;


    public Team(UUID teamId, String name, UUID leaderId) {
        this.teamId=teamId.toString();
        this.teamName=name;
        this.leader=leaderId;
        this.members=new HashSet<>();
    }

    public Boolean addMember(ServerPlayer player){
        if (player == null) return false;

        UUID uuid = player.getUUID();

        if (this.members.contains(uuid)) {
            return false;
        }

        this.members.add(uuid);

        return true;

    }

    public Boolean removeMember(ServerPlayer player){
        if (player == null) return false;

        if (this.members.contains(player.getUUID())) {
            this.members.remove(player.getUUID());
            return true;
        }
        return false;
    }

    public Boolean setLeader(ServerPlayer player){
        if (player == null) return false;

        if (this.leader.equals(player.getUUID())) {
            return false;
        }
        this.leader = player.getUUID();
        return true;
    }

    public Set<UUID> getMembers(){
        return this.members;
    }
}
