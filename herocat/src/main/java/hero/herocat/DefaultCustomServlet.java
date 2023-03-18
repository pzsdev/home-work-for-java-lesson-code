package hero.herocat;

import hero.servlet.HeroResponse;
import hero.servlet.HeroRequest;
import hero.servlet.HeroServlet;

/**
 * TODO
 *
 * @author pengzhisheng
 * @since 2023/3/16
 **/
public class DefaultCustomServlet extends HeroServlet {
    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        String uri = request.getUri();
        response.write("404 - no this servlet : " + (uri.contains("?")?uri.substring(0,uri.lastIndexOf("?")):uri));
    }

    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }
}
