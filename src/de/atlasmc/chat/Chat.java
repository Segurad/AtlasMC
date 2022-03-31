package de.atlasmc.chat;

public interface Chat {

	/**
	 * Always returns the text in legacy format.
	 * @return text in legacy
	 */
	public String getLegacyText();
	
	/**
	 * Always returns the text in json format.
	 * @return text in json
	 */
	public String getJsonText();
	
	/**
	 * Returns the text in json format if present otherwise it will return in legacy format
	 * @return text in json or legacy
	 */
	public String getText();
	
}
