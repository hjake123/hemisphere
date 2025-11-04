package dev.hyperlynx.hemisphere.remorphed;

import dev.hyperlynx.hemisphere.Hemisphere;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MorphAnimations {
    public static IForgeRegistry<MorphAnimation<?>> REGISTRY;

    public static DeferredRegister<MorphAnimation<?>> makeDeferredRegister(String mod_id) {
        //noinspection unchecked
        return DeferredRegister.create((Class<MorphAnimation<?>>)(Class<?>) MorphAnimation.class, mod_id);
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        // Super sketchy seeming cast that seems? to be fine?
        //noinspection unchecked
        REGISTRY = new RegistryBuilder<MorphAnimation<?>>().setType((Class<MorphAnimation<?>>)(Class<?>) MorphAnimation.class).setName(Hemisphere.location("morph_animations")).create();
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
