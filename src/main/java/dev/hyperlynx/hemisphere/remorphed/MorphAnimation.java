package dev.hyperlynx.hemisphere.remorphed;

import java.util.function.Consumer;

/***
 * Represents a single kind of animation that can be triggered while morphed into an entity.
 * Should be registered to "hemisphere:morph_animations".
 */
public class MorphAnimation<T extends MorphAnimating> {
    public int duration;
    public Consumer<T> run_function;
    public Consumer<T> reset_function;
}
