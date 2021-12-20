package com.spxctreofficial.particleX.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	public final Entity entity = (Entity) (Object) this;
	public final Random random = new Random();

	@Inject(method = "addDeathParticles", at = @At("HEAD"), cancellable = true)
	private void addDeathParticles(CallbackInfo info) {
		for (int i = 0; i < 3; i++) {
			double d = this.random.nextGaussian() * 0.02;
			double e = this.random.nextGaussian() * 0.02;
			double f = this.random.nextGaussian() * 0.02;
			
			entity.world.addParticle(ParticleTypes.POOF, entity.getParticleX(0.5), entity.getRandomBodyY(), entity.getParticleZ(0.5), d, e, f);
			info.cancel();
		}
			
	}
}
