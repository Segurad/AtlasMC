package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Axolotl.Variant;
import de.atlasmc.node.entity.Salmon.Type;
import de.atlasmc.node.entity.TropicalFish.Pattern;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BucketEntityDataComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreBucketEntityDataComponent extends AbstractItemComponent implements BucketEntityDataComponent {
	
	private boolean noAi;
	private boolean silent;
	private boolean noGravity;
	private boolean glowing;
	private boolean invulnerable;
	private float health;
	private int age;
	private Variant variant;
	private long huntingCooldown;
	private int tropicalFishVariant;
	private Type type;
	
	public CoreBucketEntityDataComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreBucketEntityDataComponent clone() {
		return (CoreBucketEntityDataComponent) super.clone();
	}
	
	public int getTropicalFishVariant() {
		return tropicalFishVariant;
	}
	
	public void setTropicalFishVariant(int tropicalFishVariant) {
		this.tropicalFishVariant = tropicalFishVariant;
	}

	@Override
	public boolean hasNoAI() {
		return noAi;
	}

	@Override
	public void setNoAI(boolean ai) {
		this.noAi = ai;
	}

	@Override
	public boolean isSilent() {
		return silent;
	}

	@Override
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	@Override
	public boolean hasNoGravity() {
		return noGravity;
	}

	@Override
	public void setNoGravity(boolean gravity) {
		this.noGravity = gravity;
	}

	@Override
	public boolean isInvulnerable() {
		return invulnerable;
	}

	@Override
	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public void setHealth(float health) {
		this.health = health;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public Variant getVariant() {
		return variant;
	}

	@Override
	public void setVariant(Variant variant) {
		this.variant = variant;
	}

	@Override
	public long getHuntingCooldown() {
		return huntingCooldown;
	}

	@Override
	public void setHuntingCooldown(long cooldown) {
		this.huntingCooldown = cooldown;
	}

	@Override
	public DyeColor getBodyColor() {
		return EnumUtil.getByID(DyeColor.class, ((tropicalFishVariant >> 16) & 0xFF));
	}

	@Override
	public Pattern getPattern() {
		return Pattern.getByDataID(tropicalFishVariant);
	}

	@Override
	public DyeColor getPatternColor() {
		return EnumUtil.getByID(DyeColor.class, tropicalFishVariant >> 24);
	}

	@Override
	public void setBodyColor(DyeColor color) {
		int variant = this.tropicalFishVariant & 0xFF00FF;
		variant |= color.getID() << 16;
		this.tropicalFishVariant = variant;
	}

	@Override
	public void setPattern(Pattern pattern) {
		int variant = this.tropicalFishVariant & 0xFFFF00;
		variant |= pattern.getDataID();
		this.tropicalFishVariant = variant;
	}

	@Override
	public void setPatternColor(DyeColor color) {
		int variant = this.tropicalFishVariant & 0xFFFF;
		variant |= color.getID() << 24;
		this.tropicalFishVariant = variant;
	}

	@Override
	public boolean isGlowing() {
		return glowing;
	}

	@Override
	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	@Override
	public Type getSalmonType() {
		return type;
	}

	@Override
	public void setSalmonType(Type type) {
		this.type = type;
	}

}
