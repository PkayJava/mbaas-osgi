package org.angkorteam.mbaas.login.block;

import org.angkorteam.mbaas.servlet.HtmlTag;
import org.angkorteam.mbaas.servlet.View;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public class MenuView extends View {

    public static final String TEMPLATE = "/asset/block.vm";

    public static final String ID = MenuView.class.getName();

    public MenuView(Bundle bundle) {
        super(bundle, ID, TEMPLATE);
    }

    @Override
    public VelocityContext velocityContext(Map<String, HtmlTag> header, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
