package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.util.Integration;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MorphAttackEffects {
    protected static final Map<EntityType<?>, Effect> PUNCH_EFFECT_BY_SHAPE = new HashMap<>();
    public static void registerPunchEffect(EntityType<?> entity, BiConsumer<Player, LivingEntity> effect, int delay) {
        PUNCH_EFFECT_BY_SHAPE.put(entity, new Effect(effect, delay));
    }

    protected static final Map<EntityType<?>, Effect> INTERACT_EFFECT_BY_SHAPE = new HashMap<>();
    public static void registerUseEffect(EntityType<?> entity, BiConsumer<Player, LivingEntity> effect, int delay) {
        INTERACT_EFFECT_BY_SHAPE.put(entity, new Effect(effect, delay));
    }

    protected static final Map<String, Effect> CUSTOM_EFFECTS = new HashMap<>();
    public static void registerCustomEffect(String unique_id, BiConsumer<Player, LivingEntity> effect, int delay) {
        CUSTOM_EFFECTS.put(unique_id, new Effect(effect, delay));
    }

    private static final Map<Delayed, Integer> DELAY_TIMERS = new HashMap<>();

    public static void runPunchEffect(Player attacker, LivingEntity identity) {
        var effect = PUNCH_EFFECT_BY_SHAPE.get(identity.getType());
        if(effect == null) {
            return;
        }
        DELAY_TIMERS.put(new Delayed(effect.function, attacker, identity), effect.delay());
    }

    public static void runInteractEffect(Player attacker, LivingEntity identity) {
        var effect = INTERACT_EFFECT_BY_SHAPE.get(identity.getType());
        if(effect == null) {
            return;
        }
        DELAY_TIMERS.put(new Delayed(effect.function, attacker, identity), effect.delay());
    }

    public static void runCustomEffect(String unique_id, Player attacker) {
        var effect = CUSTOM_EFFECTS.get(unique_id);
        if(effect == null) {
            return;
        }
        LivingEntity identity = Integration.morph().getShape(attacker);
        if(identity == null) {
            return;
        }
        DELAY_TIMERS.put(new Delayed(effect.function, attacker, identity), effect.delay());
    }

    public static void tick() {
        List<Delayed> finished = new ArrayList<>();
        for(Delayed delayed : DELAY_TIMERS.keySet()) {
            int timer = DELAY_TIMERS.get(delayed);
            if(timer < 1) {
                delayed.function.accept(delayed.player, delayed.identity);
                finished.add(delayed);
            }
            DELAY_TIMERS.put(delayed, timer - 1);
        }
        for(Delayed key : finished) {
            DELAY_TIMERS.remove(key);
        }
    }

    protected record Effect(BiConsumer<Player, LivingEntity> function, int delay){}
    private record Delayed(BiConsumer<Player, LivingEntity> function, Player player, LivingEntity identity){}
}
