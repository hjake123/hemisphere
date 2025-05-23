package dev.hyperlynx.hemisphere.keybind;

import dev.hyperlynx.hemisphere.Hemisphere;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Hemisphere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public record KeyBinding(Supplier<KeyMapping> mapping, Runnable handler) {
    public static final List<KeyBinding> BINDINGS = new ArrayList<>();

    public KeyBinding add(){
        BINDINGS.add(this);
        return this;
    }

    @SubscribeEvent
    public static void registerKeyMaps(RegisterKeyMappingsEvent event) {
        for(KeyBinding binding : BINDINGS) {
            event.register(binding.mapping().get());
        }
    }
}
