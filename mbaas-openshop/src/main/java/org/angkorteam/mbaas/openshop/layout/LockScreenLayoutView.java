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
 * Created by socheatkhauv on 1/14/17.
 */
public class LockScreenLayoutView extends View {

    public static final String ID = LockScreenLayoutView.class.getName();
    public static final String TEMPLATE = "/layout/lockscreen.vm";

    public LockScreenLayoutView(Bundle bundle) {
        super(bundle, ID, TEMPLATE);
    }

    @Override
    public void velocityContext(VelocityContext context, Map<String, HtmlTag> header, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {
        context.put("address", address);
    }
}
