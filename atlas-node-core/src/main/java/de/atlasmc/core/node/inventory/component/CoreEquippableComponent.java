package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readDataSet;
import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeDataSet;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.readSound;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSound;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EquippableComponent;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class CoreEquippableComponent extends AbstractItemComponent implements EquippableComponent {
	
	protected static final Object2IntMap<EquipmentSlot> SLOT_TO_ID;
	protected static final Int2ObjectMap<EquipmentSlot> ID_TO_SLOT;
	
	static {
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
	private boolean equipOnInteract;
	private NamespacedKey cameraOverlay;
	
	public CoreEquippableComponent(ComponentType type) {
		super(type);
		dispensable = true;
		swappable = true;
		damageOnHurt = true;
		equipOnInteract = true;
		equipSound = EnumSound.ITEM_ARMOR_EQUIP_GENERIC;
	}
	
	@Override
	public CoreEquippableComponent clone() {
		CoreEquippableComponent clone = (CoreEquippableComponent) super.clone();
		return clone;
	}
	
	@Override
	public boolean isEquipOnInteract() {
		return equipOnInteract;
	}
	
	@Override
	public void setEquipOnInteract(boolean equipOnInteract) {
		this.equipOnInteract = equipOnInteract;
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
