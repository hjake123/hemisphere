package dev.hyperlynx.hemisphere.remorphed;

import java.util.function.Consumer;

/***
 * Represents a single kind of animation that can be triggered while morphed into an entity.
 * Should be registered to "hemisphere:morph_animations".
 * @param duration How many ticks until the animating gets the reset signal
 * @param is_punch If this is a punch-like attack
 * @param run_function
 * @param <T>
 */
public record MorphAttackAnimation<T extends MorphAttackAnimating>(int duration, boolean is_punch, Consumer<T> run_function) {}
