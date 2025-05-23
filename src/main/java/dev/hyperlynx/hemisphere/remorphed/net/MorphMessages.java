package dev.hyperlynx.hemisphere.remorphed.net;

import net.minecraftforge.network.simple.SimpleChannel;

public class MorphMessages {
    public static void registerMessages(SimpleChannel channel) {
        int idx = 1;
        channel.registerMessage(
                idx++,
                MorphAttackMessage.class,
                MorphAttackMessage::encoder,
                MorphAttackMessage::decoder,
                MorphAttackMessage::handler
        );
        channel.registerMessage(
                idx++,
                EmptyLeftClickMessage.class,
                EmptyLeftClickMessage::encoder,
                EmptyLeftClickMessage::decoder,
                EmptyLeftClickMessage::handler
        );
    }
}
