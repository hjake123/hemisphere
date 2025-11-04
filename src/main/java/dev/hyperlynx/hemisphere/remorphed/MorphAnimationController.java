package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.remorphed.net.MorphAttackMessage;
import dev.hyperlynx.hemisphere.remorphed.net.UntrackedMorphAnimationMessage;
import dev.hyperlynx.hemisphere.util.Integration;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.*;
import java.util.function.Function;

public class MorphAnimationController {
    public static void playAttackAnim(UUID player_id, ResourceLocation animation_id) {
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(player_id, animation_id));
    }

    public static void handleLeftClick(World level, PlayerEntity attacker) {
        if(level.isRemote) {
            return;
        }
        LivingEntity identity = Integration.morph().getShape(attacker);
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

    public static void handleRightClick(World level, PlayerEntity attacker) {
        if(level.isRemote) {
            return;
        }
        LivingEntity identity = Integration.morph().getShape(attacker);
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

    public static void updateToggleState(Map<UUID, Boolean> state_map, Function<EntityType<?>, ResourceLocation> transition_lookup, PlayerEntity player, boolean should_run) {
        LivingEntity identity = Integration.morph().getShape(player);
        if(identity == null) {
            return;
        }
        if(!state_map.containsKey(player.getUniqueID()) || state_map.get(player.getUniqueID()) != should_run) {
            state_map.put(player.getUniqueID(), should_run);
            ResourceLocation shift_animation_id = transition_lookup.apply(identity.getType());
            if(shift_animation_id == null) {
                return;
            }
            if(should_run) {
                Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(),
                        new UntrackedMorphAnimationMessage(player.getUniqueID(), shift_animation_id, true));
            } else {
                Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(),
                        new UntrackedMorphAnimationMessage(player.getUniqueID(), shift_animation_id, false));
            }
        }
    }

    public static void handleJump(ServerPlayerEntity player) {
        LivingEntity identity = Integration.morph().getShape(player);
        if(identity == null) {
            return;
        }
        ResourceLocation jump_anim_id = MorphAnimations.JUMP_ANIMATION_BY_SHAPE.get(identity.getType());
        if(jump_anim_id == null) {
            return;
        }
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(player.getUUID(), jump_anim_id));
    }
}
