package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ToggleAnimationTypes {
    public static final DeferredRegister<ToggleAnimationType> TYPES = DeferredRegister.create(Hemisphere.location("toggle_animation_types"), Hemisphere.MODID);
    public static final Supplier<IForgeRegistry<ToggleAnimationType>> REGISTRY = TYPES.makeRegistry(RegistryBuilder::new);

    public static DeferredRegister<ToggleAnimationType> makeDeferredRegister(String mod_id) {
        return DeferredRegister.create(Hemisphere.location("toggle_animation_types"), mod_id);
    }

    public static void tick(Player player) {
        for(ResourceLocation toggle_type_id : ToggleAnimationTypes.REGISTRY.get().getKeys()) {
            ToggleAnimationType toggle_type = ToggleAnimationTypes.REGISTRY.get().getValue(toggle_type_id);
            if(toggle_type == null) {
                Hemisphere.LOGGER.error("Unregistered toggle type {}", toggle_type_id);
                return;
            }
            toggle_type.tick(player);
        }
    }

    public static final RegistryObject<ToggleAnimationType> SHIFT = TYPES.register("shift", () ->
            new ToggleAnimationType(Player::isShiftKeyDown));

    public static final RegistryObject<ToggleAnimationType> SWIMMING = TYPES.register("swimming", () ->
            new ToggleAnimationType(Player::isSwimming));

    public static final RegistryObject<ToggleAnimationType> FLYING = TYPES.register("flying", () ->
            new ToggleAnimationType(player -> player.getAbilities().flying));
}
