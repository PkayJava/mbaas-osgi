package org.angkorteam.mbaas.openshop.controller;

import org.angkorteam.mbaas.servlet.FormFile;
import org.angkorteam.mbaas.servlet.FormItem;
import org.angkorteam.mbaas.servlet.LogicController;
import org.angkorteam.mbaas.servlet.QueryString;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.osgi.framework.BundleContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class LoginPostController extends LogicController {

    private final BundleContext bundle;

    public LoginPostController(BundleContext bundle) {
        super(POST, "/login");
        this.bundle = bundle;
    }

    @Override
    public String execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        URL url = bundle.getBundle().getResource("/validation/login.xml");
        ValidatorResources resources = createValidatorResources(this.bundle, url);
        Validator validator = createValidator(bundle.getBundle(), resources, "register");
        validator.setParameter(Validator.BEAN_PARAM, formItem);
        validator.validate();
        if (formItem.isError()) {
            return redirect("/register");
        } else {
            formItem.clear();
            return redirect("/");
        }
    }
}
