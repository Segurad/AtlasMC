package de.atlasmc.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.configuration.file.YamlConfigurationFactory;

@SupportedAnnotationTypes("de.atlasmc.event.ListenerAutoregister")
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public final class ListenerAutoregisterProcessor extends AbstractProcessor {

	public static final String RESOURCE_LOCATION = "/META-INF/atlas/autolisteners.yml";
	
	private HashSet<String> classes;
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Messager log = processingEnv.getMessager();
		if (roundEnv.processingOver()) {
			log.printMessage(Kind.NOTE, "Finished processing listener autoregister annotations");
			writeListenersFile();
			return true;
		}
		log.printMessage(Kind.NOTE, "Processing listener autoregister annotations");
		final Elements elements = processingEnv.getElementUtils();
		for (TypeElement annotation : annotations) {
			for (Element ele : roundEnv.getElementsAnnotatedWith(annotation)) {
				String type = elements.getBinaryName((TypeElement) ele).toString();
				if (classes == null) {
					classes = new HashSet<>();
				}
				classes.add(type);
				log.printMessage(Kind.NOTE, "Found listener class: " + type);
			}
		}
		return true;
	}
	
	private void writeListenersFile() {
		if (classes == null) {
			return;
		}
		Filer filer = processingEnv.getFiler();
		String path = RESOURCE_LOCATION.substring(1);
		YamlConfiguration listenersCfg = null;
		try {
			FileObject file = filer.getResource(StandardLocation.CLASS_OUTPUT, "", path);
			try (InputStream in = file.openInputStream()) {
				listenersCfg = YamlConfigurationFactory.INSTANCE.load(in);
			}
		} catch(IOException e) {
			// no file present
		}
		if (listenersCfg == null) {
			listenersCfg = new YamlConfiguration();
		}
		boolean changes = false;
		List<String> listeners = listenersCfg.getStringList("listeners");
		if (listeners == null) {
			listenersCfg.set("listeners", classes);
			changes = true;
		} else {
			for (var clazz : classes) {
				if (listeners.contains(clazz))
					continue;
				listeners.add(clazz);
				changes = true;
			}
		}
		if (!changes)
			return;
		try {
			FileObject file = filer.createResource(StandardLocation.CLASS_OUTPUT, "", path);
			OutputStream out = file.openOutputStream();
			listenersCfg.save(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, "Unable to create " + path + ", " + e);
			return;
		}
		processingEnv.getMessager().printMessage(Kind.NOTE, "Autolisteners written");
	}

}
