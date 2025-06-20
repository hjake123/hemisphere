package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.remorphed.net.MorphAttackMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.RegistryObject;
import tocraft.walkers.api.PlayerShape;

import java.util.*;

public class MorphAttackAnimationController {
    private static final Map<MorphAttackAnimating, Timer> ATTACK_TIMERS = new HashMap<>();

    public static void addRunningAttack(MorphAttackAnimating animating, MorphAttackAnimation<?> anim) {
        if(!ATTACK_TIMERS.containsKey(animating)) {
            ATTACK_TIMERS.put(animating, new Timer(anim.duration(), anim));
        }
    }

    public static void tick() {
        decrement();
    }

    private static void decrement() {
        List<MorphAttackAnimating> finished = new ArrayList<>();
        for(MorphAttackAnimating animating : MorphAttackAnimationController.ATTACK_TIMERS.keySet()) {
            Timer timer = MorphAttackAnimationController.ATTACK_TIMERS.get(animating);
            int remaining_time = timer.time();
            if(remaining_time <= 0) {
                finished.add(animating);
            }
            MorphAttackAnimationController.ATTACK_TIMERS.put(animating, new Timer(remaining_time - 1, timer.anim()));
        }
        for(MorphAttackAnimating animating : finished) {
            var timer = MorphAttackAnimationController.ATTACK_TIMERS.remove(animating);
            animating.resetAttackAnimation(timer.anim());
        }
    }

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
        ResourceLocation punch_anim_id = MorphAttackAnimations.PUNCH_ANIMATION_BY_SHAPE.get(identity.getType());
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
        ResourceLocation interact_anim_id = MorphAttackAnimations.INTERACT_ANIMATION_BY_SHAPE.get(identity.getType());
        if(interact_anim_id == null) {
            return;
        }
        Hemisphere.CHANNEL.send(PacketDistributor.ALL.noArg(), new MorphAttackMessage(attacker.getUUID(), interact_anim_id));
    }

    private record Timer (int time, MorphAttackAnimation<?> anim) {}
}
