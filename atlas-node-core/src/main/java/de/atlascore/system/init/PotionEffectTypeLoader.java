package de.atlascore.system.init;

import static de.atlasmc.potion.PotionEffectType.*;

import de.atlascore.potion.CoreClassPotionEffectType;
import de.atlascore.potion.CoreEffectGlowing;
import de.atlascore.potion.CoreEffectHealthBoost;
import de.atlascore.potion.CoreEffectInstantDamage;
import de.atlascore.potion.CoreEffectInstantHealth;
import de.atlascore.potion.CoreEffectLuck;
import de.atlascore.potion.CoreEffectSlowness;
import de.atlascore.potion.CoreEffectSpeed;
import de.atlascore.potion.CoreEffectStrength;
import de.atlascore.potion.CoreEffectUnluck;
import de.atlascore.potion.CoreEffectWeakness;
import de.atlascore.potion.CoreEffectWither;
import de.atlascore.potion.CoreEffectNoEffect;
import de.atlascore.potion.CoreEffectPoison;
import de.atlascore.potion.CoreEffectRegeneration;

public class PotionEffectTypeLoader {
	
	public static void loadPotionEffects() {
		SPEED = new CoreClassPotionEffectType(1, 0x7CAFC6, CoreEffectSpeed.class);
		SLOWNESS = new CoreClassPotionEffectType(2, 0x5A6C81, CoreEffectSlowness.class);
		HASTE = new CoreClassPotionEffectType(3, 0xD9C043, CoreEffectNoEffect.class); // TODO implement effect
		MINING_FATIGUE = new CoreClassPotionEffectType(4, 0x4A4217, CoreEffectNoEffect.class); // TODO implement effect
		STRENGTH = new CoreClassPotionEffectType(5, 0x932423, CoreEffectStrength.class);
		INSTANT_HEALTH = new CoreClassPotionEffectType(6, 0xF82423, CoreEffectInstantHealth.class);
		INSTANT_DAMAGE = new CoreClassPotionEffectType(7, 0x430A09, CoreEffectInstantDamage.class);
		JUMP_BOOST = new CoreClassPotionEffectType(8, 0x22FFAC, CoreEffectNoEffect.class); // TODO implement effect
		NAUSEA = new CoreClassPotionEffectType(9, 0x551D4A, CoreEffectNoEffect.class);
		REGENERATION = new CoreClassPotionEffectType(10, 0xCD5CAB, CoreEffectRegeneration.class);
		RESISTANCE = new CoreClassPotionEffectType(11, 0x99453A, CoreEffectNoEffect.class); // TODO implement effect
		FIRE_RESISTANCE = new CoreClassPotionEffectType(12, 0xE49A3A, CoreEffectNoEffect.class); // TODO implement effect
		WATER_BREATHING = new CoreClassPotionEffectType(13, 0x2E5299, CoreEffectNoEffect.class); // TODO implement effect
		INVISIBILITY = new CoreClassPotionEffectType(14, 0x7F8392, CoreEffectNoEffect.class);
		BLINDNESS = new CoreClassPotionEffectType(15, 0x1F1F23, CoreEffectNoEffect.class);
		NIGHT_VISION = new CoreClassPotionEffectType(16, 0x1F1FA1, CoreEffectNoEffect.class);
		HUNGER = new CoreClassPotionEffectType(17, 0x587653, CoreEffectNoEffect.class); // TODO implement effect
		WEAKNESS = new CoreClassPotionEffectType(18, 0x484D48, CoreEffectWeakness.class);
		POISON = new CoreClassPotionEffectType(19, 0x4E9331, CoreEffectPoison.class);
		WITHER = new CoreClassPotionEffectType(20, 0x352A27, CoreEffectWither.class);
		HEALTH_BOOST = new CoreClassPotionEffectType(21, 0xF87D23, CoreEffectHealthBoost.class);
		ABSORPTION = new CoreClassPotionEffectType(22, 0x2552A5, CoreEffectNoEffect.class); // TODO implement effect
		SATURATION = new CoreClassPotionEffectType(23, 0xF82421, CoreEffectNoEffect.class); // TODO implement effect
		GLOWING = new CoreClassPotionEffectType(24, 0x94A061, CoreEffectGlowing.class);
		LEVITATION = new CoreClassPotionEffectType(25, 0xCEFFFF, CoreEffectNoEffect.class); // TODO implement effect
		LUCK = new CoreClassPotionEffectType(26, 0x339900, CoreEffectLuck.class);
		UNLUCK = new CoreClassPotionEffectType(27, 0xC0A44D, CoreEffectUnluck.class);
		SLOW_FALLING = new CoreClassPotionEffectType(28, 0xF7F8E0, CoreEffectNoEffect.class); // TODO implement effect
		CONDUIT_POWER = new CoreClassPotionEffectType(29, 0x1DC2D1, CoreEffectNoEffect.class); // TODO implement effect
		DOLPHINS_GRACE = new CoreClassPotionEffectType(30, 0x88A3BE, CoreEffectNoEffect.class); // TODO implement effect
		BAD_OMEN = new CoreClassPotionEffectType(31, 0xb6138, CoreEffectNoEffect.class); // TODO implement effect
		HERO_OF_THE_VILLAGE = new CoreClassPotionEffectType(32, 0x44FF44, CoreEffectNoEffect.class); // TODO implement effect
	}

}
