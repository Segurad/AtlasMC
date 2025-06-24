package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public abstract class AbstractCommandClickEvent implements ClickEvent {
	
	public static final NBTSerializationHandler<AbstractCommandClickEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractCommandClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.string("command", AbstractCommandClickEvent::getCommand, AbstractCommandClickEvent::setCommand)
					.build();
	
	private String command;
	
	AbstractCommandClickEvent() {}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public NBTSerializationHandler<? extends AbstractCommandClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
