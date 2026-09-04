package de.atlasmc.node.entity.component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.IDHolder;
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
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class FoxMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<FoxMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(FoxMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("Crouching", FoxMetaComponent::isCrouching, FoxMetaComponent::setCrouching, false)
					.boolField("Sitting", FoxMetaComponent::isSitting, FoxMetaComponent::setSitting, false)
					.boolField("Sleeping", FoxMetaComponent::isSleeping, FoxMetaComponent::setSleeping, false)
					.codecList("Trusted", FoxMetaComponent::hasTrusted, FoxMetaComponent::getTrusted, NBTCodecs.UUID_CODEC, true)
					.codec("Type", FoxMetaComponent::getFoxType, FoxMetaComponent::setFoxType, EnumUtil.enumStringNBTCodec(Type.class), Type.RED)
					// non standard by atlas
					.boolField("Interested", FoxMetaComponent::isInterested, FoxMetaComponent::setInterested, false)
					.boolField("Pouncing", FoxMetaComponent::isPouncing, FoxMetaComponent::setPouncing, false)
					.boolField("Faceplanted", FoxMetaComponent::isFaceplanted, FoxMetaComponent::setFaceplanted)
					.build();
	
	public static final int 
	FLAG_IS_SITTING = 0x01,
	FLAG_IS_CROUCHING = 0x04,
	FLAG_IS_INTERESTED = 0x08,
	FLAG_IS_POUNCING = 0x10,
	FLAG_IS_SLEEPING = 0x20,
	FLAG_IS_FACEPLANTED = 0x40,
	FLAG_IS_DEFENDING = 0x80;
	
	public static final MetaDataField<Integer>
	META_FOX_TYPE = new MetaDataField<>(18, Type.RED.getID(), EntityMetaTypes.VAR_INT);
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is crouching<br>
	 * 0x08 - Is interested<br>
	 * 0x10 - Is pouncing<br>
	 * 0x20 - Is sleeping<br>
	 * 0x40 - Is faceplanted<br>
	 * 0x80 - Is defending<br>
	 */
	public static final MetaDataField<Byte>
	META_FOX_FLAGS = new MetaDataField<>(19, (byte) 0, EntityMetaTypes.BYTE);
	public static final MetaDataField<UUID>
	META_FOX_FIRST_TRUSTED = new MetaDataField<>(20, null, EntityMetaTypes.OPT_UUID);
	public static final MetaDataField<UUID>
	META_FOX_LAST_TRUSTED = new MetaDataField<>(21, null, EntityMetaTypes.OPT_UUID);
	
	private List<UUID> trusted;
	
	public FoxMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_FOX_TYPE);
		container.set(META_FOX_FLAGS);
		container.set(META_FOX_FIRST_TRUSTED);
		container.set(META_FOX_LAST_TRUSTED);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 4;
	}
	
	public Type getFoxType() {
		return EnumUtil.getByID(Type.class, getHolder().getMetaContainer().getData(META_FOX_TYPE));
	}

	public boolean isSitting() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_SITTING) == FLAG_IS_SITTING;
	}

	public boolean isInterested() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_INTERESTED) == FLAG_IS_INTERESTED;
	}

	public boolean isPouncing() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_POUNCING) == FLAG_IS_POUNCING;
	}

	public boolean isSleeping() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_SLEEPING) == FLAG_IS_SLEEPING;
	}

	public boolean isFaceplanted() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_FACEPLANTED) == FLAG_IS_FACEPLANTED;
	}

	public boolean isDefending() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_DEFENDING) == FLAG_IS_DEFENDING;
	}

	public UUID getFirstTrusted() {
		return getHolder().getMetaContainer().getData(META_FOX_FIRST_TRUSTED);
	}

	public UUID getSecondTrusted() {
		return getHolder().getMetaContainer().getData(META_FOX_LAST_TRUSTED);
	}

	public void setFoxType(Type type) {
		getHolder().getMetaContainer().setData(META_FOX_TYPE, type.getID());
	}

	private void setFoxFlag(int flag, boolean set) {
		MetaData<Byte> data = getHolder().getMetaContainer().get(META_FOX_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		getHolder().getMetaContainer().setData(META_FOX_FLAGS, value);
	}
	
	public void setSitting(boolean sitting) {
		setFoxFlag(FLAG_IS_SITTING, sitting);
	}

	public void setInterested(boolean interested) {
		setFoxFlag(FLAG_IS_INTERESTED, interested);
	}

	public void setPouncing(boolean pouncing) {
		setFoxFlag(FLAG_IS_POUNCING, pouncing);
	}

	public void setSleeping(boolean sleeping) {
		setFoxFlag(FLAG_IS_SLEEPING, sleeping);
	}

	public void setFaceplanted(boolean faceplanted) {
		setFoxFlag(FLAG_IS_FACEPLANTED, faceplanted);
	}

	public void setDefending(boolean defending) {
		setFoxFlag(FLAG_IS_DEFENDING, defending);
	}

	public void setFirstTrusted(UUID uuid) {
		getHolder().getMetaContainer().setData(META_FOX_FIRST_TRUSTED, uuid);		
	}

	public void setSecondTrusted(UUID uuid) {
		getHolder().getMetaContainer().setData(META_FOX_LAST_TRUSTED, uuid);		
	}

	public void setCrouching(boolean crouching) {
		setFoxFlag(FLAG_IS_CROUCHING, crouching);
	}
	
	public boolean isCrouching() {
		return (getHolder().getMetaContainer().getData(META_FOX_FLAGS) & FLAG_IS_CROUCHING) == FLAG_IS_CROUCHING;
	}

	public void addTrusted(UUID trusted) {
		if (trusted == null)
			throw new IllegalArgumentException("Trusted can not be null!");
		getTrusted().add(trusted);
	}

	public boolean isTrusted(UUID trusted) {
		if (trusted == null || !hasTrusted())
			return false;
		return this.trusted.contains(trusted);
	}

	public List<UUID> getTrusted() {
		if (trusted == null)
			trusted = new ArrayList<>();
		return trusted;
	}

	/**
	 * Removes the UUID from the trusted set
	 * @param trusted
	 * @return true if UUID was present and removed
	 */
	public boolean removeTrusted(UUID trusted) {
		if (trusted == null || !hasTrusted())
			return false;
		return this.trusted.remove(trusted);
	}

	public boolean hasTrusted() {
		return trusted != null && !trusted.isEmpty();
	}
	
	@Override
	public NBTCodec<? extends FoxMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

	public static enum Type implements EnumName, IDHolder {
		
		RED,
		SNOW;
		
		private final String name;
		
		private Type() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getID() {
			return ordinal();
		}

	}
	
}
