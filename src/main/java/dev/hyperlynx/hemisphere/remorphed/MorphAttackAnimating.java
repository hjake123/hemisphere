package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.world.entity.EntityType;

@SuppressWarnings({"unchecked", "rawtypes"})
public interface MorphAttackAnimating {
    default void startAttackAnimation(MorphAttackAnimation anim) {
        anim.run_function().accept(this);
    }
    default void resetAttackAnimation(MorphAttackAnimation anim) {
        anim.reset_function().accept(this);
    }
}
