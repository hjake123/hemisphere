package dev.hyperlynx.hemisphere.client;

import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.keybind.KeyBinding;
import dev.hyperlynx.hemisphere.remorphed.net.EmptyLeftClickMessage;
import dev.hyperlynx.hemisphere.remorphed.MorphAttackAnimationController;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientHandlers {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) { // Only call code once as the tick event is called twice every tick
            MorphAttackAnimationController.tick();
            for(KeyBinding binding : KeyBinding.BINDINGS) {
                while(binding.mapping().get().consumeClick()) {
                    binding.handler().run();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEmptyLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        Hemisphere.CHANNEL.sendToServer(new EmptyLeftClickMessage(event.getEntity().getUUID()));
    }
}
