package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class ToggleAnimationType {
    private final Map<UUID, Boolean> toggle_states = new HashMap<>();
    private final Map<EntityType<?>, ResourceLocation> animation_by_shape = new HashMap<>();
    private final Function<Player, Boolean> check_function;

    public ToggleAnimationType(Function<Player, Boolean> checkFunction) {
        check_function = checkFunction;
    }

    public void registerAnimation(EntityType<?> type, ResourceLocation anim_id) {
        animation_by_shape.put(type, anim_id);
    }

    public void tick(Player player) {
        MorphAnimationController.updateToggleState(toggle_states, animation_by_shape::get, player, check_function.apply(player));
    }
}
