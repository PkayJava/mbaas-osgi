package org.angkorteam.mbaas.openshop.page;

import org.angkorteam.mbaas.openshop.layout.RegisterLayoutView;
import org.angkorteam.mbaas.servlet.FormItem;
import org.angkorteam.mbaas.servlet.HtmlTag;
import org.angkorteam.mbaas.servlet.QueryString;
import org.angkorteam.mbaas.servlet.View;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/12/17.
 */
public class RegisterPageView extends View {

    public static final String TEMPLATE = "/page/register.vm";
    public static final String ID = RegisterPageView.class.getName();

    public RegisterPageView(Bundle bundle) {
        super(RegisterLayoutView.ID, bundle, ID, TEMPLATE);
    }

    @Override
    public VelocityContext velocityContext(Map<String, HtmlTag> header, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("address", address);
        velocityContext.put("queryString", request.getQueryString() == null ? "" : "?" + request.getQueryString());
        velocityContext.put("request", request);
        request.setAttribute("fullname", "Socheat KHAUV");
        request.setAttribute("email", "pkayjava@gmail.com");
        request.setAttribute("password", "123123a");
        request.setAttribute("retypePassword", "123123a");
        request.setAttribute("agreement", "checked=\"checked\"");
        return velocityContext;
    }
}
