package de.atlasmc.chat.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class NBTComponent extends AbstractBaseComponent<NBTComponent> {
	
	public static final NBTSerializationHandler<NBTComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(NBTComponent.class)
					.string("source", NBTComponent::getSource, NBTComponent::setSource)
					.string("nbt", NBTComponent::getNbtPath, NBTComponent::setNbtPath)
					.boolField("interpret", NBTComponent::isInterpret, NBTComponent::setInterpret, false)
					.typeComponentField("separator", NBTComponent::getSeparator, NBTComponent::setSeparator, ChatComponent.NBT_HANDLER)
					.string("block", NBTComponent::getBlock, NBTComponent::setBlock)
					.string("entity", NBTComponent::getEntity, NBTComponent::setEntity)
					.string("storage", NBTComponent::getStorage, NBTComponent::setStorage)
					.build();
	
	private String source;
	private String nbtPath;
	private boolean interpret;
	private ChatComponent separator;
	private String block;
	private String entity;
	private String storage;

	@Override
	public ComponentType getType() {
		return ComponentType.NBT;
	}

	@Override
	protected NBTComponent getThis() {
		return this;
	}

	public String getSource() {
		return source;
	}

	public String getNbtPath() {
		return nbtPath;
	}

	public boolean isInterpret() {
		return interpret;
	}

	public ChatComponent getSeparator() {
		return separator;
	}

	public String getBlock() {
		return block;
	}

	public String getEntity() {
		return entity;
	}

	public String getStorage() {
		return storage;
	}

	public NBTComponent setSource(String source) {
		this.source = source;
		return this;
	}

	public NBTComponent setNbtPath(String nbtPath) {
		this.nbtPath = nbtPath;
		return this;
	}

	public NBTComponent setInterpret(boolean interpret) {
		this.interpret = interpret;
		return this;
	}

	public NBTComponent setSeparator(ChatComponent separator) {
		this.separator = separator;
		return this;
	}

	public NBTComponent setBlock(String block) {
		this.block = block;
		return this;
	}

	public NBTComponent setEntity(String entity) {
		this.entity = entity;
		return this;
	}

	public NBTComponent setStorage(String storage) {
		this.storage = storage;
		return this;
	}

}
