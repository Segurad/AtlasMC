package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Axolotl.Variant;
import de.atlasmc.entity.Salmon.Type;
import de.atlasmc.entity.TropicalFish.Pattern;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BucketEntityDataComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import io.netty.buffer.ByteBuf;

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
	
	public CoreBucketEntityDataComponent(NamespacedKey key) {
		super(key);
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
		return DyeColor.getByID((tropicalFishVariant >> 16) & 0xFF);
	}

	@Override
	public Pattern getPattern() {
		return Pattern.getByDataID(tropicalFishVariant);
	}

	@Override
	public DyeColor getPatternColor() {
		return DyeColor.getByID(tropicalFishVariant >> 24);
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
	
	@Override
	public ComponentType getType() {
		return ComponentType.BUCKET_ENTITY_DATA;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTReader reader = new NBTNIOReader(buf, true);
		BucketEntityDataComponent.NBT_HANDLER.deserialize(this, reader, NBTSerializationContext.DEFAULT_CLIENT);
		reader.close();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTWriter writer = new NBTNIOWriter(buf, true);
		BucketEntityDataComponent.NBT_HANDLER.serialize(this, writer, NBTSerializationContext.DEFAULT_CLIENT);
		writer.close();
	}

}
