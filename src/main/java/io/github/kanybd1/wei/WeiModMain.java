package io.github.kanybd1.wei;

import com.mojang.logging.LogUtils;
import io.github.kanybd1.wei.covenant.CovenantManager;
import io.github.kanybd1.wei.covenant.EffectRegister;
import io.github.kanybd1.wei.covenant.covenantConfig.CovenantConfig;
import io.github.kanybd1.wei.covenant.covenantStacks.StackAttachmentType;
import io.github.kanybd1.wei.network.SyncTeamPacket;
import io.github.kanybd1.wei.party.TeamManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
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

        modEventBus.addListener(this::registerPayloads);
    }

    // NOTE: 这里注意不要放在一起注册，先前的代码用 runServer 应该会直接错报服务端无法访问客户端数据。
    // 请注意为 Minecraft Dedicated Server（专用服务端）与 Minecraft Client（客户端，包含逻辑服务端与渲染端）。
    // 任何情况下 DServer 不应涉及任何带有 OnlyIn（Client） 的方法，也不应该干涉渲染，客户端逻辑等内容。
    /**
     * 数据封包注册于处理。参见客户端处 {@code WeiModClient#registerPayloadClient}
     */
    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MODID);

        registrar.playToClient(
                SyncTeamPacket.TYPE,
                SyncTeamPacket.STREAM_CODEC
        );
    }


}
