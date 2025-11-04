package dev.hyperlynx.hemisphere.remorphed;
import me.ichun.mods.morph.api.MorphApi;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;


public class MorphModWrapperImpl implements MorphModWrapper {
    public void setShape(ServerPlayerEntity player, EntityType<? extends LivingEntity> shape) {
        MorphApi.getApiImpl().morphTo(player, MorphApi.getApiImpl().createVariant(shape.create(player.getServerWorld())));
    }

    public void resetShape(ServerPlayerEntity player) {
        MorphApi.getApiImpl().demorph(player);
    }

    @Override
    public LivingEntity getShape(PlayerEntity player) {
        return MorphApi.getApiImpl().getActiveMorphEntity(player);
    }
}
