package org.angkorteam.mbaas.servlet.block;

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
 * Created by socheatkhauv on 1/8/17.
 */
public class MenuView extends View {

    public static final String ID = MenuView.class.getName();
    public static final String TEMPLATE = "/asset/menu.vm";

    public MenuView(Bundle bundle) {
        super(bundle, ID, TEMPLATE);
    }

    @Override
    public VelocityContext velocityContext(Map<String, HtmlTag> header, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
