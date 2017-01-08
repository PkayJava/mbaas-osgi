package org.angkorteam.mbaas.servlet.layout;

import com.google.common.collect.Maps;
import org.angkorteam.mbaas.servlet.View;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public class TemplateView implements View {

    public static final String TEMPLATE = "/asset/template.vm";
    public static final String ID = TemplateView.class.getName();

    private final Bundle bundle;

    private final Map<String, String> blocks;

    public TemplateView(Bundle bundle) {
        this.bundle = bundle;
        this.blocks = Maps.newHashMap();
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public String template() {
        return TEMPLATE;
    }

    @Override
    public String parentId() {
        return null;
    }

    @Override
    public Bundle bundle() {
        return this.bundle;
    }

    @Override
    public Map<String, String> blocks() {
        return this.blocks;
    }

    @Override
    public VelocityContext velocityContext(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
