package org.angkorteam.mbaas.login;

import org.angkorteam.mbaas.servlet.Connection;
import org.angkorteam.mbaas.servlet.Controller;
import org.osgi.framework.Bundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class LoginController implements Controller {

    private final Bundle bundle;

    public LoginController(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public String method() {
        return GET;
    }

    @Override
    public String id() {
        return "111";
    }

    @Override
    public Bundle bundle() {
        return this.bundle;
    }

    @Override
    public String path() {
        return "/login";
    }

    @Override
    public String execute(Connection connection, Map<String, String> pathVariables, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return LoginView.ID;
    }

}
