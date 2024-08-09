package de.atlasmc.command;

import de.atlasmc.command.argparser.VarArgParser;
import de.atlasmc.command.suggestion.SuggestionType;
import de.atlasmc.util.annotation.Nullable;

public class VarCommandArg extends CommandArg {

	private VarArgParser<?> parser;
	private SuggestionType suggestion;
	
	public VarCommandArg(String name) {
		super(name);
	}
	
	public VarArgParser<?> getParser() {
		return parser;
	}
	
	public void setParser(VarArgParser<?> parser) {
		this.parser = parser;
	}
	
	@Nullable
	public SuggestionType getSuggestion() {
		return suggestion;
	}
	
	public void setSuggestion(@Nullable SuggestionType suggestion) {
		this.suggestion = suggestion;
	}

}
