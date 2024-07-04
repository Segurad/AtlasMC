package de.atlasmc.chat;

import java.util.List;

public interface ChatChannel extends Messageable {
	
	List<Messageable> getMembers();
	
	void addMember(Messageable member);
	
	void removeMember(Messageable member);

}
