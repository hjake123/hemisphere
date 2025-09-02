package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.MorphAnimationController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

// TO SERVER
public record EmptyRightClickMessage(UUID player_id) {
    public void encoder(FriendlyByteBuf buf) {
        buf.writeUUID(player_id);
    }

    public static EmptyRightClickMessage decoder(FriendlyByteBuf buf) {
        return new EmptyRightClickMessage(buf.readUUID());
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> MorphAnimationController.handleRightClick(context.get().getSender().level, context.get().getSender()));
        context.get().setPacketHandled(true);
    }


}
