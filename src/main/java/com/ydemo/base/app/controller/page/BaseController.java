package com.ydemo.base.app.controller.page;

import com.ydemo.base.app.service.SiteService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hongbin on 2017/3/21.
 */
public abstract class BaseController {

    @Autowired
    private SiteService siteService;

    public static String THEME = "pages";

    public String render(String viewName) {


        return THEME + "/" + viewName;
    }

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    public BaseController keywords(HttpServletRequest request, String keywords) {
        request.setAttribute("keywords", keywords);
        return this;
    }




}
