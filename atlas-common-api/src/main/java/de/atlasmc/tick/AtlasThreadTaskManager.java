package de.atlasmc.tick;

import de.atlasmc.util.pipeline.AbstractConcurrentPipeline;

public class AtlasThreadTaskManager<T> extends AbstractConcurrentPipeline<AtlasThreadTask<T>> {
	
	private static final AtlasThreadTask<?>[] EMPTY = {};
	
	public void runTasks(AtlasThread<T> thread, String error, int tick) {
		final var entries = this.entries;
		final int length = entries.length;
		if (length == 0)
			return;
		final var names = this.names;
		final T context = thread.getContext();
		for (int i = 0; i < length; i++) {
			final var task = entries[i];
			try {
				task.run(context, tick);
			} catch (Exception e) {
				var taskname = names.length == length ? names[i] : getEntryName(null, task);
				thread.getLogger().error(error + taskname, e);
			}
		}
	}
	
	@Override
	protected String getEntryName(String name, AtlasThreadTask<T> entry) {
		return name != null ? name : "Task-" + entry.getClass().getName();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected AtlasThreadTask<T>[] getEmpty() {
		return (AtlasThreadTask<T>[]) EMPTY;
	}

}