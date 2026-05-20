package io.github.kanybd1.wei.covenant.party1;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public record TeamSlim(
    UUID teamId,
    String name,
    UUID leaderId,
    ImmutableList<UUID> members
) {
    public static TeamSlim load(CompoundTag compoundTag) {
        final UUID teamId = UUID.fromString(compoundTag.getString("teamId").orElseThrow());
        final String name = compoundTag.getString("name").orElseThrow();
        final UUID leaderId = UUID.fromString(compoundTag.getString("leaderId").orElseThrow());
        // TODO members
        // Fixme
        return new TeamSlim(teamId, name, leaderId, ImmutableList.of());
    }

    public void save(CompoundTag compoundTag) {
        compoundTag.putString("teamId", teamId.toString());
        compoundTag.putString("name", name);
        compoundTag.putString("leaderId", leaderId.toString());

        // TODO members
        compoundTag.putString("members", new Gson().toJson(members));
    }
}
