package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;

public interface Chat {

	/**
	 * Always returns the text in legacy format.
	 * @return text in legacy
	 */
	String toLegacyText();
	
	/**
	 * Always returns the text in json format.
	 * @return text in json
	 */
	String toJsonText();
	
	/**
	 * Returns the text in json format if present otherwise it will return in legacy format
	 * @return text in json or legacy
	 */
	String toText();
	
	/**
	 * Returns the raw text without any format codes
	 * @return raw text
	 */
	String toRawText();
	
	/**
	 * Returns the text as {@link ChatComponent}
	 * @return component
	 */
	ChatComponent toComponent();
	
	/**
	 * Indicates whether or not legacy text is stored.
	 * However calling {@link #toLegacyText()} will always return legacy text
	 * @return true if legacy is stored
	 */
	boolean hasLegacy();
	
	/**
	 * Indicates whether or not legacy text is stored.
	 * However calling {@link #toJsonText()} will always return json text
	 * @return true if json is stored
	 */
	boolean hasJson();
	
}
