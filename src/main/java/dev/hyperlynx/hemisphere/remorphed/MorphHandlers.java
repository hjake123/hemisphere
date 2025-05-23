package dev.hyperlynx.hemisphere.remorphed;

import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MorphHandlers {

    @SubscribeEvent
    public static void onBlockLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        MorphAttackAnimationController.handleLeftClick(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityHit(AttackEntityEvent event) {
        MorphAttackAnimationController.handleLeftClick(event.getEntity().level(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.EntityInteract event) {
        if(event.getLevel().isClientSide()) {
            return;
        }
        MorphAttackAnimationController.handleRightClick(event.getLevel(), event.getEntity());
    }
}
