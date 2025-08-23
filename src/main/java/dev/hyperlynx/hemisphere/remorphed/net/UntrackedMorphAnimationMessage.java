package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.client.ClientMorphFunctions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

// TO CLIENT
public record UntrackedMorphAnimationMessage(UUID player_id, ResourceLocation anim_id, boolean start) {
    public void encoder(FriendlyByteBuf buf) {
        buf.writeUUID(player_id);
        buf.writeResourceLocation(anim_id);
        buf.writeBoolean(start);
    }

    public static UntrackedMorphAnimationMessage decoder(FriendlyByteBuf buf) {
        return new UntrackedMorphAnimationMessage(buf.readUUID(), buf.readResourceLocation(), buf.readBoolean());
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if(start) {
                ClientMorphFunctions.runAnimation(player_id, anim_id);
            } else {
                ClientMorphFunctions.resetAnimation(player_id, anim_id);
            }
        });
        context.get().setPacketHandled(true);
    }


}
