package k8s.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

public class ResourceConverter {
    private ArrayList<FieldHook> fieldHooks;

    public ResourceConverter() {
        this.fieldHooks = new ArrayList();
        FieldHook emptyDefaultHook = new FieldHook((String key, Object val) -> {
            return false;
        }, (Map.Entry<String, Object> entry) -> {});
        this.fieldHooks.add(emptyDefaultHook);
    }

    public ResourceConverter addFieldHook(FieldHook hook) {
        this.fieldHooks.add(hook);
        return this;
    }

    public Resource convert(Resource src) {
        Map<String, Object> data = src.getDataMap();
        iterateMap(data);
        return src;
    }

    private Map iterateMap(Map<String, Object> m) {
        for(Map.Entry<String, Object> e : m.entrySet()) {
            if(e.getValue() instanceof Map) {
                iterateMap((Map<String, Object>) e.getValue());
            } else if (e.getValue() instanceof List) {
                iterateList((List<Object>) e);
            } else if (e.getValue() instanceof String) {
                for(FieldHook h : fieldHooks) {
                    if (h.matchCondition(e)) {
                        h.run(e);
                    }
                }
            } else {

            }
        }
        return m;
    }

    private void iterateList(List<Object> l) {
        for(Object e : l) {
            if(e instanceof String) {

            } else if(e instanceof Map) {
                iterateMap((Map<String, Object>) e);
            } else if(e instanceof List){
                iterateList((List<Object>) e);
            } else {

            }
        }
    }
}
