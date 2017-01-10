package org.angkorteam.mbaas.servlet;

import org.apache.commons.fileupload.FileItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/10/17.
 */
public class FormFile implements Serializable {

    private Map<String, FileItem[]> file;

    public FormFile(Map<String, FileItem[]> file) {
        this.file = file;
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
