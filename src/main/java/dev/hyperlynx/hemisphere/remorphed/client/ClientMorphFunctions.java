package dev.hyperlynx.hemisphere.remorphed.client;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.remorphed.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import tocraft.walkers.api.PlayerShape;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ClientMorphFunctions {
    public static void animateAttack(UUID player_id_attacking, ResourceLocation animation_id) {
        updateAnimation(player_id_attacking, animation_id, (animating, animation) -> {
            animating.startAnimation(animation);
            ClientMorphAttackAnimationController.addRunningAttack(animating, animation); // Attacks are tracked so they can disable
        });
    }

    public static void runAnimation(UUID player_id, ResourceLocation anim_id) {
        updateAnimation(player_id, anim_id, MorphAnimating::startAnimation);
    }

    public static void resetAnimation(UUID player_id, ResourceLocation anim_id) {
        updateAnimation(player_id, anim_id, MorphAnimating::resetAnimation);
    }

    private static void updateAnimation(UUID player_id, ResourceLocation anim_id, BiConsumer<MorphAnimating, MorphAnimation<?>> update_function) {
        assert Minecraft.getInstance().level != null;
        Player player = Minecraft.getInstance().level.getPlayerByUUID(player_id);
        if(player == null) {
            return;
        }
        LivingEntity identity = PlayerShape.getCurrentShape(player);
        if(identity instanceof MorphAnimating animating) {
            var animation = MorphAnimations.REGISTRY.get().getValue(anim_id);
            if(animation == null) {
                Hemisphere.LOGGER.error("Invalid morph animation anim_id {}", anim_id);
                return;
            }
            update_function.accept(animating, animation);
        }
    }
}
