package de.atlasmc.entity;

import de.atlasmc.ProjectileSource;

public interface Projectile extends Entity {
	
	public ProjectileSource getShooter();
	
	public boolean doesBounce();
	
	public void setShooter(ProjectileSource source);
	
	public void setBounce(boolean bounce);
	
	public ProjectileType getProjectileType();
	
	public static enum ProjectileType {

		ARROW(Arrow.class),
		DRAGON_FIREBALL(DragonFireball.class),
		EGG(Egg.class),
		FIREWORK_ROCKET(FireworkRocket.class),
		EYE_OF_ENDER(EyeOfEnder.class),
		ENDER_PEARL(EnderPearl.class),
		FISH_HOOK(FishingHook.class),
		LARGE_FIREBALL(LargeFireball.class),
		LLAMA_SPIT(LlamaSpit.class),
		SHULKER_BULLET(ShulkerBullet.class),
		SMALL_FIREBALL(SmallFireball.class),
		SNOWBALL(Snowball.class),
		SPECTRAL_ARROW(SpectralArrow.class),
		EXP_BOTTLE(ExpBottle.class),
		POTION(Potion.class),
		TRIDENT(Trident.class),
		WITHER_SKULL(WitherSkull.class);

		private Class<? extends Projectile> pro;

		private ProjectileType(Class<? extends Projectile> pro) {
			this.pro = pro;
		}

		public Class<? extends Projectile> getProjectileClass() {
			return pro;
		}

		public static ProjectileType get(String arg0) {
			for (ProjectileType type : ProjectileType.values()) {
				if (type.toString().equals(arg0)) {
					return type;
				}
			}
			return ARROW;
		}
	}
	
}
