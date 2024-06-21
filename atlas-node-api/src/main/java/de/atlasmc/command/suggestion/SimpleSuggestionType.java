package de.atlasmc.command.suggestion;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;

final class SimpleSuggestionType implements SuggestionType {

	private final NamespacedKey key;
	
	SimpleSuggestionType(NamespacedKey key) {
		this.key = key;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public Future<Suggestion> getSuggestion() {
		return CompleteFuture.getNullFuture();
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		return configuration;
	}

}
