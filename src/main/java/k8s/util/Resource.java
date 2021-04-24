package k8s.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Resource {

    public static void main(String[] args) {
        File file = new File("sample/registry-operator/notary.yaml");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            Map<String, Object> load = mapper.readValue(file, Map.class);
            printResource(load, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResource(Map<String, Object> resource, int depth) throws Exception {
        try {
            for (Object key : resource.keySet()) {
                Object value = resource.get(key);
                if (value instanceof Map) {
                    printLine(key + " : ", depth);
                    printResource((Map<String, Object>) value, depth+1);
                } else if (value instanceof String) {
                    printLine(key + " : " + value, depth);
                } else {
                    printLine(key + " is unhandled type: " + value , depth);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void printLine(String s, int depth) {
        for(int i=0; i<depth; i++ ) {
            System.out.print(" ");
        }
        System.out.println(s);
    }
}
