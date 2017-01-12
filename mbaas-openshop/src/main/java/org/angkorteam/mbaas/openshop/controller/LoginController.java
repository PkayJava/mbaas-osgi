package org.angkorteam.mbaas.openshop.controller;

import org.angkorteam.mbaas.openshop.page.LoginPageView;
import org.angkorteam.mbaas.servlet.FormFile;
import org.angkorteam.mbaas.servlet.FormItem;
import org.angkorteam.mbaas.servlet.LogicController;
import org.angkorteam.mbaas.servlet.QueryString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class LoginController extends LogicController {

    public LoginController() {
        super(GET, "/login");
    }

    @Override
    public String execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return view(LoginPageView.ID);
    }
}
