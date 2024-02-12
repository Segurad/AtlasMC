package de.atlascore.log.pattern;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

import de.atlascore.log.CoreAtlasMarker;

@Plugin(name = "CoreAtlasLoggerNameConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"al", "atlasLogger"})
public class CoreAtlasLoggerNameConverter extends LogEventPatternConverter {

	private static final CoreAtlasLoggerNameConverter INSTANCE = new CoreAtlasLoggerNameConverter();
	
	private CoreAtlasLoggerNameConverter() {
		super("CoreAtlasLoggerName", "atlasLoggerName");
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		Marker marker = event.getMarker();
		if (marker instanceof CoreAtlasMarker) {
			toAppendTo.append(marker.getName());
		}
	}
	
	public static CoreAtlasLoggerNameConverter newInstance(final String[] options) {
		return INSTANCE;
	}

}
