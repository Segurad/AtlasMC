package de.atlascore.log.pattern;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

import de.atlascore.log.CoreAtlasMarker;

@Plugin(name = "AtlasLoggerNamePattern", category = PatternConverter.CATEGORY)
@ConverterKeys({"al", "atlasLogger"})
public class CoreAtlasLoggerNamePattern extends LogEventPatternConverter {

	private static final CoreAtlasLoggerNamePattern INSTANCE = new CoreAtlasLoggerNamePattern();
	
	public static CoreAtlasLoggerNamePattern newInstance(final String[] options) {
		return INSTANCE;
	}
	
	private CoreAtlasLoggerNamePattern() {
		super("CoreAtlasLoggerName", null);
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		Marker marker = event.getMarker();
		if (marker instanceof CoreAtlasMarker) {
			toAppendTo.append(marker.getName());
		}
	}

}
