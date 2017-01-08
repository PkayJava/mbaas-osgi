package org.angkorteam.mbaas.servlet;

import java.util.Map;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public final class ViewMapping {

    private final String id;

    private final String template;

    private final String parentId;

    private final Map<String, String> blocks;

    private final View view;


    public ViewMapping(String id, String template, String parentId, Map<String, String> blocks, View view) {
        this.id = id;
        this.template = template;
        this.parentId = parentId;
        this.blocks = blocks;
        this.view = view;
    }

    public String getId() {
        return id;
    }

    public String getTemplate() {
        return template;
    }

    public String getParentId() {
        return parentId;
    }

    public Map<String, String> getBlocks() {
        return blocks;
    }

    public View getView() {
        return view;
    }
}
