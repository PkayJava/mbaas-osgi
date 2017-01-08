package org.angkorteam.mbaas.servlet.internal;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Created by socheatkhauv on 1/6/17.
 */
public class SimpleServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleServlet.class);

    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("simple servlet init(config)");
        this.servletContext = config.getServletContext();
        this.servletContext.setAttribute("test", "test");
        LOGGER.info("servlet context code {}", this.servletContext.hashCode());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        Writer writer = resp.getWriter();
        writer.write("Hello from the cloud!");
        if (req.getServletContext() != null) {
            writer.write((String) req.getServletContext().getAttribute("test"));
            writer.write(this.servletContext.getClass().getName());
        }
        InputStream inputStream = this.servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
        writer.write(IOUtils.toString(inputStream, "UTF-8"));
    }
}
