package io.github.kanybd1.wei.bedwar.bagshop.client;

import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientShopData {
    // 初始化为默认值，防止NPE
    private static PlayerShopData cachedData = new PlayerShopData(0,1, Set.of());

    public static void update(PlayerShopData data) {
        cachedData = data;
    }

    public static PlayerShopData get() {
        return cachedData;
    }

    public static void clear() {
        cachedData = new PlayerShopData(0,1,Set.of());
    }
}