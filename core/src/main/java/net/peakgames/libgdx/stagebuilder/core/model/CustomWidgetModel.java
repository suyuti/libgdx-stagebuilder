package net.peakgames.libgdx.stagebuilder.core.model;

import java.util.HashMap;
import java.util.Map;

public class CustomWidgetModel extends BaseModel {
    private Map<String, String> attributeMap = new HashMap<String, String>();
    private String klass;

    public void addAttribute(String key, String value) {
        this.attributeMap.put(key, value);
    }

    public String getAttribute(String key) {
        return this.attributeMap.get(key);
    }

    public String getKlass() {
        return klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    public Map<String, String> getAttributeMap() {
        return this.attributeMap;
    }
}
