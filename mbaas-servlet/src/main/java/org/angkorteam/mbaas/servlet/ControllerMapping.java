package org.angkorteam.mbaas.servlet;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public final class ControllerMapping implements Serializable {

    private final int segment;

    private final String method;

    private final String path;

    private final String pathVariable;

    private final Controller controller;

    public ControllerMapping(int segment, String method, String path, String pathVariable, Controller controller) {
        this.segment = segment;
        this.method = method;
        this.path = path;
        this.pathVariable = pathVariable;
        this.controller = controller;
    }

    public int getSegment() {
        return segment;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getPathVariable() {
        return pathVariable;
    }

    public Controller getController() {
        return controller;
    }
}
