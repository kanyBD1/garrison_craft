package com.example.wei.covenant;

import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.ObjectUtils;

public interface ICovenant {
    String getId();
    int getRequiredPlayers();
    int getMaxStacks();
    int getRequiredItem();
    int getStacks();

}
