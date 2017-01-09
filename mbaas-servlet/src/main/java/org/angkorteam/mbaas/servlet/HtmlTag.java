package org.angkorteam.mbaas.servlet;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class HtmlTag implements Serializable {

    private final String name;

    private final Map<String, String> attributes;

    private StringBuffer content;

    public HtmlTag(String name) {
        if (Strings.isNullOrEmpty(name)) {
            throw new NullPointerException();
        }
        this.name = name;
        this.attributes = Maps.newHashMap();
    }

    public void appendContent(String content) {
        if (Strings.isNullOrEmpty(content)) {
            return;
        }
        if (this.content == null) {
            this.content = new StringBuffer();
        }
        this.content.append(content);
    }

    public void clearContent() {
        this.content = null;
    }

    public void setAttribute(String name, String value) {
        if (Strings.isNullOrEmpty(name)) {
            return;
        }
        this.attributes.put(name, value == null ? "" : value);
    }

    public String removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    public String getAttribute(String name) {
        return this.attributes.get(name);
    }

    public String toHTML() {
        StringBuffer html = new StringBuffer("");
        List<String> joins = Lists.newArrayList();
        joins.add(this.name);
        if (!this.attributes.isEmpty()) {
            for (Map.Entry<String, String> attribute : this.attributes.entrySet()) {
                joins.add(attribute.getKey() + "=\"" + attribute.getValue() + "\"");
            }
        }
        if (this.content != null) {
            html.append("<").append(StringUtils.join(joins, " ")).append(">").append(this.content).append("</").append(this.name).append(">");
        } else {
            html.append("<").append(StringUtils.join(joins, " ")).append("/>");
        }
        return html.toString();
    }

}
