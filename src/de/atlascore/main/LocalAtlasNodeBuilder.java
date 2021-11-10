package de.atlascore.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import de.atlascore.atlasnetwork.CoreAtlasNetwork;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.io.protocol.ProtocolAdapter;

public class LocalAtlasNodeBuilder {
	
	private LocalAtlasNode localNode;
	private AtlasNetwork network;
	private ProtocolAdapter defaultProtocol;
	private String serverIcon;
	
	/**
	 * Inits this Builder with all Defaults
	 */
	public void initDefaults() {
		localNode = new LocalAtlasNode();
		network = new CoreAtlasNetwork();
		defaultProtocol = new CoreProtocolAdapter();
		
		File f = new File(Main.class.getResource("/assets/server_icon.png").getFile());
		byte[] data = new byte[(int) f.length()];
		try {
			FileInputStream in = new FileInputStream(f);
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverIcon = Base64.getEncoder().encodeToString(data);
	}
	
	public LocalAtlasNode build() {
		new Atlas(localNode, network);
		localNode.getProtocolAdapterHandler().setProtocol(defaultProtocol);
		return localNode;
	}

	public String getServerIcon() {
		return serverIcon;
	}

}
