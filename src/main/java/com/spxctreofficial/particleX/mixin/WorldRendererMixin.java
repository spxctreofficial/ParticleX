package com.spxctreofficial.particleX.mixin;

import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

	@Shadow protected abstract ParticlesMode getRandomParticleSpawnChance(boolean canSpawnOnMinimal);

	ParticleEffect particleEffect;

	@Inject(method = "spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;getRandomParticleSpawnChance(Z)Lnet/minecraft/client/option/ParticlesMode;"), cancellable = true)
	public void spawnParticle(ParticleEffect particleEffect, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> infoReturnable) {
		if (particleEffect == ParticleTypes.SPLASH
				|| particleEffect == ParticleTypes.ASH
				|| particleEffect == ParticleTypes.CRIMSON_SPORE 
				|| particleEffect == ParticleTypes.WARPED_SPORE
				|| particleEffect == ParticleTypes.ANGRY_VILLAGER
				|| particleEffect == ParticleTypes.HAPPY_VILLAGER
				|| particleEffect == ParticleTypes.GLOW
				|| particleEffect == ParticleTypes.SMOKE
				|| particleEffect == ParticleTypes.CAMPFIRE_COSY_SMOKE
				|| particleEffect == ParticleTypes.FLAME
				|| particleEffect == ParticleTypes.ITEM_SLIME
		) {
			this.particleEffect = particleEffect;
			infoReturnable.setReturnValue(null);
		}
	}

	@Redirect(method = "spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;getRandomParticleSpawnChance(Z)Lnet/minecraft/client/option/ParticlesMode;"))
	public ParticlesMode particleOverride(WorldRenderer worldRenderer, boolean canSpawnOnMinimal) {
		if (particleEffect == ParticleTypes.CRIT || particleEffect == ParticleTypes.ENCHANTED_HIT) {
			return ParticlesMode.ALL;
		}
		return getRandomParticleSpawnChance(canSpawnOnMinimal);
	}
}
