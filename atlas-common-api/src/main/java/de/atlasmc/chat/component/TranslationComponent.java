package de.atlasmc.chat.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

/**
 * {@link ChatComponent} that translates its contents
 * Translate can be a variable of the language file and will be replaced accordingly by the client.
 * %s can be used in combination with "with" to replace the contents of the text at this position. 
 * %% as escape to only write %
 *
 */
public class TranslationComponent extends AbstractBaseComponent<TranslationComponent> {
	
	public static final NBTCodec<TranslationComponent>
	NBT_HANDLER = NBTCodec
					.builder(TranslationComponent.class)
					.codec("key", TranslationComponent::getKey, TranslationComponent::setKey, NBTCodecs.STRING)
					.codec("fallback", TranslationComponent::getFallback, TranslationComponent::setFallback, NBTCodecs.STRING)
					.build();
	
	public static final String
	JSON_TRANSLATE = "translate",
	JSON_WITH = "with",
	JSON_FALLBACK = "fallback";
	
	private String key;
	private String fallback;
	private List<ChatComponent> with;
	
	@Override
	public ComponentType getType() {
		return ComponentType.TRANSLATABLE;
	}
	
	public String getKey() {
		return key;
	}
	
	public TranslationComponent setKey(String key) {
		this.key = key;
		return this;
	}
	
	public String getFallback() {
		return fallback;
	}
	
	public TranslationComponent setFallback(String fallback) {
		this.fallback = fallback;
		return this;
	}
	
	public List<ChatComponent> getWith() {
		if (with == null)
			with = new ArrayList<>(1);
		return with;
	}
	
	public TranslationComponent setWith(List<ChatComponent> with) {
		if (this.with == null) {
			this.with = new ArrayList<>(with);
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
