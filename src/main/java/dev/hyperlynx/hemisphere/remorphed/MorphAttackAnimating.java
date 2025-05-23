package dev.hyperlynx.hemisphere.remorphed;

public interface MorphAttackAnimating {
    void startAttackAnimation(MorphAttackAnimation anim);
    void resetAttackAnimation();
    default boolean doesLeftClickAttack() {
        return true;
    }
}
