package dev.hyperlynx.hemisphere.remorphed.net;

import dev.hyperlynx.hemisphere.remorphed.MorphAttackAnimationType;
import dev.hyperlynx.hemisphere.remorphed.client.ClientMorphFunctions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

// TO CLIENT
public record MorphAttackMessage(UUID player_id, boolean force, ResourceLocation anim_id) {
    public void encoder(FriendlyByteBuf buf) {
        buf.writeUUID(player_id);
        buf.writeBoolean(force);
        buf.writeResourceLocation(anim_id);
    }

    public static MorphAttackMessage decoder(FriendlyByteBuf buf) {
        return new MorphAttackMessage(buf.readUUID(), buf.readBoolean(), buf.readResourceLocation());
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ClientMorphFunctions.animateAttack(player_id, force, anim_id);
        });
        context.get().setPacketHandled(true);
    }


}
