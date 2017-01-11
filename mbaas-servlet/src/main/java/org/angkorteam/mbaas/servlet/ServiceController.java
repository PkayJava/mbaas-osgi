package org.angkorteam.mbaas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/11/17.
 */
public abstract class ServiceController extends Controller {

    public ServiceController(String method, String path) {
        super(method, path);
    }

    public abstract void execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
