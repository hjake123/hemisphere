package dev.hyperlynx.hemisphere.command;

import com.google.gson.JsonObject;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class SkinNameArgumentInfo implements ArgumentTypeInfo<SkinNameArgumentType, SkinNameArgumentInfo.Template> {
    @Override
    public void serializeToNetwork(Template template, FriendlyByteBuf buffer) {
        // No need.
    }

    @Override
    public Template deserializeFromNetwork(FriendlyByteBuf buffer) {
        return new Template();
    }

    @Override
    public void serializeToJson(Template template, JsonObject json) {
        // No need.
    }

    @Override
    public Template unpack(SkinNameArgumentType power_argument) {
        return new Template();
    }

    public final class Template implements ArgumentTypeInfo.Template<SkinNameArgumentType> {
        @Override
        public SkinNameArgumentType instantiate(CommandBuildContext context) {
            return new SkinNameArgumentType();
        }

        @Override
        public ArgumentTypeInfo<SkinNameArgumentType, ?> type() {
            return ModCommands.SKIN_NAME_ARGUMENT.get();
        }
    }
}
