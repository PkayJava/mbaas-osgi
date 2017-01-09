package org.angkorteam.mbaas.layout;

import org.angkorteam.mbaas.servlet.HtmlTag;
import org.angkorteam.mbaas.servlet.View;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class LoginLayout extends View {

    public LoginLayout(Bundle bundle, String id, String template) {
        super(bundle, id, template);
    }

    @Override
    public VelocityContext velocityContext(Map<String, HtmlTag> header, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
