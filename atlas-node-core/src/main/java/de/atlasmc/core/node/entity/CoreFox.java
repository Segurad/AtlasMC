package de.atlasmc.core.node.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Fox;
import de.atlasmc.node.entity.metadata.type.MetaData;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreFox extends CoreAgeableMob implements Fox {

	protected static final int 
	FLAG_IS_SITTING = 0x01,
	FLAG_IS_CROUCHING = 0x04,
	FLAG_IS_INTERESTED = 0x08,
	FLAG_IS_POUNCING = 0x10,
	FLAG_IS_SLEEPING = 0x20,
	FLAG_IS_FACEPLANTED = 0x40,
	FLAG_IS_DEFENDING = 0x80;
	
	protected static final MetaDataField<Type>
	META_FOX_TYPE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, Type.RED, MetaDataType.getVarIntEnumType(Type.class));
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is crouching<br>
	 * 0x08 - Is interested<br>
	 * 0x10 - Is pouncing<br>
	 * 0x20 - Is sleeping<br>
	 * 0x40 - Is faceplanted<br>
	 * 0x80 - Is defending<br>
	 */
	protected static final MetaDataField<Byte>
	META_FOX_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_FOX_FIRST_TRUSTED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, null, MetaDataType.OPT_UUID);
	protected static final MetaDataField<UUID>
	META_FOX_LAST_TRUSTED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX + 4, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+4;
	
	private List<UUID> trusted;
	
	public CoreFox(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FOX_TYPE);
		metaContainer.set(META_FOX_FLAGS);
		metaContainer.set(META_FOX_FIRST_TRUSTED);
		metaContainer.set(META_FOX_LAST_TRUSTED);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Type getFoxType() {
		return metaContainer.getData(META_FOX_TYPE);
	}

	@Override
	public boolean isSitting() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_SITTING) == FLAG_IS_SITTING;
	}

	@Override
	public boolean isInterested() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_INTERESTED) == FLAG_IS_INTERESTED;
	}

	@Override
	public boolean isPouncing() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_POUNCING) == FLAG_IS_POUNCING;
	}

	@Override
	public boolean isSleeping() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_SLEEPING) == FLAG_IS_SLEEPING;
	}

	@Override
	public boolean isFaceplanted() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_FACEPLANTED) == FLAG_IS_FACEPLANTED;
	}

	@Override
	public boolean isDefending() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_DEFENDING) == FLAG_IS_DEFENDING;
	}

	@Override
	public UUID getFirstTrusted() {
		return metaContainer.getData(META_FOX_FIRST_TRUSTED);
	}

	@Override
	public UUID getSecondTrusted() {
		return metaContainer.getData(META_FOX_LAST_TRUSTED);
	}

	@Override
	public void setFoxType(Type type) {
		metaContainer.get(META_FOX_TYPE).setData(type);
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (sitting ? data.getData() | FLAG_IS_SITTING : data.getData() & ~FLAG_IS_SITTING));
	}

	@Override
	public void setInterested(boolean interested) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (interested ? data.getData() | FLAG_IS_INTERESTED : data.getData() & ~FLAG_IS_INTERESTED));
	}

	@Override
	public void setPouncing(boolean pouncing) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (pouncing ? data.getData() | FLAG_IS_POUNCING : data.getData() & ~FLAG_IS_POUNCING));
	}

	@Override
	public void setSleeping(boolean sleeping) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (sleeping ? data.getData() | FLAG_IS_SLEEPING : data.getData() & ~FLAG_IS_SLEEPING));
	}

	@Override
	public void setFaceplanted(boolean faceplanted) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (faceplanted ? data.getData() | FLAG_IS_FACEPLANTED : data.getData() & ~FLAG_IS_FACEPLANTED));
	}

	@Override
	public void setDefending(boolean defending) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (defending ? data.getData() | FLAG_IS_DEFENDING : data.getData() & ~FLAG_IS_DEFENDING));		
	}

	@Override
	public void setFirstTrusted(UUID uuid) {
		metaContainer.get(META_FOX_FIRST_TRUSTED).setData(uuid);		
	}

	@Override
	public void setSecondTrusted(UUID uuid) {
		metaContainer.get(META_FOX_LAST_TRUSTED).setData(uuid);		
	}

	@Override
	public void setCrouching(boolean crouching) {
		MetaData<Byte> data = metaContainer.get(META_FOX_FLAGS);
		data.setData((byte) (crouching ? data.getData() | FLAG_IS_CROUCHING : data.getData() & ~FLAG_IS_CROUCHING));
	}
	
	@Override
	public boolean isCrouching() {
		return (metaContainer.getData(META_FOX_FLAGS) & FLAG_IS_CROUCHING) == FLAG_IS_CROUCHING;
	}

	@Override
	public void addTrusted(UUID trusted) {
		if (trusted == null)
			throw new IllegalArgumentException("Trusted can not be null!");
		getTrusted().add(trusted);
	}

	@Override
	public boolean isTrusted(UUID trusted) {
		if (trusted == null || !hasTrusted())
			return false;
		return this.trusted.contains(trusted);
	}

	@Override
	public List<UUID> getTrusted() {
		if (trusted == null)
			trusted = new ArrayList<>();
		return trusted;
	}

	@Override
	public boolean removeTrusted(UUID trusted) {
		if (trusted == null || !hasTrusted())
			return false;
		return this.trusted.remove(trusted);
	}

	@Override
	public boolean hasTrusted() {
		return trusted != null && !trusted.isEmpty();
	}

}
