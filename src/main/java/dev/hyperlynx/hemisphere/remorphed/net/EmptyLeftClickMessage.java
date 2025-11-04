package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.MorphAnimationController;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

// TO SERVER
public class EmptyLeftClickMessage {
    final UUID player_id;

    public EmptyLeftClickMessage(UUID playerId) {
        player_id = playerId;
    }

    public void encoder(PacketBuffer buf) {
        buf.writeUniqueId(player_id);
    }

    public static EmptyLeftClickMessage decoder(PacketBuffer buf) {
        return new EmptyLeftClickMessage(buf.readUniqueId());
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> MorphAnimationController.handleLeftClick(context.get().getSender().world, context.get().getSender()));
        context.get().setPacketHandled(true);
    }


}
