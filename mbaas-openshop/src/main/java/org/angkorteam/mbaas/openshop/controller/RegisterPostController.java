package org.angkorteam.mbaas.openshop.controller;

import org.angkorteam.mbaas.servlet.FormFile;
import org.angkorteam.mbaas.servlet.FormItem;
import org.angkorteam.mbaas.servlet.LogicController;
import org.angkorteam.mbaas.servlet.QueryString;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.osgi.framework.Bundle;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/12/17.
 */
public class RegisterPostController extends LogicController {

    private final Bundle bundle;

    public RegisterPostController(Bundle bundle) {
        super(POST, "/register");
        this.bundle = bundle;
    }

    @Override
    public String execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        URL url = bundle.getResource("/validation/register.xml");
        InputStream inputStream = url.openStream();
        try {
            ValidatorResources resources = new ValidatorResources(inputStream);
            Validator validator = new Validator(resources, "register");
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
}
