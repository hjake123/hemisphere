package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.client.ClientMorphFunctions;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.UUID;
import java.util.function.Supplier;

// TO CLIENT
public class UntrackedMorphAnimationMessage{
    final UUID player_id;
    final ResourceLocation anim_id;
    final boolean start;

    public UntrackedMorphAnimationMessage(UUID playerId, ResourceLocation animId, boolean start) {
        player_id = playerId;
        anim_id = animId;
        this.start = start;
    }

    public void encoder(PacketBuffer buf) {
        buf.writeUniqueId(player_id);
        buf.writeResourceLocation(anim_id);
        buf.writeBoolean(start);
    }

    public static UntrackedMorphAnimationMessage decoder(PacketBuffer buf) {
        return new UntrackedMorphAnimationMessage(buf.readUniqueId(), buf.readResourceLocation(), buf.readBoolean());
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
