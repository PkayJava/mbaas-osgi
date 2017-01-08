package org.angkorteam.mbaas.servlet;

import org.apache.velocity.VelocityContext;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public interface View {

    String id();

    String template();

    String parentId();

    Bundle bundle();

    Map<String, String> blocks();

    VelocityContext velocityContext(HttpServletRequest request, HttpServletResponse response);

}
