package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.remorphed.net.MorphAttackMessage;
import dev.hyperlynx.hemisphere.remorphed.net.UntrackedMorphAnimationMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import tocraft.walkers.api.PlayerShape;

import java.util.*;

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

    private static final Map<UUID, Boolean> WERE_HOLDING_SHIFT = new HashMap<>();
    public static void updateShiftDown(Player player, boolean holding_shift_key) {
        LivingEntity identity = PlayerShape.getCurrentShape(player);
        if(identity == null) {
            return;
        }
        if(!WERE_HOLDING_SHIFT.containsKey(player.getUUID()) || WERE_HOLDING_SHIFT.get(player.getUUID()) != holding_shift_key) {
            WERE_HOLDING_SHIFT.put(player.getUUID(), holding_shift_key);
            ResourceLocation shift_animation_id = MorphAnimations.SHIFT_ANIMATION_BY_SHAPE.get(identity.getType());
            if(shift_animation_id == null) {
                return;
            }
            if(holding_shift_key) {
                Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(),
                        new UntrackedMorphAnimationMessage(player.getUUID(), shift_animation_id, true));
            } else {
                Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(),
                        new UntrackedMorphAnimationMessage(player.getUUID(), shift_animation_id, false));
            }
        }
    }

}
