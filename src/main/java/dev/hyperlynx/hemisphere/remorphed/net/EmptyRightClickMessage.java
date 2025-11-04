package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.MorphAnimationController;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

// TO SERVER
public class EmptyRightClickMessage {
    final UUID player_id;

    public EmptyRightClickMessage(UUID playerId) {
        player_id = playerId;
    }

    public void encoder(PacketBuffer buf) {
        buf.writeUniqueId(player_id);
    }

    public static EmptyRightClickMessage decoder(PacketBuffer buf) {
        return new EmptyRightClickMessage(buf.readUniqueId());
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> MorphAnimationController.handleRightClick(context.get().getSender().level, context.get().getSender()));
        context.get().setPacketHandled(true);
    }


}
