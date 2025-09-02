package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.remorphed.net.MorphAttackMessage;
import dev.hyperlynx.hemisphere.remorphed.net.UntrackedMorphAnimationMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import tocraft.walkers.api.PlayerShape;

import java.util.*;
import java.util.function.Function;

public class MorphAnimationController {
    public static void playAttackAnim(UUID player_id, ResourceLocation animation_id) {
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(player_id, animation_id));
    }

    public static void handleLeftClick(Level level, Player attacker) {
        if(level.isClientSide()) {
            return;
        }
        LivingEntity identity = PlayerShape.getCurrentShape(attacker);
        if(identity == null) {
            return;
        }
        MorphAttackEffects.runPunchEffect(attacker, identity);
        ResourceLocation punch_anim_id = MorphAnimations.PUNCH_ANIMATION_BY_SHAPE.get(identity.getType());
        if(punch_anim_id == null) {
            return;
        }
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(attacker.getUUID(), punch_anim_id));
    }

    public static void handleRightClick(Level level, Player attacker) {
        if(level.isClientSide()) {
            return;
        }
        LivingEntity identity = PlayerShape.getCurrentShape(attacker);
        if(identity == null) {
            return;
        }
        MorphAttackEffects.runInteractEffect(attacker, identity);
        ResourceLocation interact_anim_id = MorphAnimations.INTERACT_ANIMATION_BY_SHAPE.get(identity.getType());
        if(interact_anim_id == null) {
            return;
        }
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(attacker.getUUID(), interact_anim_id));
    }

    public static void handleJump(ServerPlayer player) {
        LivingEntity identity = PlayerShape.getCurrentShape(player);
        if(identity == null) {
            return;
        }
        ResourceLocation jump_anim_id = MorphAnimations.JUMP_ANIMATION_BY_SHAPE.get(identity.getType());
        if(jump_anim_id == null) {
            return;
        }
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(player.getUUID(), jump_anim_id));
    }

    private static void updateToggleState(Map<UUID, Boolean> state_map, Function<EntityType<?>, ResourceLocation> transition_lookup, Player player, boolean should_run) {
        LivingEntity identity = PlayerShape.getCurrentShape(player);
        if(identity == null) {
            return;
        }
        if(!state_map.containsKey(player.getUUID()) || state_map.get(player.getUUID()) != should_run) {
            state_map.put(player.getUUID(), should_run);
            ResourceLocation shift_animation_id = transition_lookup.apply(identity.getType());
            if(shift_animation_id == null) {
                return;
            }
            if(should_run) {
                Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(),
                        new UntrackedMorphAnimationMessage(player.getUUID(), shift_animation_id, true));
            } else {
                Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(),
                        new UntrackedMorphAnimationMessage(player.getUUID(), shift_animation_id, false));
            }
        }
    }

    private static final Map<UUID, Boolean> WERE_HOLDING_SHIFT = new HashMap<>();
    public static void updateShiftDown(Player player, boolean holding_shift_key) {
        updateToggleState(WERE_HOLDING_SHIFT, MorphAnimations.SHIFT_ANIMATION_BY_SHAPE::get, player, holding_shift_key);
    }

    private static final Map<UUID, Boolean> WERE_SWIMMING = new HashMap<>();
    public static void updateSwimming(Player player, boolean swimming) {
        updateToggleState(WERE_SWIMMING, MorphAnimations.SWIM_ANIMATION_BY_SHAPE::get, player, swimming);
    }
}
