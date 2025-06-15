package de.atlasmc.command.suggestion;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSerializable;

@RegistryHolder(key="atlas:command/suggestion_type", target=Target.CLASS)
public interface SuggestionType extends Namespaced, ConfigurationSerializable {
	
	public static final SuggestionType 
	/**
	 * Suggests all available recipes
	 */
	ALL_RECIPES = new SimpleSuggestionType(NamespacedKey.of("minecraft:ask_recipes")),
	/**
     * Suggests all available sounds
     */
	AVAILABLE_SOUNDS = new SimpleSuggestionType(NamespacedKey.of("minecraft:available_sounds")),
	/**
	 * Suggests all available summonable entities
	 */
	SUMMONABLE_ENTITIES = new SimpleSuggestionType(NamespacedKey.of("minecraft:summonable_entities"));
	
	Future<Suggestion> getSuggestion();

}
