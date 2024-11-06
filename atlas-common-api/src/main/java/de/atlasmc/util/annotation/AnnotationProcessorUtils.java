package de.atlasmc.util.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;

public class AnnotationProcessorUtils {
	
	public static Map<String, Object> getAnnotationValues(AnnotationMirror mirror, ProcessingEnvironment processingEnv) {
		Map<String, Object> values = new HashMap<>();
		Elements elements = processingEnv.getElementUtils();
		Map<? extends ExecutableElement, ? extends AnnotationValue> rawValues = elements.getElementValuesWithDefaults(mirror);
		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : rawValues.entrySet()) {
			String key = entry.getKey().getSimpleName().toString();
			Object value = entry.getValue().getValue();
			//System.out.println(k.getSimpleName().toString() + " : " + v.getValue());
			values.put(key, value);
		}
		return values;
	}
	
    public static Collection<AnnotationMirror> getAnnotationMirrorsByType(Element element, TypeElement targetAnnotation) {
    	return getAnnotationMirrorsByType(element, targetAnnotation, new ArrayList<>());
    }
	
    public static Collection<AnnotationMirror> getAnnotationMirrorsByType(Element element, TypeElement targetAnnotation, List<AnnotationMirror> buffer) {
        List<AnnotationMirror> matchingAnnotations = new ArrayList<>();
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            DeclaredType declaredType = annotationMirror.getAnnotationType();
            if (declaredType.asElement().equals(targetAnnotation)) {
                matchingAnnotations.add(annotationMirror);
            }
        }
        return matchingAnnotations;
    }
    
    public static String[] asStringArray(Object value) {
		String[] array;
		if (value instanceof List) {
		    List<?> list = (List<?>) value;
		    array = list.stream().map(Object::toString).toArray(String[]::new);
		} else if (value instanceof String[]) {
		    array = (String[]) value;
		} else {
		    throw new IllegalArgumentException("Unsupported annotation value type: " + (value != null ? value.getClass() : "null"));
		}
		return array;
    }

}
