package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.world.entity.EntityType;

public interface MorphAttackAnimating {
    default void startAttackAnimation(MorphAttackAnimation anim) {
        anim.run_function().accept(this);
    }
    void resetAttackAnimation();
    default boolean doesLeftClickAttack() {
        return true;
    }
}
