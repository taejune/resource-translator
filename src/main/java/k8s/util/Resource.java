package k8s.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Resource {
    Map<String, Object> data;
    ObjectMapper mapper;
    String path;

    public Resource(String path) throws Exception {
        this.path = path;
        if (path.endsWith("yaml") || path.endsWith("yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else if (path.endsWith("json")) {
            mapper = new ObjectMapper(new JsonFactory());
        } else {
            throw new IllegalArgumentException("unsupported type resource name. (must be ends with yaml, yml, json");
        }

        data = mapper.readValue(new File(path), Map.class);
    }

    public String getPath(String path) {
        return this.path;
    }

    public void writeTo(File file) throws IOException {
        mapper.writeValue(file, data);
    }

    public Map<String, Object> getDataMap() {
        return data;
    }

    public void print() {
        printMap(data, 0);
    }

    private void printMap(Map<String, Object> m, int depth) {
        for (Object key : m.keySet()) {
            Object value = m.get(key);
            if (value instanceof Map) {
                printLine(key + " : ", depth);
                printMap((Map<String, Object>) value, depth + 1);
            } else if (value instanceof List) {
                printLine(key + " : ", depth);
                printList((List<Object>) value, depth + 1);
            } else if (value instanceof String) {
                printLine(key + " : " + value, depth);
            } else {
                printLine(key + " is unhandled type: " + value, depth);
            }
        }
    }

    private void printLine(String s, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print(" ");
        }
        System.out.println(s);
    }

    private void printList(List<Object> l, int depth) {
        printLine("[", depth++);
        for (Object e : l) {
            if (e instanceof String) {
                printLine("- " + e, depth);
            } else if (e instanceof Map) {
                printMap((Map<String, Object>) e, depth);
            } else if (e instanceof List) {
                printList((List<Object>) e, depth);
            } else {
                printLine("- " + e, depth);
            }
        }
        printLine("]", --depth);
    }
}
