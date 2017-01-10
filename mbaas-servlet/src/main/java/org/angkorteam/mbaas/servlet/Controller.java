package org.angkorteam.mbaas.servlet;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public abstract class Controller {

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String DELETE = "DELETE";

    public static final String PUT = "PUT";

    private final String id;

    private final String method;

    private final String path;

    public Controller(String id, String method, String path) {
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
}
