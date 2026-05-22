package com.example.wei.covenant;

import java.util.Map;

public class CovenantManager {
    private Map<String,ICovenant> covenants;

    public void registerCovenant(ICovenant covenants) {}

    ICovenant getCovenant(String id) {
        return covenants.get(id);
    }
}
