package dev.hyperlynx.hemisphere;

import com.mojang.logging.LogUtils;
import dev.hyperlynx.hemisphere.remorphed.MorphAttackAnimations;
import dev.hyperlynx.hemisphere.remorphed.net.MorphMessages;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

@Mod(Hemisphere.MODID)
public class Hemisphere {
    public static final String MODID = "hemisphere";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            location("simple_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public Hemisphere(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        MorphAttackAnimations.ANIMATIONS.register(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MorphMessages.registerMessages(CHANNEL);
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}

