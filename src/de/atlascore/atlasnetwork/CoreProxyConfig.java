package de.atlascore.atlasnetwork;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.atlasmc.atlasnetwork.ProxyConfig;

public class CoreProxyConfig implements ProxyConfig {

	@Override
	public boolean isMaintenance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxPlayers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SlotDisplayMode getSlotDisplayMode() {
		return SlotDisplayMode.DYNAMIC; // TODO
	}

	@Override
	public String getProtocolText() {
		return "AtlasMC DevBuild"; // TODO
	}

	@Override
	public String[] getPlayerInfo() {
		return new String[] { // TODO
				"Segurad",
				"§6CarlHeinz"
		};
	}

	@Override
	public JsonElement getJsonMOTD() {
		JsonObject string = new JsonObject();
		string.addProperty("text", "Hallo Welt"); // TODO
		return string;
	}

	@Override
	public String getServerIconBase64() {
		// TODO Auto-generated method stub
		return "";
	}

}
