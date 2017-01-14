package org.angkorteam.mbaas.openshop.layout;

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
public class RegisterLayoutView extends View {

    public static final String TEMPLATE = "/layout/register.vm";
    public static final String ID = RegisterLayoutView.class.getName();

    public RegisterLayoutView(Bundle bundle) {
        super(bundle, ID, TEMPLATE);
    }

    @Override
    public void velocityContext(VelocityContext context, Map<String, HtmlTag> header, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {
        context.put("address", address);
    }
}
