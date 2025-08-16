package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public interface WalkersWrapper {
    void setShape(ServerPlayer player, EntityType<? extends LivingEntity> shape);
    void resetShape(ServerPlayer player);

    class Stub implements WalkersWrapper {
        @Override
        public void setShape(ServerPlayer player, EntityType<? extends LivingEntity> shape) {
            // NOOP
        }

        @Override
        public void resetShape(ServerPlayer player) {
            // NOOP
        }
    }
}
