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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToggleAnimationTypes {
    public static IForgeRegistry<ToggleAnimationType> REGISTRY;
    public static DeferredRegister<ToggleAnimationType> TYPES = DeferredRegister.create(ToggleAnimationType.class, Hemisphere.MODID);

    public static DeferredRegister<ToggleAnimationType> makeDeferredRegister(String mod_id) {
        return DeferredRegister.create(REGISTRY, mod_id);
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        REGISTRY = new RegistryBuilder<ToggleAnimationType>().setType(ToggleAnimationType.class).setName(Hemisphere.location("toggle_animation_types")).create();
    }

    public static void tick(PlayerEntity player) {
        for(ResourceLocation toggle_type_id : ToggleAnimationTypes.REGISTRY.getKeys()) {
            ToggleAnimationType toggle_type = ToggleAnimationTypes.REGISTRY.getValue(toggle_type_id);
            if(toggle_type == null) {
                System.out.println("[ERROR] [Hemisphere] Unregistered toggle type " + toggle_type_id);
                return;
            }
            toggle_type.tick(player);
        }
    }

    public static final RegistryObject<ToggleAnimationType> SHIFT = TYPES.register("shift", () ->
            new ToggleAnimationType(PlayerEntity::isCrouching));

    public static final RegistryObject<ToggleAnimationType> SWIMMING = TYPES.register("swimming", () ->
            new ToggleAnimationType(PlayerEntity::isVisuallySwimming));

    public static final RegistryObject<ToggleAnimationType> FLYING = TYPES.register("flying", () ->
            new ToggleAnimationType(player -> player.abilities.isFlying));
}
