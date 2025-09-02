package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface MorphModWrapper {
    void setShape(ServerPlayer player, EntityType<? extends LivingEntity> shape);
    void resetShape(ServerPlayer player);

    LivingEntity getShape(Player player);

    class Stub implements MorphModWrapper {
        @Override
        public void setShape(ServerPlayer player, EntityType<? extends LivingEntity> shape) {
            // NOOP
        }

        @Override
        public void resetShape(ServerPlayer player) {
            // NOOP
        }

        @Override
        public LivingEntity getShape(Player player) {
            return player;
        }
    }
}
