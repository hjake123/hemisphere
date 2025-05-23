package dev.hyperlynx.hemisphere.remorphed.client;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.remorphed.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import tocraft.walkers.api.PlayerShape;

import java.util.UUID;

public class ClientMorphFunctions {
    public static void animateAttack(UUID player_id_attacking, ResourceLocation id) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(player_id_attacking);
        LivingEntity identity = PlayerShape.getCurrentShape(player);
        if(identity instanceof MorphAttackAnimating animating) {
            var animation = MorphAttackAnimations.REGISTRY.get().getValue(id);
            if(animation == null) {
                Hemisphere.LOGGER.error("Invalid morph animation anim_id {}", id);
                return;
            }
            animating.startAttackAnimation(animation);
            MorphAttackAnimationController.addRunningAttack(animating, animation);
        }
    }
}
