package org.angkorteam.mbaas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public abstract class Controller {

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String DELETE = "DELETE";

    public static final String PUT = "PUT";

    private final String id;

    private final String method;

    private final String path;

    protected Controller(String id, String method, String path) {
        this.id = id;
        this.method = method;
        this.path = path;
    }

    public final String getId() {
        return this.id;
    }

    public final String getMethod() {
        return this.method;
    }

    public final String getPath() {
        return this.path;
    }

    public abstract String execute(Connection connection, Map<String, String> pathVariables, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
