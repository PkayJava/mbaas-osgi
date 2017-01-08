package org.angkorteam.mbaas.servlet.internal;

import com.google.common.base.Strings;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class AssetServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = null;
        try {
            path = req.getPathInfo();
        } catch (Throwable e) {
            LOGGER.info(e.getMessage());
        }

        String extension = FilenameUtils.getExtension(path);

        if (Strings.isNullOrEmpty(extension)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ServletContext servletContext = req.getServletContext();
        InputStream inputStream = null;
        try {
            inputStream = servletContext.getResourceAsStream("/asset" + path);
        } catch (Throwable e) {
            LOGGER.info(e.getMessage());
        }

        if (inputStream != null) {
            String contentType = "application/octet-stream";
            if (StringUtils.equalsIgnoreCase(extension, "jpg")) {
                contentType = "image/jpg";
            } else if (StringUtils.equalsIgnoreCase(extension, "png")) {
                contentType = "image/png";
            } else if (StringUtils.equalsIgnoreCase(extension, "txt")) {
                contentType = "text/plain";
            } else if (StringUtils.equalsIgnoreCase(extension, "html")) {
                contentType = "text/html";
            } else if (StringUtils.equalsIgnoreCase(extension, "gif")) {
                contentType = "image/gif";
            } else if (StringUtils.equalsIgnoreCase(extension, "mf")) {
                contentType = "text/plain";
            } else if (StringUtils.equalsIgnoreCase(extension, "inf")) {
                contentType = "text/plain";
            } else if (StringUtils.equalsIgnoreCase(extension, "java")) {
                contentType = "text/plain";
            } else if (StringUtils.equalsIgnoreCase(extension, "mp3")) {
                contentType = "audio/mpeg";
            } else if (StringUtils.equalsIgnoreCase(extension, "css")) {
                contentType = "text/css";
            } else if (StringUtils.equalsIgnoreCase(extension, "js")) {
                contentType = "application/javascript";
            } else if (StringUtils.equalsIgnoreCase(extension, "json")) {
                contentType = "application/json";
            } else if (StringUtils.equalsIgnoreCase(extension, "ico")) {
                contentType = "image/x-icon";
            } else if (StringUtils.equalsIgnoreCase(extension, "xls")) {
                contentType = "application/excel";
            } else if (StringUtils.equalsIgnoreCase(extension, "zip")) {
                contentType = "application/zip";
            } else if (StringUtils.equalsIgnoreCase(extension, "doc")) {
                contentType = "application/msword";
            }
            resp.setContentType(contentType);
            try {
                IOUtils.copy(inputStream, resp.getOutputStream());
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
