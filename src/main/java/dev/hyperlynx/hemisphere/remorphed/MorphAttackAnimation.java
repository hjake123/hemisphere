package dev.hyperlynx.hemisphere.remorphed;

import java.util.function.Consumer;

/***
 * Represents a single kind of animation that can be triggered while morphed into an entity.
 * Should be registered to "hemisphere:morph_animations".
 */
public record MorphAttackAnimation<T extends MorphAttackAnimating>(
        int duration,
        Consumer<T> run_function,
        Consumer<T> reset_function
) {}
