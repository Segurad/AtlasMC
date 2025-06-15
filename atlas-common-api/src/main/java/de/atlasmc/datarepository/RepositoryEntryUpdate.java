package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.Pair;
import de.atlasmc.util.annotation.NotNull;

public interface RepositoryEntryUpdate extends Namespaced {
	
	@NotNull
	Collection<Pair<String, Change>> getFilesChanged();
	
	boolean hasFilesChanged();
	
	Change getEntryChange();
	
	public static enum Change {
		
		CHANGED('*'),
		DELETED('-'),
		CREATED('+');
		
		private char symbol;
		
		private Change(char symbol) {
			this.symbol = symbol;
		}
		
		public char symbol() {
			return symbol;
		}
		
	}

}
