package org.hero.herocat;

import org.hero.servlet.HeroRequest;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author pengzhisheng
 * @since 2023/3/16
 **/
public class HttpCustomRequest implements HeroRequest {
    @Override
    public String getUri() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return null;
    }

    @Override
    public List<String> getParameters(String name) {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }
}
