package dev.hyperlynx.hemisphere.command;

import dev.hyperlynx.hemisphere.util.Integration;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;

public class SkinForm {
    private final @Nullable String url;
    private final @Nullable Boolean slim;
    private final @Nullable EntityType<? extends LivingEntity> morph;

    public SkinForm(String url, boolean slim) {
        this.url = url;
        this.slim = slim;
        this.morph = null;
    }

    public SkinForm(EntityType<? extends LivingEntity> morph) {
        url = null;
        slim = null;
        this.morph = morph;
    }

    public void apply(ServerPlayerEntity player) {
        if(morph != null) {
            Integration.morph().setShape(player, morph);
            Integration.reskin().resetSkin(player);
        } else if(url != null) {
            Integration.reskin().setSkin(player, url);
            Integration.reskin().setModel(player, Boolean.TRUE.equals(slim));
            Integration.morph().resetShape(player);
        }
    }
}
