package gst;


import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("gst.GenericClass")
public class GenericClassProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        Set<? extends Element> annotated = annotations.stream()
            .flatMap(a -> roundEnv.getElementsAnnotatedWith(a).stream())
            .collect(Collectors.toSet());

        roundEnv.getRootElements().stream()
            .filter(TypeElement.class::isInstance)
            .map(TypeElement.class::cast)
            .filter(e -> e.getTypeParameters().isEmpty())
            .filter(e -> annotated.contains(((DeclaredType) e.getSuperclass()).asElement()))
            .filter(e -> ((DeclaredType) e.getSuperclass()).getTypeArguments().isEmpty())
            .forEach(e -> {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                   String.format("%s tries to implement %s without specifying type parameters",
                       e.getSimpleName().toString(),
                       ((DeclaredType) e.getSuperclass()).asElement().toString()),
                   e);
            });
        return true;
    }
}
