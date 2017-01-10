package org.angkorteam.mbaas.login.controller;

import org.angkorteam.mbaas.login.view.LoginView;
import org.angkorteam.mbaas.servlet.Connection;
import org.angkorteam.mbaas.servlet.LogicController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class LoginController extends LogicController {

    public LoginController() {
        super(LoginController.class.getName(), GET, "/login");
    }

    @Override
    public String execute(Connection connection, Map<String, String> pathVariables, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return LoginView.ID;
    }

}
