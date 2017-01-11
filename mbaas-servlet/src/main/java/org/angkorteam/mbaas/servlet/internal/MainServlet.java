package org.angkorteam.mbaas.servlet.internal;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.angkorteam.mbaas.servlet.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
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

    private DiskFileItemFactory factory = new DiskFileItemFactory();

    private Map<String, ControllerMapping> cached;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Velocity.init();
    }

    public MainServlet(DataSource dataSource,
                       Map<String, ControllerMapping> controllerDictionary,
                       Map<String, ViewMapping> viewDictionary,
                       Map<String, ControllerMapping> cached) {
        this.factory.setRepository(FileUtils.getTempDirectory());
        this.dataSource = dataSource;
        this.cached = cached;
        this.controllerDictionary = controllerDictionary;
        this.viewDictionary = viewDictionary;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> item = Maps.newHashMap();
        Map<String, FileItem[]> file = Maps.newHashMap();
        if (StringUtils.startsWithIgnoreCase(req.getContentType(), "application/x-www-form-urlencoded")) {
            try (InputStream inputStream = req.getInputStream()) {
                String raws = IOUtils.toString(inputStream, "UTF-8");
                String[] params = StringUtils.split(raws, '&');
                if (params != null && params.length > 0) {
                    for (String temp : params) {
                        String[] param = StringUtils.split(temp, '=');
                        String name = null;
                        String value = null;
                        if (param != null) {
                            if (param.length > 0) {
                                name = URLDecoder.decode(param[0], "UTF-8");
                            }
                            if (param.length > 1) {
                                value = URLDecoder.decode(param[1], "UTF-8");
                            }
                        }
                        if (!Strings.isNullOrEmpty(name)) {
                            if (!item.containsKey(name)) {
                                item.put(name, new String[]{});
                            }
                            if (!Strings.isNullOrEmpty(value)) {
                                String[] values = item.get(name);
                                String[] newValues = Arrays.copyOf(values, values.length + 1);
                                newValues[newValues.length - 1] = value;
                                item.put(name, newValues);
                            }
                        }
                    }
                }
            }
        } else if (ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(req);
            } catch (FileUploadException e) {
                throw new IOException(e);
            }
            if (items != null && !items.isEmpty()) {
                for (FileItem temp : items) {
                    if (temp.isFormField()) {
                        String name = temp.getFieldName();
                        String value = temp.getString();
                        if (!Strings.isNullOrEmpty(name)) {
                            if (!item.containsKey(name)) {
                                item.put(name, new String[]{});
                            }
                            if (!Strings.isNullOrEmpty(value)) {
                                String[] values = item.get(name);
                                String[] newValues = Arrays.copyOf(values, values.length + 1);
                                newValues[newValues.length - 1] = value;
                                item.put(name, newValues);
                            }
                        }
                    } else {
                        String name = temp.getFieldName();
                        FileItem value = temp;
                        if (!Strings.isNullOrEmpty(name)) {
                            if (!item.containsKey(name)) {
                                file.put(name, new FileItem[]{});
                            }
                            if (value.getSize() > 0) {
                                FileItem[] values = file.get(name);
                                FileItem[] newValues = Arrays.copyOf(values, values.length + 1);
                                newValues[newValues.length - 1] = value;
                                file.put(name, newValues);
                            }
                        }
                    }
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        QueryString queryString = new QueryString(req);
        FormItem formItem = new FormItem(item);
        FormFile formFile = new FormFile(file);
        String address = "";
        doExecute(LogicController.POST, address, queryString, formItem, formFile, null, req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryString queryString = new QueryString(req);
        String address = "";
        doExecute(LogicController.GET, address, queryString, null, null, null, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StringUtils.startsWithIgnoreCase(req.getContentType(), "application/json")) {
            QueryString queryString = new QueryString(req);
            FormItem formItem = new FormItem(Maps.newHashMap());
            FormFile formFile = new FormFile(Maps.newHashMap());
            String address = "";
            File fileBody = new File(FileUtils.getTempDirectoryPath(), System.currentTimeMillis() + RandomStringUtils.randomAlphabetic(50) + ".json");
            try (FileOutputStream outputStream = FileUtils.openOutputStream(fileBody)) {
                IOUtils.copy(req.getInputStream(), outputStream);
            }
            doExecute(LogicController.PUT, address, queryString, formItem, formFile, fileBody, req, resp);
            FileUtils.deleteQuietly(fileBody);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryString queryString = new QueryString(req);
        String address = "";
        doExecute(LogicController.DELETE, address, queryString, null, null, null, req, resp);
    }

    protected void doExecute(String method, String address, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = lookupPath(request);
        if (Strings.isNullOrEmpty(path) || StringUtils.equalsIgnoreCase("/", path)) {
            path = "/index";
        }
        String cachedKey = method + ">" + path;
        ControllerMapping requestMapping = this.cached.get(cachedKey);
        String[] segments = StringUtils.split(path, "/");
        if (requestMapping == null) {
            requestMapping = lookupRequestMapping(method, path);
        }
        if (requestMapping == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        this.cached.put(cachedKey, requestMapping);

        Map<String, String> pathVariables = Maps.newHashMap();
        String[] candidateSegments = StringUtils.split(requestMapping.getPath(), '/');
        for (int i = 0; i < candidateSegments.length; i++) {
            String candidateSegment = candidateSegments[i];
            if (StringUtils.startsWithIgnoreCase(candidateSegment, "{") && StringUtils.endsWithIgnoreCase(candidateSegment, "}")) {
                String name = StringUtils.substring(candidateSegment, 1, StringUtils.length(candidateSegment) - 1);
                pathVariables.put(name, segments[i]);
            }
        }

        Map<String, HtmlTag> header = Maps.newHashMap();

        Controller controller = requestMapping.getController();
        LOGGER.info("controller path {}", controller.getPath());

        boolean granted;
        if (!controller.isSecure()) {
            granted = true;
        } else {
            granted = controller.hasAccess(request, response);
        }

        if (!granted) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (controller instanceof LogicController) {
            try (Connection connection = this.dataSource.getConnection()) {
                connection.setAutoCommit(false);
                try {
                    String meta = ((LogicController) controller).execute(connection, address, pathVariables, queryString, formItem, formFile, fileBody, request, response);
                    if (Strings.isNullOrEmpty(meta)) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return;
                    }
                    if (StringUtils.startsWithIgnoreCase(meta, Controller.VIEW)) {
                        String viewId = StringUtils.substring(meta, Controller.VIEW.length(), meta.length());
                        response.setContentType("text/html");
                        ViewMapping view = this.viewDictionary.get(viewId);
                        if (view == null) {
                            LOGGER.debug("view id {} is not found in the registry", viewId);
                        }
                        LOGGER.info("view id {}", viewId);
                        if (!Strings.isNullOrEmpty(view.getParentId())) {
                            LOGGER.info("view parent id {}", view.getParentId());
                            StringWriter writer = processView(header, view, connection, address, pathVariables, queryString, formItem, request, response);
                            response.getWriter().write(writer.getBuffer().toString());
                        } else {
                            VelocityContext velocityContext = view.getView().velocityContext(header, connection, address, pathVariables, queryString, formItem, request, response);
                            BundleTemplate template = new BundleTemplate(view.getView());
                            template.merge(velocityContext, response.getWriter());
                        }
                    } else if (StringUtils.startsWithIgnoreCase(meta, Controller.REDIRECT)) {
                        String redirect = StringUtils.substring(meta, Controller.REDIRECT.length(), meta.length());
                        response.sendRedirect(redirect);
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.debug("{} > {} : due to this reason {}", method + StringUtils.repeat(" ", 6 - method.length()), controller.getPath(), e.getMessage());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } finally {
                    connection.rollback();
                }
            } catch (SQLException e) {
                LOGGER.debug("could open connection due to this reason {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (controller instanceof AssetController) {
            ((AssetController) controller).execute(address, pathVariables, queryString, request, response);
        } else if (controller instanceof ServiceController) {
            try (Connection connection = this.dataSource.getConnection()) {
                connection.setAutoCommit(false);
                try {
                    ((ServiceController) controller).execute(connection, address, pathVariables, queryString, formItem, formFile, fileBody, request, response);
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.debug("{} > {} : due to this reason {}", method + StringUtils.repeat(" ", 6 - method.length()), controller.getPath(), e.getMessage());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } finally {
                    connection.rollback();
                }
            } catch (SQLException e) {
                LOGGER.debug("could open connection due to this reason {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    protected StringWriter processView(Map<String, HtmlTag> header, ViewMapping view, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {

        ViewMapping parentView = null;
        if (!Strings.isNullOrEmpty(view.getParentId())) {
            parentView = this.viewDictionary.get(view.getParentId());
            if (parentView == null) {
                LOGGER.debug("parent id {} is not found in the registry", view.getParentId());
            }
            if (!Strings.isNullOrEmpty(parentView.getParentId())) {
                return processView(header, this.viewDictionary.get(parentView.getParentId()), connection, address, pathVariables, queryString, formItem, request, response);
            }
        }

        VelocityContext parentVelocityContext = null;
        if (parentView != null) {
            parentVelocityContext = parentView.getView().velocityContext(header, connection, address, pathVariables, queryString, formItem, request, response);
        }
        VelocityContext velocityContext = view.getView().velocityContext(header, connection, address, pathVariables, queryString, formItem, request, response);
        StringWriter writer = new StringWriter();
        {
            if (velocityContext == null) {
                velocityContext = new VelocityContext();
            }

            buildBlock(header, velocityContext, view, connection, address, pathVariables, queryString, formItem, request, response);

            Template template = new BundleTemplate(view.getView());
            template.merge(velocityContext, writer);
        }

        if (parentView != null) {
            if (parentVelocityContext == null) {
                parentVelocityContext = new VelocityContext();
            }

            buildBlock(header, parentVelocityContext, parentView, connection, address, pathVariables, queryString, formItem, request, response);

            StringWriter parentWriter = new StringWriter();

            Template template = new BundleTemplate(parentView.getView());

            parentVelocityContext.put("child", writer.getBuffer());

            template.merge(parentVelocityContext, parentWriter);

            return parentWriter;
        } else {
            return writer;
        }
    }

    protected void buildBlock(Map<String, HtmlTag> header, VelocityContext velocityContext, ViewMapping view, Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> blocks = view.getView().getBlocks();
        if (!blocks.isEmpty()) {
            for (Map.Entry<String, String> block : blocks.entrySet()) {
                String name = block.getKey();
                String viewId = block.getValue();
                ViewMapping blockView = this.viewDictionary.get(viewId);
                if (blockView == null) {
                    LOGGER.debug("block id {} is not found in registry", viewId);
                } else {
                    LOGGER.info("symbolicName {} template {}", blockView.getView().getBundle().getSymbolicName(), blockView.getTemplate());
                    VelocityContext blockVelocityContext = blockView.getView().velocityContext(header, connection, address, pathVariables, queryString, formItem, request, response);
                    if (blockVelocityContext == null) {
                        blockVelocityContext = new VelocityContext();
                    }
                    StringWriter blockWriter = new StringWriter();
                    Template blockTemplate = new BundleTemplate(blockView.getView());
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
