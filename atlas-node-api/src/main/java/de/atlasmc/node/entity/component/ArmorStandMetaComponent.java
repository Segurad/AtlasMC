package de.atlasmc.node.entity.component;

import java.util.Objects;

import org.joml.Vector3f;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.UnsafeAPI;

public class ArmorStandMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ArmorStandMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(ArmorStandMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("DisabledSlots", ArmorStandMetaComponent::getSlotInteractionFlags, ArmorStandMetaComponent::setSlotInteractionFlags, 0)
					.boolField("Marker", ArmorStandMetaComponent::isMarker, ArmorStandMetaComponent::setMarker, false)
					.boolField("NoBasePlate", ArmorStandMetaComponent::hasNoBasePlate, ArmorStandMetaComponent::setNoBasePlate, false)
					.beginComponent("Pose")
					.codec("Body", (h) -> h.getPoseUnsafe(Part.BODY), (h, v) -> h.setPose(Part.BODY, v), NBTCodecs.VECTOR_3F)
					.codec("Head", (h) -> h.getPoseUnsafe(Part.HEAD), (h, v) -> h.setPose(Part.HEAD, v), NBTCodecs.VECTOR_3F)
					.codec("LeftArm", (h) -> h.getPoseUnsafe(Part.LEFT_ARM), (h, v) -> h.setPose(Part.LEFT_ARM, v), NBTCodecs.VECTOR_3F)
					.codec("LeftLeg", (h) -> h.getPoseUnsafe(Part.LEFT_LEG), (h, v) -> h.setPose(Part.LEFT_LEG, v), NBTCodecs.VECTOR_3F)
					.codec("RightArm", (h) -> h.getPoseUnsafe(Part.RIGHT_ARM), (h, v) -> h.setPose(Part.RIGHT_ARM, v), NBTCodecs.VECTOR_3F)
					.codec("RightLeg", (h) -> h.getPoseUnsafe(Part.RIGHT_LEG), (h, v) -> h.setPose(Part.RIGHT_LEG, v), NBTCodecs.VECTOR_3F)
					.endComponent()
					.boolField("ShowArms", ArmorStandMetaComponent::hasArms, ArmorStandMetaComponent::setArms, false)
					.boolField("Small", ArmorStandMetaComponent::isSmall, ArmorStandMetaComponent::setSmall, false)
					.build();
	
	public static final int
	FLAG_IS_SMALL = 0x01,
	FLAG_HAS_ARMS = 0x04,
	FLAG_HAS_NO_BASEPLATE = 0x08,
	FLAG_IS_MARKER = 0x10;
	/**
	 * <table>
	 * <tr><th>Bit</th><th>Description</th></tr>
	 * <tr><td>0x01</td><td>Is Small</td></tr>
	 * <tr><td>0x04</td><td>Has Arms</td></tr>
	 * <tr><td>0x08</td><td>Has no BasePlate</td></tr>
	 * <tr><td>0x10</td><td>is Marker</td></tr>
	 * </table>
	 */
	public static final MetaDataField<Byte>
	META_ARMOR_STAND_FLAGS = new MetaDataField<>(15, (byte) 0, EntityMetaTypes.BYTE);
	public static final MetaDataField<Vector3f>
	META_ROTATION_HEAD = new MetaDataField<>(16, new Vector3f(), EntityMetaTypes.ROTATION);
	public static final MetaDataField<Vector3f>
	META_ROTATION_BODY = new MetaDataField<>(17, new Vector3f(), EntityMetaTypes.ROTATION);
	public static final MetaDataField<Vector3f>
	META_ROTATION_LEFT_ARM = new MetaDataField<>(18, new Vector3f(-10.0f, 0.0f, -10.0f), EntityMetaTypes.ROTATION);
	public static final MetaDataField<Vector3f>
	META_ROTATION_RIGHT_ARM = new MetaDataField<>(19, new Vector3f(-15.0f, 0.0f, 10.0f), EntityMetaTypes.ROTATION);
	public static final MetaDataField<Vector3f>
	META_ROTATION_LEFT_LEG = new MetaDataField<>(20, new Vector3f(-1.0f, 0.0f, -1.0f), EntityMetaTypes.ROTATION);
	public static final MetaDataField<Vector3f>
	META_ROTATION_RIGHT_LEG = new MetaDataField<>(21, new Vector3f(1.0f, 0.0f, 1.0f), EntityMetaTypes.ROTATION);
	
	private int slotFlags;
	
	public ArmorStandMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_ARMOR_STAND_FLAGS);
		container.set(META_ROTATION_HEAD, new Vector3f(META_ROTATION_HEAD.getDefaultData()));
		container.set(META_ROTATION_BODY, new Vector3f(META_ROTATION_BODY.getDefaultData()));
		container.set(META_ROTATION_LEFT_ARM, new Vector3f(META_ROTATION_LEFT_ARM.getDefaultData()));
		container.set(META_ROTATION_RIGHT_ARM, new Vector3f(META_ROTATION_RIGHT_ARM.getDefaultData()));
		container.set(META_ROTATION_LEFT_LEG, new Vector3f(META_ROTATION_LEFT_LEG.getDefaultData()));
		container.set(META_ROTATION_RIGHT_LEG, new Vector3f(META_ROTATION_RIGHT_LEG.getDefaultData()));
	}
	
	@Override
	public int getMetaFieldCount() {
		return 7;
	}

	public boolean isSmall() {
		return (getHolder().getMetaContainer().getData(META_ARMOR_STAND_FLAGS) & FLAG_IS_SMALL) == FLAG_IS_SMALL;
	}

	protected void setArmorStandFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_ARMOR_STAND_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_ARMOR_STAND_FLAGS, value);
	}
	
	public void setSmall(boolean small) {
		setArmorStandFlag(FLAG_IS_SMALL, small);
	}

	public boolean hasArms() {
		return (getHolder().getMetaContainer().getData(META_ARMOR_STAND_FLAGS) & FLAG_HAS_ARMS) == FLAG_HAS_ARMS;
	}

	public void setArms(boolean arms) {
		setArmorStandFlag(FLAG_HAS_ARMS, arms);
	}

	public boolean hasNoBasePlate() {
		return (getHolder().getMetaContainer().getData(META_ARMOR_STAND_FLAGS) & FLAG_HAS_NO_BASEPLATE) == FLAG_HAS_NO_BASEPLATE;
	}

	public void setNoBasePlate(boolean baseplate) {
		setArmorStandFlag(FLAG_HAS_NO_BASEPLATE, baseplate);
	}

	/**
	 * Returns whether or not this {@link ArmorStand} is a marker. Meaning having no hitbox
	 * @return true if is a marker
	 */
	public boolean isMarker() {
		return (getHolder().getMetaContainer().getData(META_ARMOR_STAND_FLAGS) & FLAG_IS_MARKER) == FLAG_IS_MARKER;
	}

	public void setMarker(boolean marker) {
		setArmorStandFlag(FLAG_IS_MARKER, marker);
	}

	/**
	 * Sets the slot interaction flags
	 * @see #getSlotInteractionFlags()
	 * @param flags the interaction flags
	 */
	public void setSlotInteractionFlags(int flags) {
		this.slotFlags = flags;
	}

	/**
	 * Returns the slot interaction flags as integer
	 * <table>
	 * <tr><th>Bitmask</th><th>Description</th></tr>
	 * <tr><td>0x000001</td><td>Disable remove items from hand</td></tr>
	 * <tr><td>0x000002</td><td>Disable remove item from foots</td></tr>
	 * <tr><td>0x000004</td><td>Disable remove item from legs</td></tr>
	 * <tr><td>0x000008</td><td>Disable remove item from body</td></tr>
	 * <tr><td>0x000010</td><td>Disable remove item from head</td></tr>
	 * <tr><td>0x000100</td><td>Disable swap items in hand</td></tr>
	 * <tr><td>0x000200</td><td>Disable swap item from foots</td></tr>
	 * <tr><td>0x000400</td><td>Disable swap item from legs</td></tr>
	 * <tr><td>0x000800</td><td>Disable swap item from body</td></tr>
	 * <tr><td>0x001000</td><td>Disable swap item from head</td></tr>
	 * <tr><td>0x010000</td><td>Disable set items in hand</td></tr>
	 * <tr><td>0x020000</td><td>Disable set item to foots</td></tr>
	 * <tr><td>0x040000</td><td>Disable set item to legs</td></tr>
	 * <tr><td>0x080000</td><td>Disable set item to body</td></tr>
	 * <tr><td>0x100000</td><td>Disable set item to head</td></tr>
	 * <tr><td></td><td></td></tr>
	 * <tr><td>0x1F1F1F</td><td>Disable all actions</td></tr>
	 * </table>
	 * @return flags
	 */
	public int getSlotInteractionFlags() {
		return slotFlags;
	}
	
	public void setPose(Part part, float x, float y, float z) {
		var container = getHolder().getMetaContainer();
		MetaData<Vector3f> data = container.get(part.field);
		data.getData().set(x, y, z);
		container.setChanged(part.field);
	}
	
	public Vector3f getPose(Part part) {
		return new Vector3f(getHolder().getMetaContainer().getData(part.field));
	}
	
	@UnsafeAPI
	public Vector3f getPoseUnsafe(Part part) {
		return getHolder().getMetaContainer().getData(part.field);
	}
	
	public Vector3f getPose(Part part, Vector3f angle) {
		angle.set(getHolder().getMetaContainer().getData(part.field));
		return angle; 
	}
	
	public void setPose(Part part, Vector3f angle) {
		setPose(part, angle.x, angle.y, angle.z);
	}
	
	public static enum Part {
		
		HEAD(META_ROTATION_HEAD),
		BODY(META_ROTATION_BODY), 
		RIGHT_LEG(META_ROTATION_RIGHT_LEG), 
		LEFT_LEG(META_ROTATION_LEFT_LEG), 
		RIGHT_ARM(META_ROTATION_RIGHT_ARM), 
		LEFT_ARM(META_ROTATION_RIGHT_ARM);
		
		public final MetaDataField<Vector3f> field;
		
		private Part(MetaDataField<Vector3f> limb) {
			this.field = Objects.requireNonNull(limb, "limb");
		}
		
	}
	
	@Override
	public NBTCodec<? extends ArmorStandMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
