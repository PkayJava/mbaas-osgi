package org.angkorteam.mbaas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public abstract class LogicController extends Controller {

    protected LogicController(String method, String path) {
        super(method, path);
    }

    public abstract String execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected String view(String viewId) {
        return VIEW + viewId;
    }

    protected String redirect(String path) {
        return REDIRECT + path;
    }

}
