package dev.hyperlynx.hemisphere.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class SkinNameArgument implements ArgumentType<String> {

    public SkinNameArgument() {}

    public static SkinNameArgument name() {
        return new SkinNameArgument();
    }

    @Override
    public String parse(StringReader stringReader) throws CommandSyntaxException {
        return stringReader.readString();
    }

    public static class Serializer implements IArgumentSerializer<SkinNameArgument> {
        @Override
        public void write(SkinNameArgument argument, PacketBuffer buffer) {
            // NO-OP
        }

        @Override
        public SkinNameArgument read(PacketBuffer buffer) {
            return new SkinNameArgument();
        }

        @Override
        public void write(SkinNameArgument argument, JsonObject json) {
            // NO-OP
        }
    }
}
