package org.angkorteam.mbaas.login.view;

import com.google.common.collect.Maps;
import org.angkorteam.mbaas.login.block.MenuView;
import org.angkorteam.mbaas.servlet.View;
import org.angkorteam.mbaas.servlet.layout.TemplateView;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public class LoginView implements View {

    public static final String TEMPLATE = "/asset/login.vm";
    public static final String ID = LoginView.class.getName();

    private final Bundle bundle;

    private final Map<String, String> blocks;

    public LoginView(Bundle bundle) {
        this.bundle = bundle;
        this.blocks = Maps.newHashMap();
        this.blocks.put("block", MenuView.ID);
        this.blocks.put("blocka", org.angkorteam.mbaas.servlet.block.MenuView.ID);
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
        return TemplateView.ID;
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
        VelocityContext velocityContext = new VelocityContext();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZ");
        velocityContext.put("server", dateFormat.format(new Date()));
        return velocityContext;
    }
}
