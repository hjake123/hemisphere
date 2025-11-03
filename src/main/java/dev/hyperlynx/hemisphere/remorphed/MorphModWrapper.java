package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public interface MorphModWrapper {
    void setShape(ServerPlayerEntity player, EntityType<? extends LivingEntity> shape);
    void resetShape(ServerPlayerEntity player);

    LivingEntity getShape(PlayerEntity player);

    class Stub implements MorphModWrapper {
        @Override
        public void setShape(ServerPlayerEntity player, EntityType<? extends LivingEntity> shape) {
            // NOOP
        }

        @Override
        public void resetShape(ServerPlayerEntity player) {
            // NOOP
        }

        @Override
        public LivingEntity getShape(PlayerEntity player) {
            return player;
        }
    }
}
