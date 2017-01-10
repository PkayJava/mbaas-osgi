package org.angkorteam.mbaas.servlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/10/17.
 */
public class FormItem implements Serializable {

    private Map<String, String[]> item;

    public FormItem(Map<String, String[]> item) {
        this.item = item;
    }

    public String getParameter(String name) {
        String[] values = this.item.get(name);
        return values == null || values.length == 0 ? null : values[0];
    }

    public String[] getParameterValues(String name) {
        String[] values = this.item.get(name);
        return values;
    }

    public List<String> getParameterNames() {
        return Collections.unmodifiableList(new ArrayList<>(this.item.keySet()));
    }

    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.item);
    }

}
