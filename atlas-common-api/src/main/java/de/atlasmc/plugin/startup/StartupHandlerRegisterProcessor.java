package de.atlasmc.plugin.startup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;

import de.atlasmc.util.annotation.AnnotationProcessorUtils;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.MemoryConfiguration;
import de.atlasmc.util.configuration.file.YamlConfiguration;

@SupportedAnnotationTypes({"de.atlasmc.plugin.startup.StartupHandlerRegister"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class StartupHandlerRegisterProcessor extends AbstractProcessor {

	private Configuration startup;
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Messager log = processingEnv.getMessager();
		if (roundEnv.processingOver()) {
			log.printMessage(Kind.NOTE, "Finished processing startup handler register annotation");
			writeProcessorFile();
			return true;
		}
		log.printMessage(Kind.NOTE, "Processing startup handler reguster annotation");
		for (TypeElement annotation : annotations) {
			for (Element ele : roundEnv.getElementsAnnotatedWith(annotation)) {
				Collection<AnnotationMirror> annotationMirrors = AnnotationProcessorUtils.getAnnotationMirrorsByType(ele, annotation);
				String type = ele.toString();
				for (AnnotationMirror mirror : annotationMirrors) {
					Map<String, Object> values = AnnotationProcessorUtils.getAnnotationValues(mirror, processingEnv);
					Object value = values.get("value");
					List<String> stages = AnnotationProcessorUtils.asStringList(value);
					for (String stage : stages) {
						stage = stage.substring(1, stage.length() - 1);
						List<String> handlers = startup.getStringList(stage);
						if (handlers == null) {
							handlers = new ArrayList<>();
							startup.set(stage, handlers);
						}
						handlers.add(type);
						log.printMessage(Kind.NOTE, "Found handler: " + type + " for stage " + stage);
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		startup = new MemoryConfiguration();
	}
	
	private void writeProcessorFile() {
		if (startup.getKeys().isEmpty())
			return;
		Filer filter = processingEnv.getFiler();
		String path = "META-INF/atlas/startup-handlers.yml";
		YamlConfiguration startupCfg = null;
		try {
			FileObject file = filter.getResource(StandardLocation.CLASS_OUTPUT, "", path);
			InputStream in = file.openInputStream();
			startupCfg = YamlConfiguration.loadConfiguration(in);
		} catch(IOException e) {
			// no file present
		}
		if (startupCfg == null) {
			startupCfg = new YamlConfiguration();
		}
		boolean changes = false;
		for (String k : startup.getKeys()) {
			List<String> handlers = startup.getStringList(k);
			List<String> presentHandlers = startupCfg.getStringList(k);
			if (presentHandlers == null || presentHandlers.isEmpty()) {
				startupCfg.set(k, handlers);
				changes = true;
				continue;
			}
			for (String v : handlers) {
				if (presentHandlers.contains(v))
					continue;
				presentHandlers.add(v);
				changes = true;
			}
		}
		if (!changes)
			return;
		try {
			FileObject file = filter.createResource(StandardLocation.CLASS_OUTPUT, "", path);
			OutputStream out = file.openOutputStream();
			startupCfg.save(out);
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, "Unable to create " + path + ", " + e);
			return;
		}
		processingEnv.getMessager().printMessage(Kind.NOTE, "Startup handlers written");
	}

}
