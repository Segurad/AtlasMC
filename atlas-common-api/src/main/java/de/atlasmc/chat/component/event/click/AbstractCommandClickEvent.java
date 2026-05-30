package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public abstract class AbstractCommandClickEvent implements ClickEvent {
	
	@NotNull
	public static final NBTCodec<AbstractCommandClickEvent>
	NBT_CODEC = NBTCodec
					.builder(AbstractCommandClickEvent.class)
					.include(ClickEvent.NBT_CODEC)
					.codec("command", AbstractCommandClickEvent::getCommand, AbstractCommandClickEvent::setCommand, NBTCodecs.STRING)
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
		return NBT_CODEC;
	}

}
