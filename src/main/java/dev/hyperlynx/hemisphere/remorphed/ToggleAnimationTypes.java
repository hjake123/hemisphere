package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import net.minecraft.entity.Entity;
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
        registerType("shift", SHIFT);
        registerType("swimming", SWIMMING);
        registerType("flying", FLYING);
        registerType("spring", SPRINT);
    }

    public static final ToggleAnimationType SHIFT = new ToggleAnimationType(PlayerEntity::isCrouching);
    public static final ToggleAnimationType SWIMMING = new ToggleAnimationType(PlayerEntity::isSwimming);
    public static final ToggleAnimationType FLYING = new ToggleAnimationType(player -> player.abilities.isFlying);
    public static final ToggleAnimationType SPRINT = new ToggleAnimationType(Entity::isSprinting);
}
