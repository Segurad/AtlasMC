package de.atlascore.permission;

import java.util.Collection;

import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionGroupHolder;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;

public class CorePermissionGroupHolder implements PermissionGroupHolder {

	private final ConcurrentLinkedList<PermissionGroup> groups;
	
	public CorePermissionGroupHolder() {
		this.groups = new ConcurrentLinkedList<>();
	}
	
	@Override
	public Collection<PermissionGroup> getGroups() {
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
		boolean added = false;
		final int power = group.getPower();
		while (it.hasNext()) {
			PermissionGroup next = it.next();
			if (next.getPower() < power) {
				it.addBefor(group);
				added = true;
				break;
			}
		}
		if (added)
			return;
		groups.add(group);
	}

	@Override
	public void removePermissionGroup(PermissionGroup group) {
		groups.remove(group);
	}

}
