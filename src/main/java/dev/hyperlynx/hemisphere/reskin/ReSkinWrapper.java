package dev.hyperlynx.hemisphere.reskin;


import net.minecraft.entity.player.PlayerEntity;

public interface ReSkinWrapper {
    void resetSkin(PlayerEntity player);
    void setSkin(PlayerEntity player, String url);
    void setModel(PlayerEntity player, boolean slim);

    class Stub implements ReSkinWrapper {
        public void resetSkin(PlayerEntity player) {
            // NOOP
        }

        public void setSkin(PlayerEntity player, String url) {
            // NOOP
        }

        public void setModel(PlayerEntity player, boolean slim) {
            // NOOP
        }
    }
}
