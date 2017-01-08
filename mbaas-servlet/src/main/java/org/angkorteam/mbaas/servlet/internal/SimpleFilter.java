package org.angkorteam.mbaas.servlet.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by socheatkhauv on 1/6/17.
 */
public class SimpleFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFilter.class);

    private ServletContext servletContext;

    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("simple filter init");
        this.servletContext = filterConfig.getServletContext();
        LOGGER.info("servlet context code {}", this.servletContext.hashCode());
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("filter {}", System.currentTimeMillis());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
