package dev.hyperlynx.hemisphere;

import dev.hyperlynx.hemisphere.remorphed.MorphAnimations;
import dev.hyperlynx.hemisphere.remorphed.ToggleAnimationTypes;
import dev.hyperlynx.hemisphere.remorphed.net.MorphMessages;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


@Mod(Hemisphere.MODID)
public class Hemisphere {
    public static final String MODID = "hemisphere";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            location("simple_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public Hemisphere() {
        try {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
            bus.addListener(this::commonSetup);
            ToggleAnimationTypes.TYPES.register(bus);
        } catch (Exception e) {
            System.err.println("There was a problem!! : " + e.getMessage());
            throw e;
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if(ModList.get().isLoaded("morph")) {
            MorphMessages.registerMessages(CHANNEL);
        }
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MODID, path);
    }
}

