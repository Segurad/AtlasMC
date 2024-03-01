package de.atlasmc.command.suggestion;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

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

}
