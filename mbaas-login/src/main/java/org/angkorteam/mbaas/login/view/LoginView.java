package org.angkorteam.mbaas.login.view;

import org.angkorteam.mbaas.login.block.MenuView;
import org.angkorteam.mbaas.servlet.HtmlTag;
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
public class LoginView extends View {

    public static final String TEMPLATE = "/asset/login.vm";
    public static final String ID = LoginView.class.getName();

    public LoginView(Bundle bundle) {
        super(TemplateView.ID, bundle, ID, TEMPLATE);
        this.blocks.put("block", MenuView.ID);
        this.blocks.put("blocka", org.angkorteam.mbaas.servlet.block.MenuView.ID);
    }

    @Override
    public VelocityContext velocityContext(Map<String, HtmlTag> header, HttpServletRequest request, HttpServletResponse response) {
        VelocityContext velocityContext = new VelocityContext();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZ");
        velocityContext.put("server", dateFormat.format(new Date()));
        return velocityContext;
    }
}
