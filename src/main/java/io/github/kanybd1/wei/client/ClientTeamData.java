package io.github.kanybd1.wei.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class ClientTeamData {
    public static final ClientTeamData INSTANCE = new ClientTeamData();

    private String currentTeamName = null;
    private List<String> memberNames = new ArrayList<>();

    public void updateTeam(String teamName, List<String> members) {
        this.currentTeamName = teamName;
        this.memberNames = members;
    }

    public void clearTeam() {
        this.currentTeamName = null;
        this.memberNames.clear();
    }

    public boolean isInTeam() {
        return currentTeamName != null && !memberNames.isEmpty();
    }

    public String getCurrentTeamName() { return currentTeamName; }
    public List<String> getMemberNames() { return memberNames; }
}