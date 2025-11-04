package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/***
 * Represents a single kind of animation that can be triggered while morphed into an entity.
 * Should be registered to "hemisphere:morph_animations".
 */
public class MorphAnimation<T extends MorphAnimating> extends ForgeRegistryEntry<MorphAnimation<?>> {
    private ResourceLocation location;
    public int duration;
    public Consumer<T> run_function;
    public Consumer<T> reset_function;
}
