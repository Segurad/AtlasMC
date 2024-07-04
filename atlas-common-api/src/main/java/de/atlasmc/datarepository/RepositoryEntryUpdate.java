package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.Pair;

public interface RepositoryEntryUpdate extends Namespaced {
	
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
