package de.atlasmc.tick;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AtlasThreadTaskManager<T> {
	
	private static final AtlasThreadTask<?>[] EMPTY = new AtlasThreadTask<?>[0];
	private static final String[] EMPTY_NAMES = new String[0];
	
	private final Map<String, AtlasThreadTask<T>> tasks;
	volatile String[] taskNames = EMPTY_NAMES;
	@SuppressWarnings("unchecked")
	volatile AtlasThreadTask<T>[] fastTasks = (AtlasThreadTask<T>[]) EMPTY;
	
	public AtlasThreadTaskManager() {
		this.tasks = new ConcurrentHashMap<>();
	}
	
	public AtlasThreadTask<T> removeTask(String name) {
		if (!tasks.containsKey(name))
			return null;
		synchronized (this) {
			if (!tasks.containsKey(name))
				return null;
			AtlasThreadTask<T> task = tasks.remove(name);
			String[] names = taskNames;
			AtlasThreadTask<T>[] tasks = fastTasks;
			if (names.length == 1) {
				taskNames = EMPTY_NAMES;
				@SuppressWarnings("unchecked")
				AtlasThreadTask<T>[] empty = (AtlasThreadTask<T>[]) EMPTY;
				fastTasks = empty;
				return task;
			}
			final int length = names.length;
			String[] newNames = new String[length - 1];
			@SuppressWarnings("unchecked")
			AtlasThreadTask<T>[] newTasks = new AtlasThreadTask[length - 1];
			for (int i = 0, j = 0; i < length; i++) {
				String taskName = names[i];
				if (taskName.equals(name))
					continue;
				newNames[j] = taskName;
				newTasks[j] = tasks[i];
				j++;
			}
			taskNames = newNames;
			fastTasks = newTasks;
			return task;
		}
	}
	
	public synchronized boolean addTask(String name, AtlasThreadTask<T> task) {
		if (tasks.containsKey(name))
			return false;
		synchronized (this) {
			if (tasks.containsKey(name))
				return false;
			tasks.put(name, task);
			String[] names = taskNames;
			AtlasThreadTask<T>[] tasks = fastTasks;
			final int length = names.length;
			String[] newNames = Arrays.copyOf(names, length + 1);
			AtlasThreadTask<T>[] newTasks = Arrays.copyOf(tasks, length + 1);
			newNames[length] = name;
			newTasks[length] = task;
			taskNames = newNames;
			fastTasks = newTasks;
			return true;
		}
	}
	
	public synchronized boolean addAfter(String after, String name, AtlasThreadTask<T> task) {
		if (tasks.containsKey(name))
			return false;
		if (!tasks.containsKey(after))
			return false;
		synchronized (this) {
			if (tasks.containsKey(name))
				return false;
			if (!tasks.containsKey(after))
				return false;
			tasks.put(name, task);
			String[] names = taskNames;
			AtlasThreadTask<T>[] tasks = fastTasks;
			final int length = names.length;
			String[] newNames = Arrays.copyOf(names, length + 1);
			AtlasThreadTask<T>[] newTasks = Arrays.copyOf(tasks, length + 1);
			boolean inserted = false;
			for (int i = 0, j = 0; i < length; i++) {
				String taskName = names[i];
				if (!inserted && taskName.equals(after)) {
					newNames[j] = taskName;
					newTasks[j] = tasks[i];
					j++;
					newNames[j] = name;
					newTasks[j] = task;
					j++;
					inserted = true;
					continue;
				}
				newNames[j] = taskName;
				newTasks[j] = tasks[i];
				j++;
			}
			taskNames = newNames;
			fastTasks = newTasks;
			return true;
		}
	}
	
	public synchronized boolean addBefore(String before, String name, AtlasThreadTask<T> task) {
		if (tasks.containsKey(name))
			return false;
		if (!tasks.containsKey(before))
			return false;
		synchronized (this) {
			if (tasks.containsKey(name))
				return false;
			if (!tasks.containsKey(before))
				return false;
			tasks.put(name, task);
			String[] names = taskNames;
			AtlasThreadTask<T>[] tasks = fastTasks;
			final int length = names.length;
			String[] newNames = Arrays.copyOf(names, length + 1);
			AtlasThreadTask<T>[] newTasks = Arrays.copyOf(tasks, length + 1);
			boolean inserted = false;
			for (int i = 0, j = 0; i < length; i++) {
				String taskName = names[i];
				if (!inserted && taskName.equals(before)) {
					newNames[j] = name;
					newTasks[j] = task;
					j++;
					inserted = true;
				}
				newNames[j] = taskName;
				newTasks[j] = tasks[i];
				j++;
			}
			taskNames = newNames;
			fastTasks = newTasks;
			return true;
		}
	}

	public AtlasThreadTask<T> getTask(String name) {
		return tasks.get(name);
	}
	
	public String getTaskName(AtlasThreadTask<T> task) {
		for (Entry<String, AtlasThreadTask<T>> entry : tasks.entrySet()) {
			if (entry.getValue() == task)
				return entry.getKey();
		}
		return null;
	}

}