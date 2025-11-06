package dev.hyperlynx.hemisphere.remorphed;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import noppes.mpm.ModelData;
import noppes.mpm.packets.Packets;
import noppes.mpm.packets.client.PacketPlayerDataSend;

public class MPMWrapperImpl implements MorphModWrapper{
    @Override
    public void setShape(ServerPlayerEntity player, EntityType<? extends LivingEntity> shape) {
        ModelData data = ModelData.get(player);
        data.setEntity(shape.getRegistryName());
        data.save();
        Packets.sendNearby(player, new PacketPlayerDataSend(player.getUniqueID(), data.writeToNBT()));
    }

    @Override
    public void resetShape(ServerPlayerEntity player) {
        ModelData data = ModelData.get(player);
        data.setEntity(null);
        data.save();
        Packets.sendNearby(player, new PacketPlayerDataSend(player.getUniqueID(), data.writeToNBT()));    }

    @Override
    public LivingEntity getShape(PlayerEntity player) {
        return ModelData.get(player).getEntity(player);
    }
}
