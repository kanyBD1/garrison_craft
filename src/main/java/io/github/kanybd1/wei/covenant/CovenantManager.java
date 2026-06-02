package io.github.kanybd1.wei.covenant;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CovenantManager{

    private static Set<UUID> activeCovenants;
    private static Map<UUID,Set<String>> covenants = Maps.newHashMap();

    public void activeCovenant(UUID uuid, String covenantName) {
        if(!covenants.containsKey(uuid)){
            covenants.put(uuid,Sets.newHashSet());
        }
        if(covenants.get(uuid).contains(covenantName)){
            return;
        }
        covenants.get(uuid).add(covenantName);


    }

    public void removeCovenant(UUID uuid, String covenantName) {
       if(!covenants.containsKey(uuid)){
           return;
       }
       if (!covenants.get(uuid).contains(covenantName)) {
           return;
       }
       covenants.get(uuid).remove(covenantName);
    }

}
