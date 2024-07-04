package de.atlasmc.chat.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.util.JsonBuffer;
import de.atlasmc.util.annotation.Nullable;

/**
 * {@link ChatComponent} that translates its contents
 * Translate can be a variable of the language file and will be replaced accordingly by the client.
 * %s can be used in combination with "with" to replace the contents of the text at this position. 
 * %% as escape to only write %
 *
 */
public class TranslationComponent extends AbstractBaseComponent<TranslationComponent> {
	
	public static final String
	JSON_TRANSLATE = "translate",
	JSON_WITH = "with";
	
	private String key;
	private List<ChatComponent> with;
	
	public TranslationComponent() {}
	
	public TranslationComponent(String key) {
		this.key = key;
	}
	
	/**
	 * Creates a new TranslationComponent
	 * @param key which is translated
	 * @param with optional arguments that will be inserted at %s
	 */
	public TranslationComponent(@Nullable String key, @Nullable List<ChatComponent> with) {
		this.key = key;
		this.with = with;
	}
	
	public String getKey() {
		return key;
	}
	
	public TranslationComponent setKey(String key) {
		this.key = key;
		return this;
	}
	
	public List<ChatComponent> getWith() {
		if (with == null)
			with = new ArrayList<>(1);
		return with;
	}
	
	public TranslationComponent setWith(List<ChatComponent> with) {
		if (this.with == null) {
			this.with = new ArrayList<ChatComponent>(with);
		} else {
			this.with.clear();
			this.with.addAll(with);
		}
		return this;
	}
	
	public TranslationComponent addWith(ChatComponent chat) {
		getWith().add(chat);
		return this;
	}
	
	public TranslationComponent addWith(ChatComponent... chat) {
		if (chat == null)
			return this;
		if (this.with == null)
			this.with = new ArrayList<>(chat.length);
		for (ChatComponent c : chat) {
			this.with.add(c);
		}
		return this;
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

	@Override
	protected TranslationComponent getThis() {
		return this;
	}
	
	@Override
	public TranslationComponent clone() {
		TranslationComponent copy = super.clone();
		if (copy == null)
			return null;
		if (with != null) {
			copy.with = new ArrayList<>(with.size());
			for (ChatComponent comp : with)
				copy.with.add(comp.clone());
		}
		return copy;
	}

}
