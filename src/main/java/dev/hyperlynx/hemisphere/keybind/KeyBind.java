package dev.hyperlynx.hemisphere.keybind;

import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class KeyBind {
    public Supplier<KeyBinding> key;
    public Runnable handler;

    public static final Map<String, List<KeyBind>> BINDINGS = new HashMap<>();

    public KeyBind add(String mod_id){
        if(!BINDINGS.containsKey(mod_id)) {
            BINDINGS.put(mod_id, new ArrayList<>());
        }
        BINDINGS.get(mod_id).add(this);
        return this;
    }

    public static void registerKeyMappings(RegisterKeyMappingsEvent event, String mod_id) {
        for(KeyBind binding : BINDINGS.get(mod_id)) {
            event.register(binding.mapping().get());
        }
    }
}
