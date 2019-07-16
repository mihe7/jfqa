# gst
Zu Anschauungszwecken (for educational purposes, only)

Aus Gründen der Rückwärtskompatibilität verarbeitet der Java-Compiler
```java
public abstract class MyGenericBaseClass<T> {}
public class MyImplementation exteneds MyGenericBaseClass {}
```
klaglos. Im Java-Forum kam die Frage auf, ob man verhindern kann, dass jemand eine generische Klasse ableitet, ohne Typparameter anzugeben.

Mit gst wird gezeigt, dass dies mittels Annotation Processing durchaus möglich ist.

Dazu wird die generische Klasse einfach mit @GenericClass (befindet sich im Package gst) annotiert, z. B. 
```java
@GenericClass
public abstract class MyGenericBaseClass<T> {}
```
Danach führt der Versuch, MyImplementation wie oben gezeigt zu implementieren, zu einem Compilerfehler.
