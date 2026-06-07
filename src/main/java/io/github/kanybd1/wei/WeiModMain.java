package io.github.kanybd1.wei;

import com.mojang.logging.LogUtils;
import io.github.kanybd1.wei.covenant.CovenantManager;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.network.SyncTeamPacket;
import io.github.kanybd1.wei.party1.TeamManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

@Mod(WeiModMain.MODID)
public class WeiModMain {
    public static final String MODID = "wei";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CovenantManager COVENANT_MANAGER = new CovenantManager();
    public static TeamManager TEAM_MANAGER = new TeamManager();

    public WeiModMain(IEventBus modEventBus, ModContainer modContainer) {
        CovenantConfig.init();
        EffectRegister.EVENTS.register(modEventBus);
        StackAttachmentType.ATTACHMENT_TYPES.register(modEventBus);

        // ✅ 在这里注册网络包处理事件
        modEventBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MODID);

        registrar.playToClient(
                SyncTeamPacket.TYPE,
                SyncTeamPacket.STREAM_CODEC,
                SyncTeamPacket::handle
        );
    }
}