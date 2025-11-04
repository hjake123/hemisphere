package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.client.ClientMorphFunctions;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

// TO CLIENT
public class MorphAttackMessage {
    final UUID player_id;
    final ResourceLocation anim_id;

    public MorphAttackMessage(UUID playerId, ResourceLocation animId) {
        player_id = playerId;
        anim_id = animId;
    }

    public void encoder(PacketBuffer buf) {
        buf.writeUniqueId(player_id);
        buf.writeResourceLocation(anim_id);
    }

    public static MorphAttackMessage decoder(PacketBuffer buf) {
        return new MorphAttackMessage(buf.readUniqueId(), buf.readResourceLocation());
    }

    public void handler(Supplier< NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientMorphFunctions.animateAttack(player_id, anim_id));
        context.get().setPacketHandled(true);
    }


}
