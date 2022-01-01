package de.atlascore.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.text.html.HTML.Tag;

import de.atlasmc.Atlas;
import de.atlasmc.Material;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.entity.EntityType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.factory.MetaDataFactory;
import de.atlasmc.io.protocol.ProtocolAdapter;

public class CoreNodeBuilder {
	
	private LocalAtlasNode localNode;
	private AtlasNetwork network;
	private ProtocolAdapter defaultProtocol;
	private List<ProtocolAdapter> protAdapter;
	private String serverIcon;
	private int port;
	private Consumer<CoreModulPlugin>[] initOrder; 
	
	public void initDefaults() {
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
	
	public void initByCoreModuls(List<CoreModulPlugin> plugins) {
		List<Class<?>> classes = List.of(
				Material.class,
				MetaDataFactory.class,
				EntityType.class,
				Tag.class,
				ContainerFactory.class
		);
		for (Class<?> clazz : classes)
			for (CoreModulPlugin plugin : plugins)
				plugin.initBaseFields(clazz);
		for (CoreModulPlugin plugin : plugins) {
			plugin.addTypes();
			plugin.initNode(this);
		}
	}
	
	public LocalAtlasNode build() {
		new Atlas(localNode, network);
		localNode.getProtocolAdapterHandler().setProtocol(defaultProtocol);
		return localNode;
	}

	public String getServerIcon() {
		return serverIcon;
	}

	public int getPort() {
		return port;
	}

}
