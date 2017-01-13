package org.angkorteam.mbaas.servlet;

import java.util.*;

/**
 * Created by socheatkhauv on 1/10/17.
 */
public final class FormItem extends HashMap<String, String[]> {

    private boolean error = false;

    public FormItem(Map<String, String[]> items) {
        super(items);
    }

    public String put(String key, String value) {
        put(key, new String[]{value});
        return value;
    }

    public String getParameter(String name) {
        String[] values = get(name);
        return values == null || values.length == 0 ? null : values[0];
    }

    public String[] getParameterValues(String name) {
        String[] values = get(name);
        return values;
    }

    public List<String> getParameterNames() {
        return Collections.unmodifiableList(new ArrayList<>(keySet()));
    }

    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = this.error || error;
    }

    public boolean hasError(String name) {
        return containsKey(name + ".error");
    }

    public String getError(String name) {
        return getParameter(name + ".error");
    }

}
