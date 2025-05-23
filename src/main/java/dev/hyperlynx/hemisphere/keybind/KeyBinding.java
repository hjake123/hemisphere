package dev.hyperlynx.hemisphere.keybind;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record KeyBinding(Supplier<KeyMapping> mapping, Runnable handler) {
    public static final Map<String, List<KeyBinding>> BINDINGS = new HashMap<>();

    public KeyBinding add(String mod_id){
        if(!BINDINGS.containsKey(mod_id)) {
            BINDINGS.put(mod_id, new ArrayList<>());
        }
        BINDINGS.get(mod_id).add(this);
        return this;
    }

    public static void registerKeyMappings(RegisterKeyMappingsEvent event, String mod_id) {
        for(KeyBinding binding : BINDINGS.get(mod_id)) {
            event.register(binding.mapping().get());
        }
    }
}
