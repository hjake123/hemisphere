package dev.hyperlynx.hemisphere.command;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class RoleSkins {
    public static final Map<String, SkinForm> SKINS = new HashMap<>();

    public static void registerSkin(String name, String url) {
        SKINS.put(name, new SkinForm(url));
    }

    public static void registerSkin(String name, EntityType<? extends LivingEntity> type) {
        SKINS.put(name, new SkinForm(type));
    }
}
