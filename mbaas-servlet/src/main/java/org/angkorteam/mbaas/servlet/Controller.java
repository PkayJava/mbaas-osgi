package org.angkorteam.mbaas.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public abstract class Controller {

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String DELETE = "DELETE";

    public static final String PUT = "PUT";

    public static final String VIEW = "view:";

    public static final String REDIRECT = "redirect:";

    private final String method;

    private final String path;

    public Controller(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public final String getId() {
        return getMethod() + getPath();
    }

    public final String getMethod() {
        return this.method;
    }

    public final String getPath() {
        return this.path;
    }

    public boolean isSecure() {
        return false;
    }

    public boolean hasAccess(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

}
