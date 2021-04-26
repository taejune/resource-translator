package k8s.util;

@FunctionalInterface
public interface FieldCondition {
    boolean check(String key, Object val);
}
