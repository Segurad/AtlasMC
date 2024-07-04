package de.atlascore.log;

import de.atlasmc.log.Log;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

public class CoreAtlasConsoleFilter extends AbstractFilter {
	
	@Override
	public Result filter(LogEvent event) {
		return filterMarker(event.getMarker());
	}
	
	
	protected Result filterMarker(Marker marker) {
		if (!(marker instanceof CoreAtlasMarker))
			return Result.NEUTRAL;
		CoreAtlasMarker atlasMarker = (CoreAtlasMarker) marker;
		Log logger = atlasMarker.getLogger();
		return logger.isSendToConsole() ? Result.ACCEPT : Result.DENY;
	}

}
