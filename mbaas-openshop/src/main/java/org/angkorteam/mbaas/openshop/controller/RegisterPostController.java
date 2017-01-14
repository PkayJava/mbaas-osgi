package org.angkorteam.mbaas.openshop.controller;

import org.angkorteam.mbaas.servlet.FormFile;
import org.angkorteam.mbaas.servlet.FormItem;
import org.angkorteam.mbaas.servlet.LogicController;
import org.angkorteam.mbaas.servlet.QueryString;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/12/17.
 */
public class RegisterPostController extends LogicController {

    private final BundleContext bundle;

    public RegisterPostController(BundleContext bundle) {
        super(POST, "/register");
        this.bundle = bundle;
    }

    @Override
    public String execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        URL url = bundle.getBundle().getResource("/validation/register.xml");
        try {
            ValidatorResources resources = createValidatorResources(url);
            Validator validator = createValidator(bundle.getBundle(), resources, "register");
            validator.setParameter(Validator.BEAN_PARAM, formItem);
            validator.validate();
            if (formItem.isError()) {
                return redirect("/register");
            } else {
                formItem.clear();
                return redirect("/");
            }
        } catch (SAXException | ValidatorException e) {
            throw new IOException(e);
        }
    }

    protected ValidatorResources createValidatorResources(URL url) throws IOException, SAXException {
        ServiceReference<ValidatorService> reference = bundle.getServiceReference(ValidatorService.class);
        ValidatorService service = bundle.getService(reference);
        return service.createValidatorResources(url);
    }

    protected Validator createValidator(Bundle bundle, ValidatorResources resources, String form) throws IOException, SAXException {
        ServiceReference<ValidatorService> reference = bundle.getBundleContext().getServiceReference(ValidatorService.class);
        ValidatorService service = bundle.getBundleContext().getService(reference);
        return service.createValidator(bundle, resources, form);
    }

}
