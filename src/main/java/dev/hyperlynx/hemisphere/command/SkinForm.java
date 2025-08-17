package dev.hyperlynx.hemisphere.command;

import dev.hyperlynx.hemisphere.util.Integration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

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

    public void apply(ServerPlayer player) {
        if(morph != null) {
            Integration.walkers().setShape(player, morph);
            Integration.reskin().resetSkin(player);
        } else if(url != null) {
            Integration.reskin().setSkin(player, url);
            Integration.reskin().setModel(player, Boolean.TRUE.equals(slim));
            Integration.walkers().resetShape(player);
        }
    }
}
