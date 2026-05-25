package com.example.wei.covenant;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerCovenantData {
    UUID PlayerId;
    Set<String>ActiveCovenants;
    Map<String, Integer> personalStacks;

    public void upDataActiveFromInventory() {

    }
    Set<String> getActiveCovenants() {
        return ActiveCovenants;
    }
    public void addPersonalStack(String covenantId, int amount) {
        personalStacks.put(PlayerId.toString(), amount);
    }
}

