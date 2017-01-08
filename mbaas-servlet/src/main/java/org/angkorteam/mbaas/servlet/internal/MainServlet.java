package org.angkorteam.mbaas.servlet.internal;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.angkorteam.mbaas.servlet.Controller;
import org.angkorteam.mbaas.servlet.ControllerMapping;
import org.angkorteam.mbaas.servlet.ViewMapping;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainServlet.class);

    private DataSource dataSource;

    private Map<String, ControllerMapping> controllerDictionary;

    private Map<String, ViewMapping> viewDictionary;

    @Override
    public void init(ServletConfig config) throws ServletException {

        Velocity.init();
    }

    public static void main(String[] args) {
        Velocity.init();
    }

    public MainServlet(DataSource dataSource, Map<String, ControllerMapping> controllerDictionary, Map<String, ViewMapping> viewDictionary) {
        this.dataSource = dataSource;
        this.controllerDictionary = controllerDictionary;
        this.viewDictionary = viewDictionary;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(Controller.POST, req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(Controller.GET, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(Controller.PUT, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(Controller.DELETE, req, resp);
    }

    protected void doExecute(String method, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = lookupPath(request);
        String[] segments = StringUtils.split(path, "/");
        ControllerMapping requestMapping = lookupRequestMapping(method, path);
        if (requestMapping == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> pathVariables = Maps.newHashMap();
        String[] candidateSegments = StringUtils.split(requestMapping.getPath(), '/');
        for (int i = 0; i < candidateSegments.length; i++) {
            String candidateSegment = candidateSegments[i];
            if (StringUtils.startsWithIgnoreCase(candidateSegment, "{") && StringUtils.endsWithIgnoreCase(candidateSegment, "}")) {
                String name = StringUtils.substring(candidateSegment, 1, StringUtils.length(candidateSegment) - 1);
                pathVariables.put(name, segments[i]);
            }
        }

        try (Connection connection = this.dataSource.getConnection()) {
            List<AutoCloseable> closeables = Lists.newArrayList();
            Controller controller = requestMapping.getController();
            try {
                org.angkorteam.mbaas.servlet.Connection delegate = new org.angkorteam.mbaas.servlet.Connection(connection, closeables);
                String viewId = controller.execute(delegate, pathVariables, request, response);
                for (AutoCloseable closeable : closeables) {
                    try {
                        closeable.close();
                    } catch (Throwable e) {
                        LOGGER.info("could not close " + closeable.getClass().getName() + " due to this reason {}", e.getMessage());
                    }
                }

                response.setContentType("text/html");

                ViewMapping view = this.viewDictionary.get(viewId);

                if (view == null) {
                    LOGGER.info("view id {} is not found in the registry", viewId);
                }

                if (!Strings.isNullOrEmpty(view.getParentId())) {
                    StringWriter writer = processView(view, request, response);
                    response.getWriter().write(writer.getBuffer().toString());
                } else {
                    VelocityContext velocityContext = view.getView().velocityContext(request, response);
                    Template template = Velocity.getTemplate(view.getView().bundle(), view.getTemplate());
                    template.merge(velocityContext, response.getWriter());
                }

            } catch (Throwable e) {
                e.printStackTrace();
                LOGGER.info("{} > {} : due to this reason {}", method + StringUtils.repeat(" ", 6 - method.length()), controller.path(), e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            LOGGER.info("could open connection due to this reason {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected StringWriter processView(ViewMapping view, HttpServletRequest request, HttpServletResponse response) {

        ViewMapping parentView = null;
        if (!Strings.isNullOrEmpty(view.getParentId())) {
            parentView = this.viewDictionary.get(view.getParentId());
            if (parentView == null) {
                LOGGER.info("parent id {} is not found in the registry", view.getParentId());
            }
            if (!Strings.isNullOrEmpty(parentView.getParentId())) {
                return processView(this.viewDictionary.get(parentView.getParentId()), request, response);
            }
        }

        VelocityContext parentVelocityContext = null;
        if (parentView != null) {
            parentVelocityContext = parentView.getView().velocityContext(request, response);
        }
        VelocityContext velocityContext = view.getView().velocityContext(request, response);
        StringWriter writer = new StringWriter();
        {
            if (velocityContext == null) {
                velocityContext = new VelocityContext();
            }

            buildBlock(velocityContext, view, request, response);

            Template template = Velocity.getTemplate(view.getView().bundle(), view.getTemplate());
            template.merge(velocityContext, writer);
        }

        if (parentView != null) {
            if (parentVelocityContext == null) {
                parentVelocityContext = new VelocityContext();
            }

            buildBlock(parentVelocityContext, parentView, request, response);

            StringWriter parentWriter = new StringWriter();
            Template template = Velocity.getTemplate(parentView.getView().bundle(), parentView.getTemplate());
            parentVelocityContext.put("child", writer.getBuffer());
            template.merge(parentVelocityContext, parentWriter);
            return parentWriter;
        } else {
            return writer;
        }
    }

    protected void buildBlock(VelocityContext velocityContext, ViewMapping view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> blocks = view.getView().blocks();
        if (blocks != null && !blocks.isEmpty()) {
            for (Map.Entry<String, String> block : blocks.entrySet()) {
                String name = block.getKey();
                String viewId = block.getValue();
                ViewMapping blockView = this.viewDictionary.get(viewId);
                if (blockView == null) {
                    LOGGER.info("block id {} is not found in registry", viewId);
                } else {
                    VelocityContext blockVelocityContext = blockView.getView().velocityContext(request, response);
                    if (blockVelocityContext == null) {
                        blockVelocityContext = new VelocityContext();
                    }
                    StringWriter blockWriter = new StringWriter();
                    Template blockTemplate = Velocity.getTemplate(blockView.getView().bundle(), blockView.getTemplate());
                    blockTemplate.merge(blockVelocityContext, blockWriter);
                    velocityContext.put(name, blockWriter.getBuffer());
                }
            }
        }
    }

    protected ControllerMapping lookupRequestMapping(String method, String path) {
        if (StringUtils.equalsIgnoreCase(path, "/")) {
            for (Map.Entry<String, ControllerMapping> item : this.controllerDictionary.entrySet()) {
                ControllerMapping requestMapping = item.getValue();
                if (StringUtils.equalsIgnoreCase(requestMapping.getPath(), path) && StringUtils.equalsIgnoreCase(method, requestMapping.getMethod())) {
                    return requestMapping;
                }
            }
            return null;
        }
        String[] segments = StringUtils.split(path, "/");
        int segment = StringUtils.countMatches(path, '/');
        List<ControllerMapping> requestMappings = Lists.newArrayList();
        for (Map.Entry<String, ControllerMapping> item : this.controllerDictionary.entrySet()) {
            ControllerMapping requestMapping = item.getValue();
            if (requestMapping.getSegment() == segment && StringUtils.equalsIgnoreCase(method, requestMapping.getMethod())) {
                requestMappings.add(requestMapping);
            }
        }
        if (requestMappings.isEmpty()) {
            return null;
        }
        List<ControllerMapping> candidates = Lists.newArrayList(requestMappings);
        for (int index = 0; index < segment; index++) {
            List<ControllerMapping> newNameCandidates = Lists.newLinkedList();
            List<ControllerMapping> newLikeCandidates = Lists.newLinkedList();
            while (!candidates.isEmpty()) {
                ControllerMapping candidate = candidates.remove(0);
                String[] dbSegment = StringUtils.split(candidate.getPathVariable(), '/');
                if (StringUtils.equalsIgnoreCase(segments[index], dbSegment[index])) {
                    newNameCandidates.add(candidate);
                } else if (StringUtils.equalsIgnoreCase(BundleActivator.PATH, dbSegment[index])) {
                    newLikeCandidates.add(candidate);
                }
            }
            if (!newNameCandidates.isEmpty()) {
                candidates.addAll(newNameCandidates);
            } else {
                candidates.addAll(newLikeCandidates);
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }
        if (candidates.size() > 1) {
            return null;
        }
        return candidates.get(0);
    }

    protected String lookupPath(HttpServletRequest request) {
        String path = null;
        if (request.getRequestURI() == null) {
            return "/";
        } else {
            path = request.getRequestURI();
        }
        if (StringUtils.length(path) == 1 && StringUtils.equalsIgnoreCase(path, "/")) {
            return "/";
        } else {
            while (StringUtils.containsIgnoreCase(path, "//")) {
                path = StringUtils.replaceAll(path, "//", "/");
            }
        }

        if (StringUtils.length(path) == 1 && StringUtils.equalsIgnoreCase(path, "/")) {
            return "/";
        } else {
            if (StringUtils.endsWithIgnoreCase(path, "/")) {
                return StringUtils.substring(path, 0, StringUtils.length(path) - 1);
            } else {
                return path;
            }
        }
    }

}
