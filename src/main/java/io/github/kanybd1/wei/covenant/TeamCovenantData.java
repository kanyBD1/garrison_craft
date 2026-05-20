package io.github.kanybd1.wei.covenant;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TeamCovenantData {
    UUID teamId;
    Map<String, Integer> teamStacks;
    Set<String> activeResonances;
    int[] playerCounts;

    public void addTeamStacks(String covenantId, int amount) {
    }

    public void recalcResonances(Set<String> changedCovenants) {
    }

    Set<String> getActiveResonances() {
        return activeResonances;
    }
}
