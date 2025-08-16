package dev.hyperlynx.hemisphere.command;

import dev.hyperlynx.hemisphere.util.Integration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class SkinForm {
    private final @Nullable String url;
    private final @Nullable EntityType<? extends LivingEntity> morph;

    public SkinForm(String url) {
        this.url = url;
        this.morph = null;
    }

    public SkinForm(EntityType<? extends LivingEntity> morph) {
        url = null;
        this.morph = morph;
    }

    public void apply(ServerPlayer player) {
        if(morph != null) {
            Integration.walkers().setShape(player, morph);
            Integration.reskin().resetSkin(player);
        } else if(url != null) {
            Integration.reskin().setSkin(player, url);
            Integration.walkers().resetShape(player);
        }
    }
}
