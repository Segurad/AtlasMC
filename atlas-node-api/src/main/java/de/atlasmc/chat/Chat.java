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
	
	/**
	 * Returns the raw text without any format codes
	 * @return raw text
	 */
	public String getRawText();
	
	/**
	 * Indicates whether or not legacy text is stored.
	 * However calling {@link #getLegacyText()} will always return legacy text
	 * @return true if legacy is stored
	 */
	public boolean hasLegacy();
	
	/**
	 * Indicates whether or not legacy text is stored.
	 * However calling {@link #getJsonText()} will always return json text
	 * @return true if json is stored
	 */
	public boolean hasJson();
	
}
