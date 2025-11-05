package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

public class MorphAnimations {
    private static final Map<ResourceLocation, MorphAnimation<?>> ANIMATIONS = new HashMap<>();

    public static void registerAnimation(ResourceLocation id, MorphAnimation<?> anim) {
        ANIMATIONS.put(id, anim);
    }

    public static MorphAnimation<?> get(ResourceLocation id) {
        return ANIMATIONS.get(id);
    }

    protected static final Map<EntityType<?>, ResourceLocation> PUNCH_ANIMATION_BY_SHAPE = new HashMap<>();
    public static void registerPunchAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        PUNCH_ANIMATION_BY_SHAPE.put(entity, anim_id);
    }

    protected static final Map<EntityType<?>, ResourceLocation> INTERACT_ANIMATION_BY_SHAPE = new HashMap<>();
    public static void registerUseAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        INTERACT_ANIMATION_BY_SHAPE.put(entity, anim_id);
    }

    protected static final Map<EntityType<?>, ResourceLocation> JUMP_ANIMATION_BY_SHAPE = new HashMap<>();
    public static void registerJumpAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        JUMP_ANIMATION_BY_SHAPE.put(entity, anim_id);
    }

    public static void registerCrouchAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        ToggleAnimationTypes.SHIFT.registerAnimation(entity, anim_id);
    }

    public static void registerSwimAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        ToggleAnimationTypes.SWIMMING.registerAnimation(entity, anim_id);
    }

    public static void registerFlyingAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        ToggleAnimationTypes.FLYING.registerAnimation(entity, anim_id);
    }

    public static void registerSprintAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        ToggleAnimationTypes.SPRINT.registerAnimation(entity, anim_id);
    }
}
