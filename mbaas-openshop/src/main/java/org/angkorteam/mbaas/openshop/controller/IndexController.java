package org.angkorteam.mbaas.login.controller;

import org.angkorteam.mbaas.login.page.IndexPageView;
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
 * Created by socheatkhauv on 1/11/17.
 */
public class IndexController extends LogicController {

    public IndexController() {
        super(GET, "/index");
    }

    @Override
    public String execute(Connection connection, String address, Map<String, String> pathVariables, QueryString queryString, FormItem formItem, FormFile formFile, File fileBody, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return view(IndexPageView.ID);
    }

}
