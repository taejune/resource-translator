package k8s.util;

import java.util.Map;

public class FieldHook {
    private final FieldCondition cond;
    private final FieldUpdater conv;

    public FieldHook(FieldCondition cond, FieldUpdater conv) {
        this.cond = cond;
        this.conv = conv;
    }

    public boolean matchCondition(Map.Entry<String, Object> e) {
        return cond.check(e.getKey(), e.getValue());
    }

    public void run(Map.Entry<String, Object> e) {
        conv.update(e);
    }
}
