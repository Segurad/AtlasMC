package de.atlasmc.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.atlasmc.util.configuration.file.YamlConfiguration;

@SupportedAnnotationTypes({ 
		"de.atlasmc.registry.RegistryHolder",
		"de.atlasmc.registry.RegistryValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class RegistryAnnotationProcessor extends AbstractProcessor {

	private Map<String, String> registries;
	private Map<String, Map<String, String>> registryEntries;
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (roundEnv.processingOver()) {
			processingEnv.getMessager().printMessage(Kind.NOTE, "Finished processing registry annotations");
			writeFiles();
			return true;
		}
		processingEnv.getMessager().printMessage(Kind.NOTE, "Processing registry annotations");
		for (TypeElement annotation : annotations) {
			if (annotation.toString().equals("de.atlasmc.registry.RegistryHolder")) {
				for (Element ele : roundEnv.getElementsAnnotatedWith(annotation)) {
					List<? extends AnnotationMirror> annotationMirrors = ele.getAnnotationMirrors();
					for (AnnotationMirror mirror : annotationMirrors) {
						Map<String, Object> values = getAnnotationValues(mirror);
						String key = (String) values.get("key");
						String type = ele.toString();
						Object target = values.get("target");
						if (registries == null) {
							registries = new HashMap<>();
						}
						if (registries.containsKey(key)) {
							String message = "Another type (" + registries.get(key) + ") with the given registry key is already present: " + key;
							processingEnv.getMessager().printMessage(Kind.ERROR, message, ele, mirror);
						}
						//System.out.println("holder: " + key + " : " + type + " : " + target);
						registries.put(key, type + ":" + target);
						processingEnv.getMessager().printMessage(Kind.NOTE, "Found registry: " + key + " type: " + type + " target: " + target);
					}
				}
			} else if (annotation.toString().equals("de.atlasmc.registry.RegistryValue")) {
				for (Element ele : roundEnv.getElementsAnnotatedWith(annotation)) {
					List<? extends AnnotationMirror> annotationMirrors = ele.getAnnotationMirrors();
					for (AnnotationMirror mirror : annotationMirrors) {
						Map<String, Object> values = getAnnotationValues(mirror);
						String registry = (String) values.get("registry");
						String key = (String) values.get("key");
						String type = ele.toString();
						if (registryEntries == null) {
							registryEntries = new HashMap<>();
						}
						Map<String, String> entries = null;
						if (registryEntries.containsKey(registry)) {
							entries = registryEntries.get(registry);
						} else {
							registryEntries.put(registry, entries = new HashMap<>());
						}
						//System.out.println("value: " + key + " : " + type);
						entries.put(key, type);
						boolean isDefault = (boolean) values.get("isDefault");
						processingEnv.getMessager().printMessage(Kind.NOTE, "Found value: " + key + " type: " + type + " registry: " + registry + " default: " + isDefault);
						if (isDefault)
							entries.put("registry:default", type);
					}
				}
			}
		}
		return true;
	}
	
	private void writeFiles() {
		writeRegistriesFile();
		writeRegistryEntriesFile();
	}
	
	private void writeRegistriesFile() {
		if (registries == null || registries.isEmpty()) {
			return;
		}
		Filer filter = processingEnv.getFiler();
		String path = "META-INF/atlas/registries.yml";
		YamlConfiguration registryConfig = null;
		try {
			FileObject file = filter.getResource(StandardLocation.CLASS_OUTPUT, "", path);
			InputStream in = file.openInputStream();
			registryConfig = YamlConfiguration.loadConfiguration(in);
		} catch(IOException e) {
			// no file present
		}
		if (registryConfig == null) {
			registryConfig = new YamlConfiguration();
		}
		boolean changes = false;
		for (String k : registries.keySet()) {
			String v = registries.get(k);
			//System.out.println(k + " : " + v);
			if (!v.equals(registryConfig.set(k, v)))
				changes = true;
		}
		if (!changes)
			return;
		try {
			FileObject file = filter.createResource(StandardLocation.CLASS_OUTPUT, "", path);
			OutputStream out = file.openOutputStream();
			registryConfig.save(out);
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, "Unable to create " + path + ", " + e);
			return;
		}
		processingEnv.getMessager().printMessage(Kind.NOTE, "Registries written");
	}
	
	private void writeRegistryEntriesFile() {
		if (registryEntries == null || registryEntries.isEmpty()) {
			return;
		}
		Filer filter = processingEnv.getFiler();
		String path = "META-INF/atlas/registry-entries.yml";
		YamlConfiguration registryConfig = null;
		try {
			FileObject file = filter.getResource(StandardLocation.CLASS_OUTPUT, "", path);
			InputStream in = file.openInputStream();
			registryConfig = YamlConfiguration.loadConfiguration(in);
		} catch(IOException e) {
			// no file present
		}
		if (registryConfig == null) {
			registryConfig = new YamlConfiguration();
		}
		boolean changes = false;
		for (String k : registryEntries.keySet()) {
			Map<String, String> v = registryEntries.get(k);
			List<String> presentValues = registryConfig.getStringList(k);
			if (presentValues != null && !presentValues.isEmpty()) {
				Map<String, String> presentValuesMap = new HashMap<>();
				for (String present : presentValues) {
					String[] parts = present.split(":", 2);
					presentValuesMap.put(parts[1], parts[0]);
				}
				presentValuesMap.putAll(v);
				v = presentValuesMap;
			}
			final List<String> data = new ArrayList<>(v.size());
			v.forEach((key, type) -> {
				data.add(type + ":" + key);
			});
			registryConfig.set(k, data);
			if (!data.equals(presentValues))
				changes = true;
		}
		if (!changes)
			return;
		try {
			FileObject file = filter.createResource(StandardLocation.CLASS_OUTPUT, "", path);
			OutputStream out = file.openOutputStream();
			registryConfig.save(out);
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, "Unable to create " + path + ", " + e);
			return;
		}
		processingEnv.getMessager().printMessage(Kind.NOTE, "Registry values written");
	}
	
	private Map<String, Object> getAnnotationValues(AnnotationMirror mirror) {
		Map<String, Object> values = new HashMap<>();
		Elements elements = processingEnv.getElementUtils();
		Map<? extends ExecutableElement, ? extends AnnotationValue> rawValues = elements.getElementValuesWithDefaults(mirror);
		rawValues.forEach((k,v) -> {
			//System.out.print(k.getSimpleName().toString() + " : " + v.getValue());
			values.put(k.getSimpleName().toString(), v.getValue());
		});
		return values;
	}

}
