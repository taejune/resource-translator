package k8s.util;

import java.util.Map;

@FunctionalInterface
public interface FieldUpdater {
    void update(Map.Entry<String, Object> entry);
}
