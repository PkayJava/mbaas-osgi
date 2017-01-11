package org.angkorteam.mbaas.login.page;

import org.angkorteam.mbaas.login.layout.WebsiteLayoutView;
import org.angkorteam.mbaas.servlet.FormItem;
import org.angkorteam.mbaas.servlet.HtmlTag;
import org.angkorteam.mbaas.servlet.QueryString;
import org.angkorteam.mbaas.servlet.View;
import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/11/17.
 */
public class IndexPageView extends View {

    public static final String TEMPLATE = "/page/index.vm";
    public static final String ID = LoginPageView.class.getName();

    public IndexPageView(Bundle bundle) {
        super(WebsiteLayoutView.ID, bundle, ID, TEMPLATE);
    }

    @Override
    public VelocityContext velocityContext(Map<String, HtmlTag> header, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {
        VelocityContext velocityContext = new VelocityContext();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZ");
        velocityContext.put("server", dateFormat.format(new Date()));
        return velocityContext;
    }
}