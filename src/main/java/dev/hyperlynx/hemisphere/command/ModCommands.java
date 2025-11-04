package dev.hyperlynx.hemisphere.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dev.hyperlynx.hemisphere.Hemisphere;
import dev.hyperlynx.hemisphere.util.Integration;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Hemisphere.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommands {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENTS = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Hemisphere.MODID);

    public static final RegistryObject<ArgumentTypeInfo<SkinNameArgumentType, SkinNameArgumentInfo.Template>> SKIN_NAME_ARGUMENT =
            COMMAND_ARGUMENTS.register("skin_name", SkinNameArgumentInfo::new);

    private static final SimpleCommandExceptionType ERROR_INVALID_SKIN = new SimpleCommandExceptionType(Component.translatable("commands.hemisphere.invalid_skin"));

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        ArgumentTypeInfos.registerByClass(SkinNameArgumentType.class, SKIN_NAME_ARGUMENT.get());
        if(RoleSkins.SKINS.isEmpty()) {
            return;
        }
        LiteralArgumentBuilder<CommandSourceStack> command_builder = Commands.literal("role_skin")
                .then(Commands.literal("apply")
                        .then(Commands.argument("skin_name", SkinNameArgumentType.name())
                                .executes(context ->
                                        applySkin(context.getArgument("skin_name", String.class), context.getSource().getPlayer()))
                        .then(Commands.argument("target", EntityArgument.player())
                        .executes(context ->
                                applySkin(context.getArgument("skin_name", String.class), EntityArgument.getPlayer(context, "target"))
                ))))
                .then(Commands.literal("remove")
                        .executes(context -> removeSkin(context.getSource().getPlayer()))
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(context -> removeSkin(EntityArgument.getPlayer(context, "target")))
                ));
        event.getDispatcher().register(command_builder);
    }

    private static int removeSkin(ServerPlayerEntity target) {
        Integration.morph().resetShape(target);
        Integration.reskin().resetSkin(target);
        return 1;
    }

    public static int applySkin(String skin_name, ServerPlayerEntity player) throws CommandSyntaxException {
        if(!RoleSkins.SKINS.containsKey(skin_name)) {
            throw ERROR_INVALID_SKIN.create();
        }
        RoleSkins.SKINS.get(skin_name).apply(player);
        return 1;
    }

}
