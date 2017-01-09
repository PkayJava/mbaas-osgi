package org.angkorteam.mbaas.servlet.internal;

import com.google.common.base.Strings;
import org.angkorteam.mbaas.servlet.AssetMapping;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class AssetServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServlet.class);

    private Map<String, AssetMapping> assetDictionary;

    public AssetServlet(Map<String, AssetMapping> assetDictionary) {
        this.assetDictionary = assetDictionary;
    }

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

        if (path.startsWith("/")) {
            path = path.substring(1);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int index = path.indexOf("/");
        String bundleId = null;
        if (index >= 0) {
            bundleId = path.substring(0, index);
            path = path.substring(index, path.length());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        AssetMapping assetMapping = this.assetDictionary.get(bundleId);
        if (assetMapping == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!StringUtils.equalsIgnoreCase(assetMapping.getPath(), path)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        URL url = assetMapping.getBundle().getResource(assetMapping.getPath());

        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
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
