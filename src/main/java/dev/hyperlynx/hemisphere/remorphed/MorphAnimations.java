package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MorphAnimations {
    public static final DeferredRegister<MorphAnimation<?>> ANIMATIONS = DeferredRegister.create(Hemisphere.location("morph_animations"), Hemisphere.MODID);
    public static final Supplier<IForgeRegistry<MorphAnimation<?>>> REGISTRY = ANIMATIONS.makeRegistry(RegistryBuilder::new);

    public static DeferredRegister<MorphAnimation<?>> makeDeferredRegister(String mod_id) {
        return DeferredRegister.create(Hemisphere.location("morph_animations"), mod_id);
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
        ToggleAnimationTypes.SHIFT.get().registerAnimation(entity, anim_id);
    }

    public static void registerSwimAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        ToggleAnimationTypes.SWIMMING.get().registerAnimation(entity, anim_id);
    }

    public static void registerFlyingAnimation(EntityType<?> entity, ResourceLocation anim_id) {
        ToggleAnimationTypes.FLYING.get().registerAnimation(entity, anim_id);
    }
}
