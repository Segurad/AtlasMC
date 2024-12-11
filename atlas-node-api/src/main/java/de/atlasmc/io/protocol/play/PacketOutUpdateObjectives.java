package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore.NumberFormatType;
import de.atlasmc.scoreboard.RenderType;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_OBJECTIVES, definition = "set_objective")
public class PacketOutUpdateObjectives extends AbstractPacket implements PacketPlayOut {
	
	public String name;
	public Mode mode;
	public Chat displayName;
	public RenderType renderType;
	public NumberFormatType formatType;
	public ChatComponent numberFormat;

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_OBJECTIVES;
	}
	
	public static enum Mode {
		CREATE,
		REMOVE,
		UPDATE_DISPLAY;
		
		private static List<Mode> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Mode getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Mode> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
