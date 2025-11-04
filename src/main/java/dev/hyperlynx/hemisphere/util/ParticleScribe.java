package dev.hyperlynx.hemisphere.util;


import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.concurrent.ThreadLocalRandom;

public class ParticleScribe {
    public static void drawParticle(World level, IParticleData opt, double x, double y, double z) {
        if (level.isRemote) {
            level.addParticle(opt, x, y, z, 0, 0, 0);
        } else {
            ((ServerWorld) level).spawnParticle(opt, x, y, z, 1, 0, 0, 0, 0.0);
        }
    }

    public static void drawParticleBox(World level, IParticleData opt, AxisAlignedBB aabb, int frequency) {
        double x, y, z;
        for(int i = 0; i < frequency; i++){
            x = level.getRandom().nextDouble() * (aabb.maxX - aabb.minX) + aabb.minX;
            y = level.getRandom().nextDouble() * (aabb.maxY - aabb.minY) + aabb.minY;
            z = level.getRandom().nextDouble() * (aabb.maxZ - aabb.minZ) + aabb.minZ;
            drawParticle(level, opt, x, y, z);
        }
    }

    public static void drawParticleLine(World level, IParticleData opt, BlockPos a, BlockPos b, int frequency, double noise){
        drawParticleLine(level, opt, a.getX()+0.5, a.getY()+0.5,a.getZ()+0.5,
                b.getX()+0.5, b.getY()+0.5, b.getZ()+0.5, frequency, noise);
    }

    public static void drawParticleLine(World level, IParticleData opt, Vector3d a, Vector3d b, int frequency, double noise){
        drawParticleLine(level, opt, a.x, a.y,a.z, b.x, b.y, b.z, frequency, noise);
    }

    public static void drawParticleLine(World level, IParticleData opt, double x1, double y1, double z1, double x2, double y2, double z2, int frequency, double noise) {
        for (int i = 0; i < frequency; i++) {
            double u = level.getRandom().nextDouble();
            double x = (1 - u) * x1 + u * x2;
            double y = (1 - u) * y1 + u * y2;
            double z = (1 - u) * z1 + u * z2;

            x += (level.getRandom().nextFloat() - 0.5) * noise;
            y += (level.getRandom().nextFloat() - 0.5) * noise;
            z += (level.getRandom().nextFloat() - 0.5) * noise;

            drawParticle(level, opt, x, y, z);
        }
    }

    public static void drawParticleZigZag(World level, IParticleData opt, BlockPos a, BlockPos b, int frequency, int segments, double noise){
        drawParticleZigZag(level, opt, a.getX()+0.5, a.getY()+0.5, a.getZ()+0.5, b.getX()+0.5, b.getY()+0.5, b.getZ()+0.5, frequency, segments, noise);
    }

    public static void drawParticleZigZag(World level, IParticleData opt, double x1, double y1, double z1, double x2, double y2, double z2, int frequency, int segments, double noise){
        double prev_x = x1;
        double prev_y = y1;
        double prev_z = z1;

        // For each line segment:
        // - Find a minimum and maximum length ('progress')
        // - Choose an actual length with these as bounds
        // - Adjust x y and z by the chosen progress
        // - Deflect x y and z by a random amount
        for(int i = 0; i < segments; i++){
            double next_x;
            double next_y;
            double next_z;

            if(i == segments-1){
                next_x = x2;
                next_y = y2;
                next_z = z2;
            }else{
                double min_progress = 1.0/(segments+3);
                double max_progress = segments==3 ? 1.0 : 1.0/(segments-3);
                double actual_progress = ThreadLocalRandom.current().nextDouble(min_progress, max_progress);

                double x_dist = Math.abs(x2 - prev_x) * actual_progress;
                double y_dist = Math.abs(y2 - prev_y) * actual_progress;
                double z_dist = Math.abs(z2 - prev_z) * actual_progress;

                if (x2 > 0)
                    next_x = x2 > prev_x ? prev_x + x_dist : prev_x - x_dist;
                else
                    next_x = x2 < prev_x ? prev_x - x_dist : prev_x + x_dist;

                if (y2 > 0)
                    next_y = y2 > prev_y ? prev_y + y_dist : prev_y - y_dist;
                else
                    next_y = y2 < prev_y ? prev_y - y_dist : prev_y + y_dist;

                if (z2 > 0)
                    next_z = z2 > prev_z ? prev_z + z_dist : prev_z - z_dist;
                else
                    next_z = z2 < prev_z ? prev_z - z_dist : prev_z + z_dist;

                next_x += (level.getRandom().nextFloat()-0.5) * noise;
                next_y += (level.getRandom().nextFloat()-0.5) * noise;
                next_z += (level.getRandom().nextFloat()-0.5) * noise;
            }
            drawParticleLine(level, opt, prev_x, prev_y, prev_z, next_x, next_y, next_z, frequency, 0);

            prev_x = next_x;
            prev_y = next_y;
            prev_z = next_z;
        }
    }

    public static void drawParticleRing(World level, IParticleData opt, BlockPos pos, double height, double radius, int frequency){
        drawExactParticleRing(level, opt, Vector3d.copyCenteredHorizontally(pos), height, radius, frequency);
    }

    public static void drawExactParticleRing(World level, IParticleData opt, Vector3d pos, double height, double radius, int frequency){
        for(int i = 0; i < frequency; i++){
            int deflection_angle = level.getRandom().nextInt(360);
            drawDeflectedParticle(level, opt, pos, height, radius, deflection_angle);
        }
    }

    public static void drawDeflectedParticle(World level, IParticleData opt, Vector3d pos, double height, double radius, int deflection_angle) {
        double x = Math.cos(Math.toRadians(deflection_angle)) * radius + pos.x;
        double z = Math.sin(Math.toRadians(deflection_angle)) * radius + pos.z;
        drawParticle(level, opt, x, pos.y + height, z);
    }

    public static void drawParticleSphere(World level, IParticleData opt, Vector3d pos, double radius, int frequency){
        for(int i = 0; i < frequency; i++){
            double x = level.getRandom().nextGaussian();
            double y = level.getRandom().nextGaussian();
            double z = level.getRandom().nextGaussian();
            double normalizer = 1 / Math.sqrt(x * x + y * y + z * z);

            x = x * normalizer * radius;
            y = y * normalizer * radius;
            z = z * normalizer * radius;

            drawParticle(level, opt, pos.x + x, pos.y + y, pos.z + z);
        }
    }
}
