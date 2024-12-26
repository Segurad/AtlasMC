package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Axolotl.Variant;
import de.atlasmc.entity.Salmon.Type;
import de.atlasmc.entity.TropicalFish.Pattern;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BucketEntityDataComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreBucketEntityDataComponent extends AbstractItemComponent implements BucketEntityDataComponent {

	protected static final CharKey
	NBT_NO_AI = CharKey.literal("NoAI"),
	NBT_SILENT = CharKey.literal("Silent"),
	NBT_NO_GRAVITY = CharKey.literal("NoGravity"),
	NBT_GLOWING = CharKey.literal("Glowing"),
	NBT_INVULNERABLE = CharKey.literal("Invulnerable"),
	NBT_HEALTH = CharKey.literal("Health"),
	NBT_AGE = CharKey.literal("Age"),
	NBT_VARIANT = CharKey.literal("Variant"),
	NBT_HUNTING_COOLDOWN = CharKey.literal("HuntingCooldown"),
	NBT_BUCKET_VARIANT_TAG = CharKey.literal("BucketVariantTag"),
	NBT_TYPE = CharKey.literal("type");
	
	protected static final NBTFieldContainer<CoreBucketEntityDataComponent> NBT_FIELDS;
	
	static {
		NBT_FIELDS = NBTFieldContainer.newContainer();
		NBT_FIELDS.setField(NBT_NO_AI, (holder, reader) -> {
			holder.ai = !reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_SILENT, (holder, reader) -> {
			holder.silent = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_NO_GRAVITY, (holder, reader) -> {
			holder.gravity = !reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_GLOWING, (holder, reader) -> {
			holder.glowing = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_INVULNERABLE, (holder, reader) -> {
			holder.invulnerable = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_HEALTH, (holder, reader) -> {
			holder.health = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			holder.age = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			holder.variant = Variant.getByID(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_HUNTING_COOLDOWN, (holder, reader) -> {
			holder.huntingCooldown = reader.readLongTag();
		});
		NBT_FIELDS.setField(NBT_BUCKET_VARIANT_TAG, (holder, reader) -> {
			int variant = reader.readIntTag();
			List<DyeColor> colors = DyeColor.getValues();
			holder.patternColor = colors.get(variant >> 24);
			holder.bodyColor = colors.get((variant >> 16) & 0xFF);
			holder.pattern = Pattern.getByDataID(variant);
		});
		NBT_FIELDS.setField(NBT_TYPE, (holder, reader) -> {
			holder.type = Type.getByName(reader.readStringTag());
		});
	}
	
	private boolean ai = true;
	private boolean silent;
	private boolean gravity = true;
	private boolean glowing;
	private boolean invulnerable;
	private float health;
	private int age;
	private Variant variant;
	private long huntingCooldown;
	private DyeColor patternColor;
	private DyeColor bodyColor;
	private Pattern pattern;
	private Type type;
	
	public CoreBucketEntityDataComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public CoreBucketEntityDataComponent clone() {
		return (CoreBucketEntityDataComponent) super.clone();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(getNamespacedKeyRaw());
		writeNBTContent(writer, systemData);
		writer.writeEndTag();
	}
	
	protected void writeNBTContent(NBTWriter writer, boolean systemData) throws IOException {
		if (!ai)
			writer.writeByteTag(NBT_NO_AI, true);
		if (silent)
			writer.writeByteTag(NBT_SILENT, true);
		if (!gravity)
			writer.writeByteTag(NBT_NO_GRAVITY, true);
		if (glowing)
			writer.writeByteTag(NBT_GLOWING, true);
		if (invulnerable)
			writer.writeByteTag(NBT_INVULNERABLE, true);
		if (health != 0.0f)
			writer.writeFloatTag(NBT_HEALTH, health);
		if (age != 0)
			writer.writeIntTag(NBT_AGE, age);
		if (variant != null)
			writer.writeIntTag(NBT_VARIANT, variant.getID());
		if (huntingCooldown != 0)
			writer.writeLongTag(NBT_HUNTING_COOLDOWN, huntingCooldown);
		if (pattern != null && bodyColor != null && patternColor != null) {
			writer.writeIntTag(NBT_BUCKET_VARIANT_TAG, pattern.getDataID() + bodyColor.getID() << 16 + patternColor.getID() << 24);
		}
		if (type != null)
			writer.writeStringTag(NBT_TYPE, type.getName());
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public boolean hasAI() {
		return ai;
	}

	@Override
	public void setAI(boolean ai) {
		this.ai = ai;
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
	public boolean hasGravity() {
		return gravity;
	}

	@Override
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
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
		return bodyColor;
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public DyeColor getPatternColor() {
		return patternColor;
	}

	@Override
	public void setBodyColor(DyeColor color) {
		this.bodyColor = color;
	}

	@Override
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public void setPatternColor(DyeColor color) {
		this.patternColor = color;
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
		fromNBT(reader);
		reader.close();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTWriter writer = new NBTNIOWriter(buf, true);
		writer.writeCompoundTag(null);
		writeNBTContent(writer, false);
		writer.writeEndTag();
		writer.close();
	}

}
