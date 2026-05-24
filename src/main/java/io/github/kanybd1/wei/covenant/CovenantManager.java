package io.github.kanybd1.wei.covenant;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

public class CovenantManager{
    private final List<UUID> covenants = Lists.newArrayListWithCapacity(1);

    public void activeCovenant(UUID uuid) {
        if (covenants.contains(uuid)) {
            return;
        }

        covenants.add(uuid);
    }

    public void removeCovenant(UUID uuid) {
        covenants.remove(uuid);
    }

    public ImmutableList<UUID> getCovenants() {
        return ImmutableList.copyOf(covenants);
    }
}
