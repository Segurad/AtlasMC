package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.CombatEvent;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCombatEvent extends AbstractPacket implements CombatEvent {

	private int event, duration, entityID, playerID;
	private String deathMessage;
	
	public CorePacketOutCombatEvent() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	/**
	 * 
	 * @param event 0, 1, or 2
	 * @param duration only if event 1
	 * @param entityID only if event 1 or 2
	 * @param playerID only if event 2
	 * @param deathMessage only if event 2
	 */
	public CorePacketOutCombatEvent(int event, int duration , int entityID, int playerID, ChatComponent deathMessage) {
		this();
		this.event = event;
		this.duration = duration;
		this.entityID = entityID;
		this.playerID = playerID;
		if (deathMessage != null) this.deathMessage = deathMessage.getJsonText();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		event = readVarInt(in);
		if (event == 0) return;
		if (event == 1) {
			duration = readVarInt(in);
			entityID = in.readInt();
		} else {
			playerID = readVarInt(in);
			entityID = in.readInt();
			deathMessage = readString(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(event, out);
		if (event == 0) return;
		if (event == 1) {
			writeVarInt(duration, out);
			out.writeInt(entityID);
		} else {
			writeVarInt(playerID, out);
			out.writeInt(entityID);
			writeString(deathMessage, out);
		}
	}

	@Override
	public int getEvent() {
		return event;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public int getPlayerID() {
		return playerID;
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public String getDeathMessage() {
		return deathMessage;
	}

}
