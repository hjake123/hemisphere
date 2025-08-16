package dev.hyperlynx.hemisphere.reskin;

import net.minecraft.world.entity.player.Player;

public interface ReSkinWrapper {
    void resetSkin(Player player);
    public void setSkin(Player player, String url);

    class Stub implements ReSkinWrapper {
        public void resetSkin(Player player) {
            // NOOP
        }

        public void setSkin(Player player, String url) {
            // NOOP
        }
    }
}
