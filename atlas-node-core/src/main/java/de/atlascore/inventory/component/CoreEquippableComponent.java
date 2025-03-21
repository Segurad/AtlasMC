package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EquippableComponent;
import de.atlasmc.registry.Registries;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreEquippableComponent extends AbstractItemComponent implements EquippableComponent {

	protected static final NBTFieldSet<CoreEquippableComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_SLOT = CharKey.literal("slot"),
	NBT_EQUIP_SOUND = CharKey.literal("equip_sound"),
	NBT_ASSET_ID = CharKey.literal("asset_id"),
	NBT_ALLOWED_ENTITIES = CharKey.literal("allowed_entities"),
	NBT_DISPENSABLE = CharKey.literal("dispensable"),
	NBT_SWAPPABLE = CharKey.literal("swappable"),
	NBT_DAMAGE_ON_HURT = CharKey.literal("damage_on_hurt"),
	NBT_CAMERA_OVERLAY = CharKey.literal("camera_overlay");
	
	protected static final Object2IntMap<EquipmentSlot> SLOT_TO_ID;
	protected static final Int2ObjectMap<EquipmentSlot> ID_TO_SLOT;
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_SLOT, (holder, reader) -> {
			holder.slot = EquipmentSlot.getByName(reader.readStringTag());
		});
		NBT_FIELDS.setField(NBT_EQUIP_SOUND, (holder, reader) -> {
			holder.setEquipSound(Sound.fromNBT(reader));
		});
		NBT_FIELDS.setField(NBT_ASSET_ID, (holder, reader) -> {
			holder.assetID = reader.readNamespacedKey();
		});
		NBT_FIELDS.setField(NBT_ALLOWED_ENTITIES, (holder, reader) -> {
			holder.allowedEntities = DataSet.getFromNBT(Registries.getRegistry(EntityType.class), reader);
		});
		NBT_FIELDS.setField(NBT_DISPENSABLE, (holder, reader) -> {
			holder.dispensable = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_SWAPPABLE, (holder, reader) -> {
			holder.swappable = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_DAMAGE_ON_HURT, (holder, reader) -> {
			holder.damageOnHurt = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_CAMERA_OVERLAY, (holder, reader) -> {
			holder.cameraOverlay = reader.readNamespacedKey();
		});
		Object2IntMap<EquipmentSlot> slotToID = new Object2IntOpenHashMap<>();
		slotToID.put(EquipmentSlot.MAIN_HAND, 0);
		slotToID.put(EquipmentSlot.FEET, 1);
		slotToID.put(EquipmentSlot.LEGS, 2);
		slotToID.put(EquipmentSlot.CHEST, 3);
		slotToID.put(EquipmentSlot.HEAD, 4);
		slotToID.put(EquipmentSlot.OFF_HAND, 5);
		slotToID.put(EquipmentSlot.BODY, 6);
		SLOT_TO_ID = Object2IntMaps.unmodifiable(slotToID);
		Int2ObjectMap<EquipmentSlot> idToSlot = new Int2ObjectOpenHashMap<>();
		for (Entry<EquipmentSlot> entry : slotToID.object2IntEntrySet()) {
			idToSlot.put(entry.getIntValue(), entry.getKey());
		}
		ID_TO_SLOT = Int2ObjectMaps.unmodifiable(idToSlot);
	}
	
	private EquipmentSlot slot;
	private Sound equipSound;
	private NamespacedKey assetID;
	private DataSet<EntityType> allowedEntities;
	private boolean dispensable;
	private boolean swappable;
	private boolean damageOnHurt;
	private NamespacedKey cameraOverlay;
	
	public CoreEquippableComponent(NamespacedKey key) {
		super(key);
		dispensable = true;
		swappable = true;
		damageOnHurt = true;
		equipSound = EnumSound.ITEM_ARMOR_EQUIP_GENERIC;
	}
	
	@Override
	public CoreEquippableComponent clone() {
		CoreEquippableComponent clone = (CoreEquippableComponent) super.clone();
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (slot != null)
			writer.writeStringTag(NBT_SLOT, slot.getName());
		if (equipSound != EnumSound.ITEM_ARMOR_EQUIP_GENERIC)
			Sound.toNBT(NBT_EQUIP_SOUND, equipSound, writer, systemData);
		if (assetID != null)
			writer.writeNamespacedKey(NBT_ASSET_ID, assetID);
		if (allowedEntities != null)
			DataSet.toNBT(NBT_ALLOWED_ENTITIES, allowedEntities, writer, systemData);
		if (!dispensable)
			writer.writeByteTag(NBT_DISPENSABLE, false);
		if (!swappable)
			writer.writeByteTag(NBT_SWAPPABLE, false);
		if (!damageOnHurt)
			writer.writeByteTag(NBT_DAMAGE_ON_HURT, false);
		if (cameraOverlay != null)
			writer.writeNamespacedKey(NBT_CAMERA_OVERLAY, cameraOverlay);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public EquipmentSlot getSlot() {
		return slot;
	}

	@Override
	public void setSlot(EquipmentSlot slot) {
		this.slot = slot;
	}

	@Override
	public Sound getEquipSound() {
		return equipSound;
	}

	@Override
	public void setEquipSound(Sound sound) {
		if (sound == null) {
			this.equipSound = EnumSound.ITEM_ARMOR_EQUIP_GENERIC;
		} else {
			this.equipSound = sound;
		}
	}

	@Override
	public boolean hasAllowedEntities() {
		return allowedEntities != null;
	}

	@Override
	public boolean isDispensable() {
		return dispensable;
	}

	@Override
	public void setDispensable(boolean dispensable) {
		this.dispensable = dispensable;
	}

	@Override
	public boolean isSwappable() {
		return swappable;
	}

	@Override
	public void setSwappable(boolean swappable) {
		this.swappable = swappable;
	}

	@Override
	public boolean isDamageOnHurt() {
		return damageOnHurt;
	}

	@Override
	public void setDamageOnHurt(boolean damage) {
		this.damageOnHurt = damage;
	}

	@Override
	public DataSet<EntityType> getAllowedEntities() {
		return allowedEntities;
	}

	@Override
	public void setAllowedEntities(DataSet<EntityType> entities) {
		this.allowedEntities = entities;
	}

	@Override
	public NamespacedKey getCameraOverlay() {
		return cameraOverlay;
	}

	@Override
	public void setCameraOverlay(NamespacedKey overlay) {
		this.cameraOverlay = overlay;
	}

	@Override
	public NamespacedKey getAssetID() {
		return assetID;
	}

	@Override
	public void setAssetID(NamespacedKey assetID) {
		this.assetID = assetID;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.EQUIPPABLE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		slot = ID_TO_SLOT.get(readVarInt(buf));
		equipSound = readSound(buf);
		if (buf.readBoolean())
			assetID = readIdentifier(buf);
		if (buf.readBoolean())
			cameraOverlay = readIdentifier(buf);
		if (buf.readBoolean())
			allowedEntities = readDataSet(Registries.getRegistry(EntityType.class), buf);
		dispensable = buf.readBoolean();
		swappable = buf.readBoolean();
		damageOnHurt = buf.readBoolean();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(SLOT_TO_ID.getInt(slot), buf);
		writeSound(equipSound, buf);
		if (assetID != null) {
			buf.writeBoolean(true);
			writeIdentifier(assetID, buf);
		} else {
			buf.writeBoolean(false);
		}
		if (cameraOverlay != null) {
			buf.writeBoolean(true);
			writeIdentifier(cameraOverlay, buf);
		} else {
			buf.writeBoolean(false);
		}
		if (allowedEntities != null) {
			buf.writeBoolean(true);
			writeDataSet(allowedEntities, Registries.getRegistry(EntityType.class), buf);
		} else {
			buf.writeBoolean(false);
		}
		buf.writeBoolean(dispensable);
		buf.writeBoolean(swappable);
		buf.writeBoolean(damageOnHurt);
	}

}
