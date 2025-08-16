package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import tocraft.walkers.api.PlayerShape;

public class WalkersWrapperImpl implements WalkersWrapper {
    public void setShape(ServerPlayer player, EntityType<? extends LivingEntity> shape) {
        PlayerShape.updateShapes(player, shape.create(player.level()));
    }

    public void resetShape(ServerPlayer player) {
        PlayerShape.updateShapes(player, null);
    }
}
