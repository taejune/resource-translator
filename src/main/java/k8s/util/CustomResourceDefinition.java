package k8s.util;

import java.util.Map;

public class CustomResourceDefinition {
    private String apiVersion;
    private String kind;
    private Map<String, Object> metadata;
    private Map<String, Object> spec;

}
