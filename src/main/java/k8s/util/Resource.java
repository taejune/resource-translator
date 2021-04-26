package k8s.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.Map;

public class Resource {
    private Map<String, Object> data;

    public Resource(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            data = mapper.readValue(new File(path), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
