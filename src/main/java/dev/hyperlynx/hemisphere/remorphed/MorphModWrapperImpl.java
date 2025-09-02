package dev.hyperlynx.hemisphere.remorphed;

import draylar.identity.api.PlayerIdentity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MorphModWrapperImpl implements MorphModWrapper {
    public void setShape(ServerPlayer player, EntityType<? extends LivingEntity> shape) {
        PlayerIdentity.updateIdentity(player, null, shape.create(player.level));
    }

    public void resetShape(ServerPlayer player) {
        PlayerIdentity.updateIdentity(player, null, null);
    }

    @Override
    public LivingEntity getShape(Player player) {
        return PlayerIdentity.getIdentity(player);
    }
}
