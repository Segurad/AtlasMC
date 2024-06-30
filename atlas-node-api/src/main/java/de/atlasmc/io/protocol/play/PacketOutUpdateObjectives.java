package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.scoreboard.RenderType;

@DefaultPacketID(PacketPlay.OUT_UPDATE_OBJECTIVES)
public class PacketOutUpdateObjectives extends AbstractPacket implements PacketPlayOut {
	
	private String name;
	private String displayName;
	private Mode mode;
	private RenderType renderType;
	
	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Mode getMode() {
		return mode;
	}

	public RenderType getRenderType() {
		return renderType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setRenderType(RenderType renderType) {
		this.renderType = renderType;
	}

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
