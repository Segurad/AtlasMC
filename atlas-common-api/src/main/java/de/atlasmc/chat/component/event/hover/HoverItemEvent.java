package de.atlasmc.chat.component.event.hover;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.util.nbt.tag.NBT;

public class HoverItemEvent implements HoverEvent {
	
	public static final NBTSerializationHandler<HoverItemEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(HoverItemEvent.class)
					.include(HoverEvent.NBT_HANDLER)
					.namespacedKey("id", HoverItemEvent::getID, HoverItemEvent::setID)
					.intTag("count", HoverItemEvent::getCount, HoverItemEvent::setCount, 1)
					.rawField("components", TagType.COMPOUND, HoverItemEvent::getComponents, HoverItemEvent::setComponents, false)
					.build();
	
	private NamespacedKey id;
	private int count = 1;
	private NBT components;
	
	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ITEM;
	}

	public NamespacedKey getID() {
		return id;
	}
	
	public int getCount() {
		return count;
	}
	
	public NBT getComponents() {
		return components;
	}
	
	public void setID(NamespacedKey id) {
		this.id = id;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setComponents(NBT components) {
		this.components = components;
	}

	@Override
	public NBTSerializationHandler<? extends HoverItemEvent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
