package org.angkorteam.mbaas.servlet;

import com.google.common.collect.Maps;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public abstract class View {

    private final Bundle bundle;

    private final String id;

    private final String parentId;

    private final String template;

    protected final Map<String, String> blocks = Maps.newHashMap();

    protected View(Bundle bundle, String id, String template) {
        this.bundle = bundle;
        this.id = id;
        this.template = template;
        this.parentId = null;
    }

    protected View(String parentId, Bundle bundle, String id, String template) {
        this.bundle = bundle;
        this.parentId = parentId;
        this.id = id;
        this.template = template;
    }

    public final String getId() {
        return this.id;
    }

    public final String getTemplate() {
        return this.template;
    }

    public final String getParentId() {
        return this.parentId;
    }

    public final Bundle getBundle() {
        return this.bundle;
    }

    public final Map<String, String> getBlocks() {
        return Collections.unmodifiableMap(this.blocks);
    }

    public abstract VelocityContext velocityContext(Map<String, HtmlTag> header, HttpServletRequest request, HttpServletResponse response);

}
