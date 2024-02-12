package de.atlasmc.block.tile;

import java.util.UUID;

public interface Skull extends TileEntity {
	
	public String getTextureSignature();
	
	/**
	 * Private Key of Mojang-Server (Yggdrasil) send as Base64 String<br>
	 * Only if required by the Texture
	 * @param signature
	 */
	public void setTextureSignature(String signature);
	
	public String getTextureValue();
	
	/**
	 * Textures (Skin and Cape) as JSON-Object encoded in Base64<br>
	 * Usually the skin server url has to be <u>http://textures.minecraft.net/texture/</u> 
	 * @param value
	 */
	public void setTextureValue(String value);
	
	public UUID getPlayerUUID();
	
	public void setPlayerUUID(UUID uuid);
	
	public String getPlayerName();
	
	public void setPlayerName(String name);
	
}
