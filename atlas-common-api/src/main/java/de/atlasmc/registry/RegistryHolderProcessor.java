package de.atlasmc.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.atlasmc.util.annotation.AnnotationProcessorUtils;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;
import de.atlasmc.util.configuration.file.YamlConfiguration;

@SupportedAnnotationTypes("de.atlasmc.registry.RegistryHolder")
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public final class RegistryHolderProcessor extends AbstractProcessor {

	private Configuration registries;
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Messager log = processingEnv.getMessager();
		if (roundEnv.processingOver()) {
			log.printMessage(Kind.NOTE, "Finished processing registry holder annotations");
			writeRegistriesFile();
			return true;
		}
		log.printMessage(Kind.NOTE, "Processing registry holder annotations");
		final Elements elements = processingEnv.getElementUtils();
		for (TypeElement annotation : annotations) {
			for (Element ele : roundEnv.getElementsAnnotatedWith(annotation)) {
				Collection<AnnotationMirror> annotationMirrors = AnnotationProcessorUtils.getAnnotationMirrorsByType(ele, annotation);
				for (AnnotationMirror mirror : annotationMirrors) {
					Map<String, Object> values = AnnotationProcessorUtils.getAnnotationValues(mirror, processingEnv);
					String key = (String) values.get("key");
					String type = elements.getBinaryName((TypeElement) ele).toString();
					Object target = values.get("target");
					if (registries == null) {
						registries = new MemoryConfiguration();
					}
					if (registries.contains(key)) {
						ConfigurationSection other = registries.getConfigurationSection(key);
						String otherTarget = other.getString("target");
						String otherType = other.getString(type);
						String message = "Another type (" + otherType + " : " + otherTarget + ") with the given registry key is already present: " + key;
						log.printMessage(Kind.ERROR, message, ele, mirror);
					}
					//System.out.println("holder: " + key + " : " + type + " : " + target);
					ConfigurationSection registry = registries.createSection(key);
					registry.set("target", target.toString());
					registry.set("type", type);
					log.printMessage(Kind.NOTE, "Found registry: " + key + " type: " + type + " target: " + target);
				}
			}
		}
		return true;
	}
	
	private void writeRegistriesFile() {
		if (registries == null) {
			return;
		}
		Filer filer = processingEnv.getFiler();
		String path = "META-INF/atlas/registries.yml";
		YamlConfiguration registryCfg = null;
		try {
			FileObject file = filer.getResource(StandardLocation.CLASS_OUTPUT, "", path);
			InputStream in = file.openInputStream();
			registryCfg = YamlConfiguration.loadConfiguration(in);
		} catch(IOException e) {
			// no file present
		}
		if (registryCfg == null) {
			registryCfg = new YamlConfiguration();
		}
		boolean changes = false;
		for (String k : registries.getKeys()) {
			ConfigurationSection v = registries.getConfigurationSection(k);
			//System.out.println(k + " : " + v);
			String target = v.getString("target");
			String type = v.getString("type");
			ConfigurationSection registry = registryCfg.getConfigurationSection(k);
			if (registry == null) {
				registry = registryCfg.createSection(k);
				registry.set("type", type);
				registry.set("target", target);
				changes = true;
				continue;
			}
			if (target.equals(registry.get("target")) && type.equals(registry.get("type")))
				continue;
			registry.set("type", type);
			registry.set("target", target);
			changes = true;
		}
		if (!changes)
			return;
		try {
			FileObject file = filer.createResource(StandardLocation.CLASS_OUTPUT, "", path);
			OutputStream out = file.openOutputStream();
			registryCfg.save(out);
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, "Unable to create " + path + ", " + e);
			return;
		}
		processingEnv.getMessager().printMessage(Kind.NOTE, "Registries written");
	}

}
