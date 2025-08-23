package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MorphHandlers {

    @SubscribeEvent
    public static void onBlockLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        MorphAnimationController.handleLeftClick(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityHit(AttackEntityEvent event) {
        MorphAnimationController.handleLeftClick(event.getEntity().level(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if(event.getLevel().isClientSide()) {
            return;
        }
        MorphAnimationController.handleRightClick(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if(event.getLevel().isClientSide()) {
            return;
        }
        MorphAnimationController.handleRightClick(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRightClickWithItem(PlayerInteractEvent.RightClickItem event) {
        if(event.getLevel().isClientSide()) {
            return;
        }
        MorphAnimationController.handleRightClick(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        MorphAttackEffects.tick();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.player instanceof ServerPlayer) {
            MorphAnimationController.updateShiftDown(event.player, event.player.isShiftKeyDown());
        }
    }

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntity() instanceof ServerPlayer splayer) {
            MorphAnimationController.handleJump(splayer);
        }
    }
}
