package dev.hyperlynx.hemisphere.remorphed.client;

import dev.hyperlynx.hemisphere.remorphed.MorphAnimating;
import dev.hyperlynx.hemisphere.remorphed.MorphAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientMorphAttackAnimationController {
    public static final Map<MorphAnimating, Timer> ATTACK_TIMERS = new HashMap<>();

    public static void addRunningAttack(MorphAnimating animating, MorphAnimation<?> anim) {
        if (!ATTACK_TIMERS.containsKey(animating)) {
            ATTACK_TIMERS.put(animating, new Timer(anim.duration(), anim));
        }
    }

    public static void tick() {
        decrement();
    }

    private static void decrement() {
        List<MorphAnimating> finished = new ArrayList<>();
        for (MorphAnimating animating : ATTACK_TIMERS.keySet()) {
            Timer timer = ATTACK_TIMERS.get(animating);
            int remaining_time = timer.time();
            if (remaining_time <= 0) {
                finished.add(animating);
            }
            ATTACK_TIMERS.put(animating, new Timer(remaining_time - 1, timer.anim()));
        }
        for (MorphAnimating animating : finished) {
            var timer = ATTACK_TIMERS.remove(animating);
            animating.resetAnimation(timer.anim());
        }
    }

    private record Timer(int time, MorphAnimation<?> anim) {
    }
}
