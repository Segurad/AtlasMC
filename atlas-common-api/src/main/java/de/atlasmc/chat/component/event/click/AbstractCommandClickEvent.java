package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.codec.NBTCodec;

public abstract class AbstractCommandClickEvent implements ClickEvent {
	
	public static final NBTCodec<AbstractCommandClickEvent>
	NBT_HANDLER = NBTCodec
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
	public NBTCodec<? extends AbstractCommandClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
