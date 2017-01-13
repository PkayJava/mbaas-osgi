package org.angkorteam.mbaas.servlet;

import org.apache.commons.fileupload.FileItem;

import java.util.*;

/**
 * Created by socheatkhauv on 1/10/17.
 */
public final class FormFile extends HashMap<String, FileItem[]> {

    private Map<String, FileItem[]> file;

    public FormFile(Map<String, FileItem[]> file) {
        super(file);
    }

    public FileItem put(String key, FileItem value) {
        put(key, new FileItem[]{value});
        return value;
    }

    public FileItem getParameter(String name) {
        FileItem[] values = this.file.get(name);
        return values == null || values.length == 0 ? null : values[0];
    }

    public FileItem[] getParameterValues(String name) {
        FileItem[] values = this.file.get(name);
        return values;
    }

    public List<String> getParameterNames() {
        return Collections.unmodifiableList(new ArrayList<>(this.file.keySet()));
    }

    public Map<String, FileItem[]> getParameterMap() {
        return Collections.unmodifiableMap(this.file);
    }
}
