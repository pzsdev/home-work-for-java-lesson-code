package com.hero.webapp;

import hero.servlet.HeroRequest;
import hero.servlet.HeroResponse;
import hero.servlet.HeroServlet;

/**
 * TODO
 *
 * @author pengzhisheng
 * @since 2023/3/18
 **/
public class SkuServlet extends HeroServlet {
    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        String uri = request.getUri();
        String path = request.getPath();
        String method = request.getMethod();
        String name = request.getParameter("name");

        String content = "uri = " + uri + "\n" +
                "path = " + path + "\n" +
                "method = " + method + "\n" +
                "param = " + name;
        response.write(content);
    }

    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }
}
