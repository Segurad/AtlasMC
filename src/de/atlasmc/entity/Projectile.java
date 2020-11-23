package de.atlasmc.entity;

public interface Projectile extends Entity {

	
	public static enum ProjectileType {

		ARROW(Arrow.class),
		DRAGON_FIREBALL(DragonFireball.class),
		EGG(ThrownEgg.class),
		ENDER_PEARL(EnderPearl.class),
		FIREBALL(Fireball.class),
		FISH_HOOK(FishingHook.class),
		LARGE_FIREBALL(LargeFireball.class),
		LAMA_SPIT(LlamaSpit.class),
		SHULKER_BULLET(ShulkerBullet.class),
		SMALL_FIREBALL(SmallFireball.class),
		SNOWBALL(Snowball.class),
		SPECTRAL_ARROW(SpectralArrow.class),
		THROWN_EXP_BOTTLE(ThrownExpBottle.class),
		THROWN_POTION(ThrownPotion.class),
		TRIDENT(ThrownTrident.class),
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
