package de.atlascore.permission;

import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionGroupHolder;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;

public class CorePermissionGroupHolder implements PermissionGroupHolder {

	private final ConcurrentLinkedList<PermissionGroup> groups;
	private boolean changed;
	
	public CorePermissionGroupHolder() {
		this.groups = new ConcurrentLinkedList<>();
	}
	
	@Override
	public ConcurrentLinkedList<PermissionGroup> getGroups() {
		return groups;
	}

	@Override
	public PermissionGroup getHighestGroup() {
		PermissionGroup group = groups.getHead();
		return group;
	}

	@Override
	public synchronized void addPermissionGroup(PermissionGroup group) {
		LinkedListIterator<PermissionGroup> it = groups.iterator();
		final int power = group.getPower();
		while (it.hasNext()) {
			PermissionGroup next = it.next();
			if (next.getPower() < power) {
				it.addBefor(group);
				changed = true;
				return;
			}
		}
		groups.add(group);
		changed = true;
	}

	@Override
	public void removePermissionGroup(PermissionGroup group) {
		if (groups.remove(group))
			changed = true;
	}

	@Override
	public boolean hasGroupsChanged() {
		return changed;
	}

	@Override
	public void groupsChanged() {
		changed = false;
	}

}
