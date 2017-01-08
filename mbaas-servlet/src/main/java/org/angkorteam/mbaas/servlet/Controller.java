package org.angkorteam.mbaas.servlet;

import org.osgi.framework.Bundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public interface Controller {

    String GET = "GET";

    String POST = "POST";

    String DELETE = "DELETE";

    String PUT = "PUT";

    String id();

    String method();

    String path();

    Bundle bundle();

    String execute(Connection connection, Map<String, String> pathVariables, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
