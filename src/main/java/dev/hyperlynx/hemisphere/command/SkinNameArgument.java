package dev.hyperlynx.hemisphere.command;

import com.google.common.collect.Iterables;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (!(context.getSource() instanceof ISuggestionProvider)) {
            return Suggestions.empty();
        } else {
            List<String> suggested_names = new ArrayList<>(RoleSkins.SKINS.keySet());
            return ISuggestionProvider.suggest(suggested_names, builder);
        }
    }
}
