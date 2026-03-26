package de.atlasmc.util.mojang;

import java.util.UUID;

public record PlayerInfo(UUID uuid, String name, boolean legacy, boolean demo) {
	
}
