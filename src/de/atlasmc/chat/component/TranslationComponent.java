package de.atlasmc.chat.component;

import java.util.List;

import de.atlasmc.util.JsonBuffer;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * {@link ChatComponent} that translates its contents
 * Translate can be a variable of the language file and will be replaced accordingly by the client.
 * %s can be used in combination with "with" to replace the contents of the text at this position. 
 * %% as escape to only write %
 *
 */
public class TranslationComponent extends BaseComponent {
	
	public static final String
	JSON_TRANSLATE = "translate",
	JSON_WITH = "with";
	
	private String key;
	private List<ChatComponent> with;
	
	/**
	 * Creates a new TranslationComponent
	 * @param key which is translated
	 * @param with optional arguments that will be inserted at %s
	 */
	public TranslationComponent(@NotNull String key, @Nullable List<ChatComponent> with) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
		this.with = with;
	}
	
	public String getKey() {
		return key;
	}
	
	public List<ChatComponent> getWith() {
		return with;
	}
	
	public void setWith(List<ChatComponent> with) {
		this.with = with;
	}
	
	@Override
	public void addContents(JsonBuffer buff) {
		buff.appendText(JSON_TRANSLATE, key);
		if (with != null) {
			buff.beginArray(JSON_WITH);
			for (ChatComponent comp : with) {
				if (comp == null)
					continue;
				buff.beginObject(null);
				comp.addContents(buff);
				buff.endObject();
			}
			buff.endArray();
		}
		super.addContents(buff);
	}

}
