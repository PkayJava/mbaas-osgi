package org.angkorteam.mbaas.servlet;

import org.apache.commons.io.IOUtils;
import org.osgi.framework.Bundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class AssetController extends Controller {

    private final Bundle bundle;

    private final String resourcePrefix;

    public AssetController(Bundle bundle, String id, String resourcePrefix, String path) {
        super(id, GET, path);
        this.bundle = bundle;
        this.resourcePrefix = resourcePrefix;
    }

    public void execute(Map<String, String> pathVariables, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        URL url = this.bundle.getResource(this.resourcePrefix + request.getRequestURI());
        if (url == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try (InputStream inputStream = url.openStream()) {
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

}
