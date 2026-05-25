package io.github.kanybd1.wei.party1;

import net.minecraft.nbt.CompoundTag;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TeamSlim {
    private UUID teamId;
    private String name;

    private UUID leaderId;
    private Set<UUID> members;

    public  TeamSlim(UUID teamId, String name, UUID leaderId) {
        this.teamId = teamId;
        this.name = name;
        this.leaderId = leaderId;
        this.members = new HashSet<>();
        this.members.add(leaderId);
    }

    public CompoundTag save(CompoundTag compoundTag){

    }
}