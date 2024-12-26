package de.atlasmc.entity;

import de.atlasmc.ProjectileSource;
import de.atlasmc.world.World;

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
		WITHER_SKULL(WitherSkull.class),
		WIND_CHARGE(WindCharge.class);

		private Class<? extends Projectile> pro;

		private ProjectileType(Class<? extends Projectile> pro) {
			this.pro = pro;
		}

		/**
		 * Returns the class representing the {@link Projectile} entity of this type
		 * @return class
		 */
		public Class<? extends Projectile> getProjectileClass() {
			return pro;
		}

		/**
		 * Returns a unspawned {@link Projectile} represented by the {@link ProjectileType}
		 * @param world
		 * @see EntityType#create(World, java.util.UUID)
		 * @return projectile
		 */
		public Projectile create(World world) {
			switch(this) {
			case ARROW:
				return (Projectile) EntityType.ARROW.create(world);
			case DRAGON_FIREBALL:
				return (Projectile) EntityType.DRAGON_FIREBALL.create(world);
			case EGG:
				return (Projectile) EntityType.EGG.create(world);
			case ENDER_PEARL:
				return (Projectile) EntityType.ENDER_PEARL.create(world);
			case SHULKER_BULLET:
				return (Projectile) EntityType.SHULKER_BULLET.create(world);
			case SMALL_FIREBALL:
				return (Projectile) EntityType.SMALL_FIREBALL.create(world);
			case SNOWBALL:
				return (Projectile) EntityType.SNOWBALL.create(world);
			case SPECTRAL_ARROW:
				return (Projectile) EntityType.SPECTRAL_ARROW.create(world);
			case TRIDENT:
				return (Projectile) EntityType.TRIDENT.create(world);
			case WITHER_SKULL:
				return (Projectile) EntityType.WITHER_SKULL.create(world);
			case WIND_CHARGE:
				return (Projectile) EntityType.WIND_CHARGE.create(world);
			default:
				throw new IllegalStateException("Missing create implementation for type: " + this.name());
			}
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
