package net.team_prometheus.pyromancer.particles;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RotParticle extends TextureSheetParticle {
    public static RotParticleProvider provider(SpriteSet spriteSet) {
        return new RotParticleProvider(spriteSet);
    }
    public static class RotParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public RotParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        public Particle createParticle(@NotNull SimpleParticleType typeIn, @NotNull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RotParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
    private final SpriteSet spriteSet;

    protected RotParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 1.5f;
        this.lifetime = Math.max(1, 10 + (this.random.nextInt(2) - 1));
        this.gravity = 0.1f;
        this.hasPhysics = true;
        this.xd = vx * 0.3;
        this.yd = vy * 0.3;
        this.zd = vz * 0.3;
        this.pickSprite(spriteSet);
    }
    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    @Override
    public void tick() {
        super.tick();
    }
}

