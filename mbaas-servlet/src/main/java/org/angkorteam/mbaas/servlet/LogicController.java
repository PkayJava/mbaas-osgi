package org.angkorteam.mbaas.servlet;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    protected ValidatorResources createValidatorResources(BundleContext bundle, URL url) throws IOException {
        ServiceReference<ValidatorService> reference = bundle.getServiceReference(ValidatorService.class);
        ValidatorService service = bundle.getService(reference);
        return service.createValidatorResources(url);
    }

    protected Validator createValidator(Bundle bundle, ValidatorResources resources, String form) throws IOException {
        ServiceReference<ValidatorService> reference = bundle.getBundleContext().getServiceReference(ValidatorService.class);
        ValidatorService service = bundle.getBundleContext().getService(reference);
        return service.createValidator(bundle, resources, form);
    }

}
