package org.angkorteam.mbaas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public abstract class LogicController extends Controller {

    protected LogicController(String id, String method, String path) {
        super(id, method, path);
    }

    public abstract String execute(Connection connection, Map<String, String> pathVariables, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
