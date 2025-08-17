package dev.hyperlynx.hemisphere.reskin;

import com.sekwah.reskin.CustomSkinManager;
import net.minecraft.world.entity.player.Player;

public class ReSkinWrapperImpl implements ReSkinWrapper {
    public void resetSkin(Player player) {
        CustomSkinManager.resetSkin(player);
    }

    public void setSkin(Player player, String url) {
        CustomSkinManager.setSkin(player, url);
    }

    public void setModel(Player player, boolean slim) {
        CustomSkinManager.setModel(player, slim ? "slim" : "default");
    }
}
