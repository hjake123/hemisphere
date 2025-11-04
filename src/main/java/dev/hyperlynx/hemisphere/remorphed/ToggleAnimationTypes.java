package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ToggleAnimationTypes {
    private static final Map<ResourceLocation, ToggleAnimationType> TYPES = new HashMap<>();

    public static void tick(PlayerEntity player) {
        for(ResourceLocation toggle_type_id : TYPES.keySet()) {
            ToggleAnimationType toggle_type = TYPES.get(toggle_type_id);
            if(toggle_type == null) {
                System.out.println("[ERROR] [Hemisphere] Unregistered toggle type " + toggle_type_id);
                return;
            }
            toggle_type.tick(player);
        }
    }

    public static void registerType(ResourceLocation id, ToggleAnimationType type) {
        TYPES.put(id, type);
    }

    private static void registerType(String path, ToggleAnimationType type) {
        registerType(Hemisphere.location(path), type);
    }

    public static void init() {
        registerType("shift", new ToggleAnimationType(PlayerEntity::isCrouching));
        registerType("swimming", new ToggleAnimationType(PlayerEntity::isVisuallySwimming));
        registerType("flying", new ToggleAnimationType(player -> player.abilities.isFlying));
    }

    public static final Supplier<ToggleAnimationType> SHIFT = () -> TYPES.get(Hemisphere.location("shift"));
    public static final Supplier<ToggleAnimationType> SWIMMING = () -> TYPES.get(Hemisphere.location("swimming"));
    public static final Supplier<ToggleAnimationType> FLYING = () -> TYPES.get(Hemisphere.location("flying"));
}
