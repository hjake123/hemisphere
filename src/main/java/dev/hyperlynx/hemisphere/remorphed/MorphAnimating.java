package dev.hyperlynx.hemisphere.remorphed;

@SuppressWarnings({"unchecked", "rawtypes"})
public interface MorphAnimating {
    default void startAnimation(MorphAnimation anim) {
        anim.run_function().accept(this);
    }
    default void resetAnimation(MorphAnimation anim) {
        anim.reset_function().accept(this);
    }
}
