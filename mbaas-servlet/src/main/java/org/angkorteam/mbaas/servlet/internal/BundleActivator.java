package org.angkorteam.mbaas.servlet.internal;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.angkorteam.mbaas.servlet.Controller;
import org.angkorteam.mbaas.servlet.ControllerMapping;
import org.angkorteam.mbaas.servlet.View;
import org.angkorteam.mbaas.servlet.ViewMapping;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.*;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.*;

public final class BundleActivator implements org.osgi.framework.BundleActivator,
        ServiceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BundleActivator.class);

    private BundleContext context;

    private ServiceRegistration<Servlet> assetServlet;

    private ServiceRegistration<Servlet> mainServlet;

    private DataSource dataSource;

    private Map<String, ControllerMapping> controllerDictionary;

    private Map<String, ViewMapping> viewDictionary;

    public static final List<Character> CHARACTERS = new ArrayList<>();
    public static final List<Character> NUMBERS = new ArrayList<>();
    public static final List<Character> CURLLY_BRACES = new ArrayList<>();
    public static final String PATH = "{s}";

    static {
        CURLLY_BRACES.add('{');
        CURLLY_BRACES.add('}');
        CHARACTERS.add('a');
        CHARACTERS.add('b');
        CHARACTERS.add('c');
        CHARACTERS.add('d');
        CHARACTERS.add('e');
        CHARACTERS.add('f');
        CHARACTERS.add('g');
        CHARACTERS.add('h');
        CHARACTERS.add('i');
        CHARACTERS.add('j');
        CHARACTERS.add('k');
        CHARACTERS.add('l');
        CHARACTERS.add('m');
        CHARACTERS.add('n');
        CHARACTERS.add('o');
        CHARACTERS.add('p');
        CHARACTERS.add('q');
        CHARACTERS.add('r');
        CHARACTERS.add('s');
        CHARACTERS.add('t');
        CHARACTERS.add('u');
        CHARACTERS.add('v');
        CHARACTERS.add('x');
        CHARACTERS.add('w');
        CHARACTERS.add('y');
        CHARACTERS.add('z');
        CHARACTERS.add('-');
        NUMBERS.add('0');
        NUMBERS.add('1');
        NUMBERS.add('2');
        NUMBERS.add('3');
        NUMBERS.add('4');
        NUMBERS.add('5');
        NUMBERS.add('6');
        NUMBERS.add('7');
        NUMBERS.add('8');
        NUMBERS.add('9');
    }

    public void start(BundleContext context) throws Exception {
        this.context = context;
        this.controllerDictionary = Maps.newHashMap();
        this.viewDictionary = Maps.newHashMap();
        context.addServiceListener(this);

        {
            ServiceReference<DataSourceFactory> reference = context.getServiceReference(DataSourceFactory.class);
            DataSourceFactory service = context.getService(reference);
            Properties properties = new Properties();
            properties.setProperty(DataSourceFactory.JDBC_URL, "jdbc:mysql://localhost/goldenbird_demo?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&serverTimezone=UTC");
            properties.setProperty(DataSourceFactory.JDBC_USER, "root");
            properties.setProperty(DataSourceFactory.JDBC_PASSWORD, "password");
            this.dataSource = service.createDataSource(properties);
        }

        {
            Dictionary<String, Object> props = new Hashtable<>();
            props.put("alias", "/asset");
            props.put("servlet-name", "Asset Servlet");
            this.assetServlet = context.registerService(Servlet.class, new AssetServlet(), props);
        }

        {
            Dictionary<String, Object> props = new Hashtable<>();
            props.put("alias", "/");
            props.put("servlet-name", "Main Servlet");
            this.mainServlet = context.registerService(Servlet.class, new MainServlet(this.dataSource, this.controllerDictionary, this.viewDictionary), props);
        }

        {
            Dictionary<String, Object> props = new Hashtable<>();
            String[] urls = {"/whiteboard"};
            props.put("filter-name", "My Crazy Filter");
            props.put("urlPatterns", urls);
            context.registerService(Filter.class, new SimpleFilter(), props);
        }
    }

    public void stop(BundleContext context) throws Exception {
        this.assetServlet.unregister();
        this.assetServlet = null;
        this.mainServlet.unregister();
        this.mainServlet = null;
    }

    public boolean isValid(String method, String path) {
        if (Strings.isNullOrEmpty(path)) {
            return false;
        }
        if (StringUtils.length(path) == 1 && StringUtils.equalsIgnoreCase(path, "/")) {
            return true;
        }

        String pathVariable = null;
        List<String> segmentNames = Lists.newArrayList();
        if (!StringUtils.startsWithIgnoreCase(path, "/")
                || StringUtils.endsWithIgnoreCase(path, "/")
                || StringUtils.startsWithIgnoreCase(path, "/system")
                || StringUtils.startsWithIgnoreCase(path, "/features")
                || StringUtils.startsWithIgnoreCase(path, "/gogo")
                || StringUtils.startsWithIgnoreCase(path, "/instance")
                || StringUtils.startsWithIgnoreCase(path, "/asset")) {
            return false;
        }

        for (int i = 1; i < path.length(); i++) {
            char ch = path.charAt(i);
            if (ch == '/' || ch == '_' || CURLLY_BRACES.contains(ch) || CHARACTERS.contains(ch) || NUMBERS.contains(ch)) {
            } else {
                return false;
            }
        }

        String[] segments = StringUtils.split(path, "/");
        List<String> newSegments = Lists.newLinkedList();
        for (String segment : segments) {
            if (!Strings.isNullOrEmpty(segment)) {
                if (StringUtils.startsWithIgnoreCase(segment, "{") && StringUtils.endsWithIgnoreCase(segment, "}")) {
                    String name = segment.substring(1, segment.length() - 1);
                    if (StringUtils.containsIgnoreCase(name, "{") || StringUtils.containsIgnoreCase(name, "}")) {
                        return false;
                    } else {
                        if (segmentNames.contains(name)) {
                            return false;
                        } else {
                            segmentNames.add(name);
                        }
                    }
                    newSegments.add(PATH);
                } else {
                    if (StringUtils.containsIgnoreCase(segment, "{") || StringUtils.containsIgnoreCase(segment, "}")) {
                        return false;
                    }
                    newSegments.add(segment);
                }
            }
        }
        pathVariable = "/" + StringUtils.join(newSegments, "/");

        if (!Strings.isNullOrEmpty(pathVariable) && !Strings.isNullOrEmpty(method)) {
            for (Map.Entry<String, ControllerMapping> item : this.controllerDictionary.entrySet()) {
                ControllerMapping requestMapping = item.getValue();
                if (StringUtils.equalsIgnoreCase(pathVariable, requestMapping.getPathVariable()) && StringUtils.equalsIgnoreCase(method, requestMapping.getMethod())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void serviceChanged(ServiceEvent event, Controller controller) {
        int eventType = event.getType();

        if (eventType == ServiceEvent.UNREGISTERING) {
            this.controllerDictionary.remove(controller.id());
            return;
        }

        if (eventType == ServiceEvent.MODIFIED) {
            this.controllerDictionary.remove(controller.id());
            eventType = ServiceEvent.REGISTERED;
        }

        if (eventType == ServiceEvent.REGISTERED) {
            boolean valid = isValid(controller.method(), controller.path());
            if (valid) {
                String[] segments = StringUtils.split(controller.path(), "/");
                List<String> newSegments = Lists.newLinkedList();
                for (String segment : segments) {
                    if (!Strings.isNullOrEmpty(segment)) {
                        if (StringUtils.startsWithIgnoreCase(segment, "{") && StringUtils.endsWithIgnoreCase(segment, "}")) {
                            newSegments.add(PATH);
                        } else {
                            newSegments.add(segment);
                        }
                    }
                }
                String pathVariable = "/" + StringUtils.join(newSegments, "/");
                int segment = StringUtils.countMatches(controller.path(), '/');
                ControllerMapping controllerMapping = new ControllerMapping(segment, controller.method(), controller.path(), pathVariable, controller);
                LOGGER.info("register {} {} {}", controller.id(), controller.path(), controller.getClass().getName());
                this.controllerDictionary.put(controller.id(), controllerMapping);
            }
        }
    }

    public void serviceChanged(ServiceEvent event, View view) {
        int eventType = event.getType();

        if (eventType == ServiceEvent.UNREGISTERING) {
            this.viewDictionary.remove(view.id());
            return;
        }

        if (eventType == ServiceEvent.MODIFIED) {
            this.controllerDictionary.remove(view.id());
            eventType = ServiceEvent.REGISTERED;
        }

        if (eventType == ServiceEvent.REGISTERED) {
            ViewMapping viewMapping = new ViewMapping(view.id(), view.template(), view.parentId(), view.blocks(), view);
            this.viewDictionary.put(view.id(), viewMapping);
        }
    }

    public void serviceChanged(ServiceEvent event) {
        ServiceReference<?> serviceReference = event.getServiceReference();
        if (serviceReference == null) {
            return;
        }
        Object object = this.context.getService(serviceReference);
        if (object == null) {
            return;
        }
        if (object instanceof Controller) {
            serviceChanged(event, (Controller) object);
        } else if (object instanceof View) {
            serviceChanged(event, (View) object);
        }

    }
}
