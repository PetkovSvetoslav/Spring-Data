package com.example.nextleveltechnologies.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected boolean isLogged(HttpServletRequest request) {
        return request.getSession().getAttribute("userId") != null;
    }
}
