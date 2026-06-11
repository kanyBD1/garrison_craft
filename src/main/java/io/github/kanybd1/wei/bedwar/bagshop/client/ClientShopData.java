package io.github.kanybd1.wei.bedwar.bagshop.client;

import io.github.kanybd1.wei.bedwar.bagshop.data.PlayerShopData;
import io.github.kanybd1.wei.bedwar.bagshop.gui.ShopScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientShopData {
    // 初始化为默认值，防止NPE
    private static PlayerShopData cachedData = new PlayerShopData(0, 1, Set.of());

    public static void update(PlayerShopData data) {
        cachedData = data;

        // ⭐ 核心修复：数据更新时，如果玩家正打开着商店界面，立即通知其刷新显示
        // 必须使用 Minecraft.getInstance() 获取客户端实例，不能在静态初始化块中直接调用
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof ShopScreen shopScreen) {
            shopScreen.updateDisplayFromCache();
        }
    }

    public static PlayerShopData get() {
        return cachedData;
    }

    public static void clear() {
        cachedData = new PlayerShopData(0, 1, Set.of());

        // ⭐ 补充：断开连接或退出世界时，同样需要清理GUI状态（可选但推荐）
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof ShopScreen shopScreen) {
            shopScreen.updateDisplayFromCache();
        }
    }
}